package �ʷ���������;

import java.io.IOException;
/*
��һ�ࣺ��ʶ��   letter(letter | digit)*  ���
�ڶ��ࣺ����    (digit)+  ���
�����ࣺ������(32)
auto       break    case     char        const      continue
default    do       double   else        enum       extern
float      for      goto     if          int        long
register   return   short    signed      sizeof     static
struct     switch   typedef  union       unsigned   void
volatile    while

�����ࣺ���  ��/*������//���� () { } [ ] " "  '
�����ࣺ����� <��<=��>��>=��=��+��-��*��/��^��

�����п������Ž��б��룺
<$,0>
<auto,1>
...
<while,32>
<+��33>
<-,34>
<*,35>
</,36>
<<,37>
<<=,38>
<>,39>
<>=,40>
<=,41>
<==,42>
<!=,43>
<;,44>
<(,45>
<),46>
<^,47>
<,,48>
<",49>
<',50>
<#,51>
<&,52>
<&&,53>
<|,54>
<||,55>
<%,56>
<~,57>
<<<,58>����
<>>,59>����
<[,60>
<],61>
<{,62>
<},63>
<\,64>
<.,65>
<?,66>
<:,67>
<!,68>
"[","]","{","}"
<����99  ,��ֵ>
<��ʶ��100 ����ʶ��ָ��>


*/
public class Test {
		
	public static void main(String[] args) throws IOException {
		char[] C = new char[10000];
		C=FileRead.FileReadB("data1.txt");
		int sum=FileRead.getsum();
		C=Filtration.filtration(C, sum);
		//System.out.println(C);
		char[][] reserveWord =new char[40][20];
		char[][] operatorOrDelimiter = new char[40][20];
		reserveWord=FileRead.FileReadS("reserveWord.txt");
		operatorOrDelimiter=FileRead.FileReadS("operatorOrDelimiter.txt");
		Recognize.Sscanner(C,sum,reserveWord,operatorOrDelimiter);
		//System.out.println(Check.searchReserve(reserveWord, "break".toCharArray()));
		
		
	}
}
