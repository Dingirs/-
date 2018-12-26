package LL1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @function �ַ�������
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
        String lastStr = itemCharStr.substring(itemCharStr.length() - 1);//ȡ���һλ
        if (lastStr.equals(aStr)) {//�����ǰ��Ҫ���ҵķ��ս���Ѿ������һλ
            return true;
        }
        return false;

    }

    /**
     * ����aBb,b=��
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
            ArrayList<String> arrayList = expressionMap.get(alastChar);//�鿴��һλ���ս����ӳ������б��ʽ
            if (arrayList.contains("��")) {//����ڱ��ʽ�а����ţ�˵���÷��ս������Ϊ��
                System.out.println(alastChar + "  contains('��')" + aStr);
                return true;
            }
        }
        return false;//�Ѿ���ĩβ

    }

    /**
     * �Ƿ�������ֵ��ַ���<Br>
     * (2)Ab,=First(b)-��,ֱ������ս��
     *
     * @param itemCharStr
     * @param a
     * @return
     */
    public static boolean containsAb(TreeSet<Character> ntSet, String itemCharStr, Character a) {
        String aStr = a.toString();//��ǰ��Ҫ���ҵķ��ս��ת��ΪString
        if (itemCharStr.contains(aStr)) {//�鿴��ǰ�����ҵı��ʽ���Ƿ������Ҫ���ҵķ��ս��
            int aIndex = itemCharStr.indexOf(aStr);//��λ
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);//�иǰ���ս����һλΪһ���ִ�   �����ַ�����ָ���� beginIndex ����ʼ��ֱ������ endIndex - 1 �����ַ���
            } catch (Exception e) {
                return false;//�����Ҫ���ҵķ��ս�����治�����ַ�������false
            }
            if (ntSet.contains(findStr.charAt(0))) {//�����ǰ�ս����������һλ�ַ�
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * �Ƿ�������ֵ��ַ���<Br>
     * (2).2Ab,=First(b)-��
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
                return false;//�����Ҫ���ҵķ��ս�����治�����ַ�������false
            }
            if (nvSet.contains(findStr.charAt(0))) {//�鿴��һλ�Ƿ��ڷ��ս��������
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * ��ȡA����ַ�
     *
     * @param itemCharStr
     * @param a
     * @return
     */
    public static Character getAlastChar(String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);//��λ
            String findStr = "";
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);//�и��һλ
            } catch (Exception e) {
                return null;
            }
            return findStr.charAt(0);//���ظ��ս��
        }
        return null;
    }

    /**
     * �Ƿ�Ϊ�ſ�ʼ��
     *
     * @param selectExp
     * @return
     */
    public static boolean isEmptyStart(String selectExp) {
        char charAt = selectExp.charAt(0);
        if (charAt == '��') {
            return true;
        }
        return false;
    }

    /**
     * �Ƿ����ս����ʼ��
     *
     * @param ntSet
     * @param selectExp
     * @return
     */
    public static boolean isNtStart(TreeSet<Character> ntSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        if (ntSet.contains(charAt)) {//��ǰ�ַ����������ս������
            return true;
        }
        return false;
    }

    /**
     * �Ƿ��Ƿ��ս����ʼ��
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
     * ������selectmap�У�peek��Ӧ��select�����Ƿ��з��ս��charAt������ڵĻ����ض�Ӧ�ı��ʽ
     *
     * @param selectMap
     * @param peek
     *            ��ǰNv
     * @param charAt
     *            ��ǰ�ַ�
     * @return
     * 			��Ӧ�ı��ʽ
     */
    public static String findUseExp(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap,
    		Character peek,char charAt) {
        try {
            HashMap<String, TreeSet<Character>> hashMap = selectMap.get(peek);
            Set<String> keySet = hashMap.keySet();
            for (String useExp : keySet) {//���ҸĶ��˵ķ��������ı��ʽ�з��Ϸ������һ��
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