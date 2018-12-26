package LL1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Gs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Gs() {
		super();
		gsArray = new ArrayList<String>();
		nvSet = new TreeSet<Character>();
		ntSet = new TreeSet<Character>();
		firstMap = new HashMap<Character, TreeSet<Character>>();
		followMap = new HashMap<Character, TreeSet<Character>>();
		selectMap = new TreeMap<Character, HashMap<String, TreeSet<Character>>>();
	}

	private String[][] analyzeTable;

	/**
	 * Select集合
	 * S->S-AB->{ab}
	 */
	private TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap;
	/**
	 * LL（1）文法产生集合
	 */
	private ArrayList<String> gsArray;
	/**
	 * 表达式集合
	 */
	private HashMap<Character, ArrayList<String>> expressionMap;
	/**
	 * 开始符
	 */
	private Character s;
	/**
	 * Vn非终结符集合
	 */
	private TreeSet<Character> nvSet;
	/**
	 * Vt终结符集合
	 */
	private TreeSet<Character> ntSet;
	/**
	 * First集合
	 */
	private HashMap<Character, TreeSet<Character>> firstMap;
	/**
	 * Follow集合，一键对应一个值
	 */
	private HashMap<Character, TreeSet<Character>> followMap;

	public String[][] getAnalyzeTable() {
		return analyzeTable;
	}

	public void setAnalyzeTable(String[][] analyzeTable) {
		this.analyzeTable = analyzeTable;
	}

	public TreeMap<Character, HashMap<String, TreeSet<Character>>> getSelectMap() {
		return selectMap;
	}

	public void setSelectMap(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap) {
		this.selectMap = selectMap;
	}

	public HashMap<Character, TreeSet<Character>> getFirstMap() {
		return firstMap;
	}

	public void setFirstMap(HashMap<Character, TreeSet<Character>> firstMap) {
		this.firstMap = firstMap;
	}

	public HashMap<Character, TreeSet<Character>> getFollowMap() {
		return followMap;
	}

	public void setFollowMap(HashMap<Character, TreeSet<Character>> followMap) {
		this.followMap = followMap;
	}

	public HashMap<Character, ArrayList<String>> getExpressionMap() {
		return expressionMap;
	}

	public void setExpressionMap(HashMap<Character, ArrayList<String>> expressionMap) {
		this.expressionMap = expressionMap;
	}

	public ArrayList<String> getGsArray() {
		return gsArray;
	}

	public void setGsArray(ArrayList<String> gsArray) {
		this.gsArray = gsArray;
	}

	public Character getS() {
		return s;
	}

	public void setS(Character s) {
		this.s = s;
	}

	public TreeSet<Character> getNvSet() {
		return nvSet;
	}

	public void setNvSet(TreeSet<Character> nvSet) {
		this.nvSet = nvSet;
	}

	public TreeSet<Character> getNtSet() {
		return ntSet;
	}

	public void setNtSet(TreeSet<Character> ntSet) {
		this.ntSet = ntSet;
	}

	/**
	 * 获取非终结符集与终结符集
	 * 
	 * @param gsArray
	 * @param nvSet
	 * @param ntSet
	 */
	public void getNvNt() {
		for (String gsItem : gsArray) {//循环把非终结符翻入nvSet
			String[] nvNtItem = gsItem.split("->");	//按照—>分割
			String charItemStr = nvNtItem[0];
			char charItem = charItemStr.charAt(0);
			// nv在左边
			nvSet.add(charItem);
		}
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			// nt在右边
			String nvItemStr = nvNtItem[1];
			// 遍历每一个字
			for (int i = 0; i < nvItemStr.length(); i++) {//循环遍历其中的每一个char
				char charItem = nvItemStr.charAt(i);
				if (!nvSet.contains(charItem)) {//如果这个char不在非终结符中
					ntSet.add(charItem);//终结符进ntSet集中
				}
			}
		}
	}

	/**
	 * 初始化表达式集合
	 */
	public void initExpressionMaps() {
		expressionMap = new HashMap<Character, ArrayList<String>>();
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");//分割表达式
			String charItemStr = nvNtItem[0];
			String charItemRightStr = nvNtItem[1];
			char charItem = charItemStr.charAt(0);//charItem保存当前表达式左边的非终结符
			if (!expressionMap.containsKey(charItem)) {//如果该非终结符还没在expressionMap的key中
				ArrayList<String> expArr = new ArrayList<String>();
				expArr.add(charItemRightStr);
				expressionMap.put(charItem, expArr);//更新该非终结符key映射的arraylist
			} else {
				ArrayList<String> expArr = expressionMap.get(charItem);//提取该非终结符对应的惨胜实arraylist
				expArr.add(charItemRightStr);//在arraylist中添加新的表达式
				expressionMap.put(charItem, expArr);//更新
			}
		}
	}

	/**
	 * 获取First集
	 */
	public void getFirst() {
		// 遍历所有Nv,求出它们的First集合
		Iterator<Character> iterator = nvSet.iterator();//返回在此 set 中的元素上按升序进行迭代的迭代器。
		//int i =0;
		while (iterator.hasNext()) {//如果仍有元素可以迭代，则返回 true
			Character charItem = iterator.next();//charItem存放当前要查找first集的非终结符
			ArrayList<String> arrayList = expressionMap.get(charItem);//arratlist存放当前非终结符映射的所有表达式
			TreeSet<Character> itemSet =new TreeSet<Character>();//临时存放当前非终结符的first集
			for (String itemStr : arrayList) {//循环检查该非终结符映射下的arraylist中的每一个表达式
				itemSet.addAll(calcFirst(itemSet,charItem,itemStr));
			}
			firstMap.put(charItem, itemSet);
			int a =itemSet.size();
			System.out.println(charItem+"的first集有"+a);
		}
	}

	/**
	 * 计算First函数
	 * 
	 * @param itemSet临时存放的first集
	 * @param charItem当前要查找的非终结符
	 * @param itemStr当前查看的产生式
	 * @return
	 */
	private TreeSet<Character> calcFirst(TreeSet<Character> itemSet, Character charItem, String itemStr) {
		// 将它的每一位和Nt判断下
		// 是终结符或空串,就停止，并将它加到FirstMap中
		for (int i = 0; i < itemStr.length(); i++) {
			char itemitemChar = itemStr.charAt(i);
			if (itemitemChar == 'ε' || ntSet.contains(itemitemChar)) {
				itemSet.add(itemitemChar);
				return itemSet;
			} 
			else if (nvSet.contains(itemitemChar)) {							// 这一位是一个非终结符
				ArrayList<String> arrayList = expressionMap.get(itemitemChar);	//查看该非终结符映射的所有的表达式，求该终结符的first集
				TreeSet<Character> tempset= new TreeSet<Character>();
				for (int j = 0; j < arrayList.size(); j++) {					//循环查看每一个表达式
					String temstring = arrayList.get(j);
					if(firstMap.get(itemitemChar)!=null) {
						tempset.addAll(firstMap.get(itemitemChar));
					}
					else {
						tempset.addAll(calcFirst(tempset, itemitemChar, temstring));
					}
				}
				if(!tempset.contains('ε')){
					itemSet.addAll(tempset);
					break;
				}
				else {
					tempset.remove('ε');
					itemSet.addAll(tempset);
				}
			}
		}
		return itemSet;
	}

	/**
	 * 获取Follow集合
	 */
	public void getFollow() {
		for (Character tempKey : nvSet) {											//初始化follow集
			TreeSet<Character> tempSet = new TreeSet<Character>();
			followMap.put(tempKey, tempSet);
		}
		Iterator<Character> iterator = nvSet.descendingIterator();					//返回在此 set 元素上按降序进行迭代的迭代器。
		while (iterator.hasNext()) {
			Character charItem = iterator.next();									//下一个需要查找follow集的非终结符
			System.out.println("charItem:" + charItem);
			Set<Character> keySet = expressionMap.keySet();							//返回此映射中所包含的键的 Set视图。
			for (Character keyCharItem : keySet) {									//循环查看所有key值下映射的所有表达式
				ArrayList<String> charItemArray = expressionMap.get(keyCharItem);	//存放当前key值映射下的所有语法表达式
				for (String itemCharStr : charItemArray) {							//itemCharStr存放当前映射下的一条表达式
					System.out.println(keyCharItem + "->" + itemCharStr);
					TreeSet<Character> itemSet = followMap.get(charItem);			//存放已有的follow集内容
					calcFollow(charItem, charItem, keyCharItem, itemCharStr, itemSet);//计算当前表达式下能找到的follow集
				}//在keyCharItem对应的表达式itemCharStrs中查找charItem的follow集，用itemSet临时存放follow集，最后更新putCharItem的follow集
			}
		}
	}

	/**
	 * 计算Follow集
	 * 
	 * @param putCharItem
	 *            需要计算follow集的非终结符
	 * @param charItem
	 *            当前需要查找的非终结符（因为可能存在当前非终结符在表达式末尾，需要求上层非终结符的follow集）
	 * @param keyCharItem
	 *            当前被查找的表达式对应的非终结符key（keyCharItem->itemCharStr）
	 * @param itemCharStr
	 *            当前被查找的表达式
	 * @param itemSet
	 *            当前的follow集
	 */
	private void calcFollow(Character putCharItem, Character charItem, Character keyCharItem, String itemCharStr,
			TreeSet<Character> itemSet) {
		// （1）A是S（开始符)，加入#
		if (charItem.equals(s)) {
			itemSet.add('#');//在follow集中添加元素
			System.out.println("---------------find S:" + charItem + "   ={#}+Follow(E)");
			followMap.put(putCharItem, itemSet);//更新follow集
			// return;
		}
		// (2)Ab,=First(b)-ε,直接添加终结符
		if (TextUtil.containsAb(ntSet, itemCharStr, charItem)) {//如果当前查找的非终结符后一位是终结符
			Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);//取后一位
			System.out.println("---------------find Ab:" + itemCharStr + "    " + charItem + "   =" + alastChar);
			itemSet.add(alastChar);//添加
			followMap.put(putCharItem, itemSet);//更新
			// return;
		}
		// (2).2AB,=First(B)-ε,=First(B)-ε，添加first集合
		if (TextUtil.containsAB(nvSet, itemCharStr, charItem)) {//后一位是非终结符
			Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);//取后一位
			System.out.println(
					"---------------find AB:" + itemCharStr + "    " + charItem + "   =First(" + alastChar + ")");
			TreeSet<Character> treeSet = firstMap.get(alastChar);//把后一位非终结符的first集放入需要查找的非终结符的follow集中
			itemSet.addAll(treeSet);//添加
			if (treeSet.contains('ε')) {//如果follow更新后存在ε,即first集中有ε
				itemSet.remove('ε');//去除follow集中的ε
				char tempChar = TextUtil.getAlastChar(itemCharStr, charItem);//提取后一位
				System.out.println("tempChar:" + tempChar + "  key" + keyCharItem);
				if (!keyCharItem.equals(charItem)) {//如果当前非终结符key不是需要查找的非终结符
					System.out.println("---------------find tempChar bA: " + "tempChar:" + tempChar + keyCharItem
							+ "   " + itemCharStr + "    " + charItem + "   =Follow(" + keyCharItem + ")");
					//计算当前终结符key的follow集
					Set<Character> keySet = expressionMap.keySet();
					for (Character keyCharItems : keySet) {
						ArrayList<String> charItemArray = expressionMap.get(keyCharItems);//当前keys值下映射的所有表达式
						for (String itemCharStrs : charItemArray) {
							calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);//对当前被查找的非终结符计算follow集合
						}//在keyCharItems对应的itemCharStrs中查找keyCharIten的follow集，用itemSet存放keyCharItem的folloe集，最后更新putCharItem的follow集
					}
				}
			}
			followMap.put(putCharItem, itemSet);//更新follow集	
		}
		// (3)B->aA,=Follow(B),添加followB
		if (TextUtil.containsbA(nvSet, itemCharStr, charItem, expressionMap)) {//需要查找的非终结符在末尾
			if (!keyCharItem.equals(charItem)) {//如果当前非终结符key不是需要查找的非终结符
				System.out.println("---------------find bA: " + keyCharItem + "   " + itemCharStr + "    " + charItem
						+ "   =Follow(" + keyCharItem + ")");
				//计算当前终结符key的follow集
				Set<Character> keySet = expressionMap.keySet();
				for (Character keyCharItems : keySet) {
					ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
					for (String itemCharStrs : charItemArray) {
						calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);
					}
				}
			}
		}
	}

	/**
	 * 获取Select集合
	 */
	public void getSelect() {
		// 遍历每一个表达式
		// HashMap<Character, HashMap<String, TreeSet<Character>>>
		Set<Character> keySet = expressionMap.keySet();//keySet存放所有非终结符key
		for (Character selectKey : keySet) {//计算所有的表达式，并用selectkey当前表达式对应的非终结符
			ArrayList<String> arrayList = expressionMap.get(selectKey);//当前非终结符key映射的所有表达式
			// 每一个表达式
			HashMap<String, TreeSet<Character>> selectItemMap = new HashMap<String, TreeSet<Character>>();//表达式->select集的映射
			for (String selectExp : arrayList) {//计算每一个表达式的select
				/**
				 * 存放select结果的集合
				 */
				TreeSet<Character> selectSet = new TreeSet<Character>();
				// set里存放的数据分3种情况,由selectExp决定
				// 1.A->ε,=follow(A)
				if (TextUtil.isEmptyStart(selectExp)) {//表达式为空
					selectSet = followMap.get(selectKey);//把当前非终结符key的follow集放入select集
					selectSet.remove('ε');//去除ε
					selectItemMap.put(selectExp, selectSet);//更新
				}
				// 2.Nt开始,=Nt
				// <br>终结符开始
				if (TextUtil.isNtStart(ntSet, selectExp)) {//表达式首字符是终结符
					selectSet.add(selectExp.charAt(0));//select直接添加当前第一个终结符
					selectSet.remove('ε');
					selectItemMap.put(selectExp, selectSet);
				}
				// 3.Nv开始，=first(Nv)
				if (TextUtil.isNvStart(nvSet, selectExp)) {//表达式首字符是非终结符
					selectSet = firstMap.get(selectKey);//把非终结符的first集添加进select集
					selectSet.remove('ε');
					selectItemMap.put(selectExp, selectSet);
				}
				selectMap.put(selectKey, selectItemMap);
			}
		}
	}

	/**
	 * 生成预测分析表
	 */
	public void genAnalyzeTable() throws Exception {
		Object[] ntArray = ntSet.toArray();
		Object[] nvArray = nvSet.toArray();
		// 预测分析表初始化
		analyzeTable = new String[nvArray.length + 1][ntArray.length + 1];

		// 输出一个占位符
		System.out.print("Nv/Nt" + "\t\t");
		analyzeTable[0][0] = "Nv/Nt";
		// 初始化首行
		for (int i = 0; i < ntArray.length; i++) {
			if (ntArray[i].equals('ε')) {
				ntArray[i] = '#';//后面为空说明已经结束了
			}
			System.out.print(ntArray[i] + "\t\t");
			analyzeTable[0][i + 1] = ntArray[i] + "";//把终结符放入分析表首行，空出第一个格子
		}
		System.out.println("");//换行，空出第一个格子
		for (int i = 0; i < nvArray.length; i++) {
			// 首列初始化
			System.out.print(nvArray[i] + "\t\t");
			analyzeTable[i + 1][0] = nvArray[i] + "";//把非终结符都放入分析表首列
			for (int j = 0; j < ntArray.length; j++) {//一行一行把slect集对应的表达式放入其中
				String findUseExp = TextUtil.findUseExp(selectMap, Character.valueOf((Character) nvArray[i]),
						Character.valueOf((Character) ntArray[j]));//在分析表中查找对应的[非终结符][终结符]中的表达式
				if (null == findUseExp) {//分析表中不存在表达式
					System.out.print("\t\t");
					analyzeTable[i + 1][j + 1] = "";
				} else {
					//改了i+1
					System.out.print(nvArray[i] + "->" + findUseExp + "\t\t");
					analyzeTable[i + 1][j + 1] = nvArray[i] + "->" + findUseExp;
				}
			}
			System.out.println();
		}
	}
}

