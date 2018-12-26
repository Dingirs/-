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
	 * Select����
	 * S->S-AB->{ab}
	 */
	private TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap;
	/**
	 * LL��1���ķ���������
	 */
	private ArrayList<String> gsArray;
	/**
	 * ���ʽ����
	 */
	private HashMap<Character, ArrayList<String>> expressionMap;
	/**
	 * ��ʼ��
	 */
	private Character s;
	/**
	 * Vn���ս������
	 */
	private TreeSet<Character> nvSet;
	/**
	 * Vt�ս������
	 */
	private TreeSet<Character> ntSet;
	/**
	 * First����
	 */
	private HashMap<Character, TreeSet<Character>> firstMap;
	/**
	 * Follow���ϣ�һ����Ӧһ��ֵ
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
	 * ��ȡ���ս�������ս����
	 * 
	 * @param gsArray
	 * @param nvSet
	 * @param ntSet
	 */
	public void getNvNt() {
		for (String gsItem : gsArray) {//ѭ���ѷ��ս������nvSet
			String[] nvNtItem = gsItem.split("->");	//���ա�>�ָ�
			String charItemStr = nvNtItem[0];
			char charItem = charItemStr.charAt(0);
			// nv�����
			nvSet.add(charItem);
		}
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");
			// nt���ұ�
			String nvItemStr = nvNtItem[1];
			// ����ÿһ����
			for (int i = 0; i < nvItemStr.length(); i++) {//ѭ���������е�ÿһ��char
				char charItem = nvItemStr.charAt(i);
				if (!nvSet.contains(charItem)) {//������char���ڷ��ս����
					ntSet.add(charItem);//�ս����ntSet����
				}
			}
		}
	}

	/**
	 * ��ʼ�����ʽ����
	 */
	public void initExpressionMaps() {
		expressionMap = new HashMap<Character, ArrayList<String>>();
		for (String gsItem : gsArray) {
			String[] nvNtItem = gsItem.split("->");//�ָ���ʽ
			String charItemStr = nvNtItem[0];
			String charItemRightStr = nvNtItem[1];
			char charItem = charItemStr.charAt(0);//charItem���浱ǰ���ʽ��ߵķ��ս��
			if (!expressionMap.containsKey(charItem)) {//����÷��ս����û��expressionMap��key��
				ArrayList<String> expArr = new ArrayList<String>();
				expArr.add(charItemRightStr);
				expressionMap.put(charItem, expArr);//���¸÷��ս��keyӳ���arraylist
			} else {
				ArrayList<String> expArr = expressionMap.get(charItem);//��ȡ�÷��ս����Ӧ�Ĳ�ʤʵarraylist
				expArr.add(charItemRightStr);//��arraylist������µı��ʽ
				expressionMap.put(charItem, expArr);//����
			}
		}
	}

	/**
	 * ��ȡFirst��
	 */
	public void getFirst() {
		// ��������Nv,������ǵ�First����
		Iterator<Character> iterator = nvSet.iterator();//�����ڴ� set �е�Ԫ���ϰ�������е����ĵ�������
		//int i =0;
		while (iterator.hasNext()) {//�������Ԫ�ؿ��Ե������򷵻� true
			Character charItem = iterator.next();//charItem��ŵ�ǰҪ����first���ķ��ս��
			ArrayList<String> arrayList = expressionMap.get(charItem);//arratlist��ŵ�ǰ���ս��ӳ������б��ʽ
			TreeSet<Character> itemSet =new TreeSet<Character>();//��ʱ��ŵ�ǰ���ս����first��
			for (String itemStr : arrayList) {//ѭ�����÷��ս��ӳ���µ�arraylist�е�ÿһ�����ʽ
				itemSet.addAll(calcFirst(itemSet,charItem,itemStr));
			}
			firstMap.put(charItem, itemSet);
			int a =itemSet.size();
			System.out.println(charItem+"��first����"+a);
		}
	}

	/**
	 * ����First����
	 * 
	 * @param itemSet��ʱ��ŵ�first��
	 * @param charItem��ǰҪ���ҵķ��ս��
	 * @param itemStr��ǰ�鿴�Ĳ���ʽ
	 * @return
	 */
	private TreeSet<Character> calcFirst(TreeSet<Character> itemSet, Character charItem, String itemStr) {
		// ������ÿһλ��Nt�ж���
		// ���ս����մ�,��ֹͣ���������ӵ�FirstMap��
		for (int i = 0; i < itemStr.length(); i++) {
			char itemitemChar = itemStr.charAt(i);
			if (itemitemChar == '��' || ntSet.contains(itemitemChar)) {
				itemSet.add(itemitemChar);
				return itemSet;
			} 
			else if (nvSet.contains(itemitemChar)) {							// ��һλ��һ�����ս��
				ArrayList<String> arrayList = expressionMap.get(itemitemChar);	//�鿴�÷��ս��ӳ������еı��ʽ������ս����first��
				TreeSet<Character> tempset= new TreeSet<Character>();
				for (int j = 0; j < arrayList.size(); j++) {					//ѭ���鿴ÿһ�����ʽ
					String temstring = arrayList.get(j);
					if(firstMap.get(itemitemChar)!=null) {
						tempset.addAll(firstMap.get(itemitemChar));
					}
					else {
						tempset.addAll(calcFirst(tempset, itemitemChar, temstring));
					}
				}
				if(!tempset.contains('��')){
					itemSet.addAll(tempset);
					break;
				}
				else {
					tempset.remove('��');
					itemSet.addAll(tempset);
				}
			}
		}
		return itemSet;
	}

	/**
	 * ��ȡFollow����
	 */
	public void getFollow() {
		for (Character tempKey : nvSet) {											//��ʼ��follow��
			TreeSet<Character> tempSet = new TreeSet<Character>();
			followMap.put(tempKey, tempSet);
		}
		Iterator<Character> iterator = nvSet.descendingIterator();					//�����ڴ� set Ԫ���ϰ�������е����ĵ�������
		while (iterator.hasNext()) {
			Character charItem = iterator.next();									//��һ����Ҫ����follow���ķ��ս��
			System.out.println("charItem:" + charItem);
			Set<Character> keySet = expressionMap.keySet();							//���ش�ӳ�����������ļ��� Set��ͼ��
			for (Character keyCharItem : keySet) {									//ѭ���鿴����keyֵ��ӳ������б��ʽ
				ArrayList<String> charItemArray = expressionMap.get(keyCharItem);	//��ŵ�ǰkeyֵӳ���µ������﷨���ʽ
				for (String itemCharStr : charItemArray) {							//itemCharStr��ŵ�ǰӳ���µ�һ�����ʽ
					System.out.println(keyCharItem + "->" + itemCharStr);
					TreeSet<Character> itemSet = followMap.get(charItem);			//������е�follow������
					calcFollow(charItem, charItem, keyCharItem, itemCharStr, itemSet);//���㵱ǰ���ʽ�����ҵ���follow��
				}//��keyCharItem��Ӧ�ı��ʽitemCharStrs�в���charItem��follow������itemSet��ʱ���follow����������putCharItem��follow��
			}
		}
	}

	/**
	 * ����Follow��
	 * 
	 * @param putCharItem
	 *            ��Ҫ����follow���ķ��ս��
	 * @param charItem
	 *            ��ǰ��Ҫ���ҵķ��ս������Ϊ���ܴ��ڵ�ǰ���ս���ڱ��ʽĩβ����Ҫ���ϲ���ս����follow����
	 * @param keyCharItem
	 *            ��ǰ�����ҵı��ʽ��Ӧ�ķ��ս��key��keyCharItem->itemCharStr��
	 * @param itemCharStr
	 *            ��ǰ�����ҵı��ʽ
	 * @param itemSet
	 *            ��ǰ��follow��
	 */
	private void calcFollow(Character putCharItem, Character charItem, Character keyCharItem, String itemCharStr,
			TreeSet<Character> itemSet) {
		// ��1��A��S����ʼ��)������#
		if (charItem.equals(s)) {
			itemSet.add('#');//��follow�������Ԫ��
			System.out.println("---------------find S:" + charItem + "   ={#}+Follow(E)");
			followMap.put(putCharItem, itemSet);//����follow��
			// return;
		}
		// (2)Ab,=First(b)-��,ֱ������ս��
		if (TextUtil.containsAb(ntSet, itemCharStr, charItem)) {//�����ǰ���ҵķ��ս����һλ���ս��
			Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);//ȡ��һλ
			System.out.println("---------------find Ab:" + itemCharStr + "    " + charItem + "   =" + alastChar);
			itemSet.add(alastChar);//���
			followMap.put(putCharItem, itemSet);//����
			// return;
		}
		// (2).2AB,=First(B)-��,=First(B)-�ţ����first����
		if (TextUtil.containsAB(nvSet, itemCharStr, charItem)) {//��һλ�Ƿ��ս��
			Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);//ȡ��һλ
			System.out.println(
					"---------------find AB:" + itemCharStr + "    " + charItem + "   =First(" + alastChar + ")");
			TreeSet<Character> treeSet = firstMap.get(alastChar);//�Ѻ�һλ���ս����first��������Ҫ���ҵķ��ս����follow����
			itemSet.addAll(treeSet);//���
			if (treeSet.contains('��')) {//���follow���º���ڦ�,��first�����Ц�
				itemSet.remove('��');//ȥ��follow���еĦ�
				char tempChar = TextUtil.getAlastChar(itemCharStr, charItem);//��ȡ��һλ
				System.out.println("tempChar:" + tempChar + "  key" + keyCharItem);
				if (!keyCharItem.equals(charItem)) {//�����ǰ���ս��key������Ҫ���ҵķ��ս��
					System.out.println("---------------find tempChar bA: " + "tempChar:" + tempChar + keyCharItem
							+ "   " + itemCharStr + "    " + charItem + "   =Follow(" + keyCharItem + ")");
					//���㵱ǰ�ս��key��follow��
					Set<Character> keySet = expressionMap.keySet();
					for (Character keyCharItems : keySet) {
						ArrayList<String> charItemArray = expressionMap.get(keyCharItems);//��ǰkeysֵ��ӳ������б��ʽ
						for (String itemCharStrs : charItemArray) {
							calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);//�Ե�ǰ�����ҵķ��ս������follow����
						}//��keyCharItems��Ӧ��itemCharStrs�в���keyCharIten��follow������itemSet���keyCharItem��folloe����������putCharItem��follow��
					}
				}
			}
			followMap.put(putCharItem, itemSet);//����follow��	
		}
		// (3)B->aA,=Follow(B),���followB
		if (TextUtil.containsbA(nvSet, itemCharStr, charItem, expressionMap)) {//��Ҫ���ҵķ��ս����ĩβ
			if (!keyCharItem.equals(charItem)) {//�����ǰ���ս��key������Ҫ���ҵķ��ս��
				System.out.println("---------------find bA: " + keyCharItem + "   " + itemCharStr + "    " + charItem
						+ "   =Follow(" + keyCharItem + ")");
				//���㵱ǰ�ս��key��follow��
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
	 * ��ȡSelect����
	 */
	public void getSelect() {
		// ����ÿһ�����ʽ
		// HashMap<Character, HashMap<String, TreeSet<Character>>>
		Set<Character> keySet = expressionMap.keySet();//keySet������з��ս��key
		for (Character selectKey : keySet) {//�������еı��ʽ������selectkey��ǰ���ʽ��Ӧ�ķ��ս��
			ArrayList<String> arrayList = expressionMap.get(selectKey);//��ǰ���ս��keyӳ������б��ʽ
			// ÿһ�����ʽ
			HashMap<String, TreeSet<Character>> selectItemMap = new HashMap<String, TreeSet<Character>>();//���ʽ->select����ӳ��
			for (String selectExp : arrayList) {//����ÿһ�����ʽ��select
				/**
				 * ���select����ļ���
				 */
				TreeSet<Character> selectSet = new TreeSet<Character>();
				// set���ŵ����ݷ�3�����,��selectExp����
				// 1.A->��,=follow(A)
				if (TextUtil.isEmptyStart(selectExp)) {//���ʽΪ��
					selectSet = followMap.get(selectKey);//�ѵ�ǰ���ս��key��follow������select��
					selectSet.remove('��');//ȥ����
					selectItemMap.put(selectExp, selectSet);//����
				}
				// 2.Nt��ʼ,=Nt
				// <br>�ս����ʼ
				if (TextUtil.isNtStart(ntSet, selectExp)) {//���ʽ���ַ����ս��
					selectSet.add(selectExp.charAt(0));//selectֱ����ӵ�ǰ��һ���ս��
					selectSet.remove('��');
					selectItemMap.put(selectExp, selectSet);
				}
				// 3.Nv��ʼ��=first(Nv)
				if (TextUtil.isNvStart(nvSet, selectExp)) {//���ʽ���ַ��Ƿ��ս��
					selectSet = firstMap.get(selectKey);//�ѷ��ս����first����ӽ�select��
					selectSet.remove('��');
					selectItemMap.put(selectExp, selectSet);
				}
				selectMap.put(selectKey, selectItemMap);
			}
		}
	}

	/**
	 * ����Ԥ�������
	 */
	public void genAnalyzeTable() throws Exception {
		Object[] ntArray = ntSet.toArray();
		Object[] nvArray = nvSet.toArray();
		// Ԥ��������ʼ��
		analyzeTable = new String[nvArray.length + 1][ntArray.length + 1];

		// ���һ��ռλ��
		System.out.print("Nv/Nt" + "\t\t");
		analyzeTable[0][0] = "Nv/Nt";
		// ��ʼ������
		for (int i = 0; i < ntArray.length; i++) {
			if (ntArray[i].equals('��')) {
				ntArray[i] = '#';//����Ϊ��˵���Ѿ�������
			}
			System.out.print(ntArray[i] + "\t\t");
			analyzeTable[0][i + 1] = ntArray[i] + "";//���ս��������������У��ճ���һ������
		}
		System.out.println("");//���У��ճ���һ������
		for (int i = 0; i < nvArray.length; i++) {
			// ���г�ʼ��
			System.out.print(nvArray[i] + "\t\t");
			analyzeTable[i + 1][0] = nvArray[i] + "";//�ѷ��ս�����������������
			for (int j = 0; j < ntArray.length; j++) {//һ��һ�а�slect����Ӧ�ı��ʽ��������
				String findUseExp = TextUtil.findUseExp(selectMap, Character.valueOf((Character) nvArray[i]),
						Character.valueOf((Character) ntArray[j]));//�ڷ������в��Ҷ�Ӧ��[���ս��][�ս��]�еı��ʽ
				if (null == findUseExp) {//�������в����ڱ��ʽ
					System.out.print("\t\t");
					analyzeTable[i + 1][j + 1] = "";
				} else {
					//����i+1
					System.out.print(nvArray[i] + "->" + findUseExp + "\t\t");
					analyzeTable[i + 1][j + 1] = nvArray[i] + "->" + findUseExp;
				}
			}
			System.out.println();
		}
	}
}

