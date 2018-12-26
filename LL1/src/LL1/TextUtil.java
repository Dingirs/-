package LL1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @function 字符工具类
 *
 */
public class TextUtil {
    /**
     * (3)B->aA,=Follow(B)
     *
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     * @return
     */
    public static boolean containsbA(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                     HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        String lastStr = itemCharStr.substring(itemCharStr.length() - 1);//取最后一位
        if (lastStr.equals(aStr)) {//如果当前需要查找的非终结符已经在最后一位
            return true;
        }
        return false;

    }

    /**
     * 形如aBb,b=空
     *
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     * @return
     */
    public static boolean containsbAbIsNull(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                            HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        if (containsAB(nvSet, itemCharStr, a)) {
            Character alastChar = getAlastChar(itemCharStr, a);
            System.out.println("----------------+++++++++++++++++++--" + expressionMap.toString());
            ArrayList<String> arrayList = expressionMap.get(alastChar);//查看后一位非终结符的映射的所有表达式
            if (arrayList.contains("ε")) {//如果在表达式中包含ε，说明该非终结符可以为空
                System.out.println(alastChar + "  contains('ε')" + aStr);
                return true;
            }
        }
        return false;//已经在末尾

    }

    /**
     * 是否包含这种的字符串<Br>
     * (2)Ab,=First(b)-ε,直接添加终结符
     *
     * @param itemCharStr
     * @param a
     * @return
     */
    public static boolean containsAb(TreeSet<Character> ntSet, String itemCharStr, Character a) {
        String aStr = a.toString();//当前需要查找的非终结符转换为String
        if (itemCharStr.contains(aStr)) {//查看当前被查找的表达式中是否存在需要查找的非终结符
            int aIndex = itemCharStr.indexOf(aStr);//定位
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);//切割当前非终结符后一位为一个字串   该子字符串从指定的 beginIndex 处开始，直到索引 endIndex - 1 处的字符。
            } catch (Exception e) {
                return false;//如果需要查找的非终结符后面不存在字符，返回false
            }
            if (ntSet.contains(findStr.charAt(0))) {//如果当前终结符集包含后一位字符
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否包含这种的字符串<Br>
     * (2).2Ab,=First(b)-ε
     *
     * @param itemCharStr
     * @param a
     * @return
     */
    public static boolean containsAB(TreeSet<Character> nvSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;//如果需要查找的非终结符后面不存在字符，返回false
            }
            if (nvSet.contains(findStr.charAt(0))) {//查看后一位是否在非终结符集合中
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取A后的字符
     *
     * @param itemCharStr
     * @param a
     * @return
     */
    public static Character getAlastChar(String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);//定位
            String findStr = "";
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);//切割后一位
            } catch (Exception e) {
                return null;
            }
            return findStr.charAt(0);//返回该终结符
        }
        return null;
    }

    /**
     * 是否为ε开始的
     *
     * @param selectExp
     * @return
     */
    public static boolean isEmptyStart(String selectExp) {
        char charAt = selectExp.charAt(0);
        if (charAt == 'ε') {
            return true;
        }
        return false;
    }

    /**
     * 是否是终结符开始的
     *
     * @param ntSet
     * @param selectExp
     * @return
     */
    public static boolean isNtStart(TreeSet<Character> ntSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        if (ntSet.contains(charAt)) {//当前字符被包含在终结符集中
            return true;
        }
        return false;
    }

    /**
     * 是否是非终结符开始的
     *
     * @param nvSet
     * @param selectExp
     * @return
     */
    public static boolean isNvStart(TreeSet<Character> nvSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        if (nvSet.contains(charAt)) {
            return true;
        }
        return false;
    }

    /**
     * 查找在selectmap中，peek对应的select集下是否有非终结符charAt，如果在的话返回对应的表达式
     *
     * @param selectMap
     * @param peek
     *            当前Nv
     * @param charAt
     *            当前字符
     * @return
     * 			对应的表达式
     */
    public static String findUseExp(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap,
    		Character peek,char charAt) {
        try {
            HashMap<String, TreeSet<Character>> hashMap = selectMap.get(peek);
            Set<String> keySet = hashMap.keySet();
            for (String useExp : keySet) {//查找改顶端的符号下属的表达式中符合分析表的一项
                TreeSet<Character> treeSet = hashMap.get(useExp);
                if (treeSet.contains(charAt)) {
                    return useExp;
                }
            } 
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}