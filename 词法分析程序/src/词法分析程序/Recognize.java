package �ʷ���������;

import java.io.IOException;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Recognize {

	static void Sscanner(char[] r, int number, char reserveWord[][], char operatorOrDelimiter[][]) throws IOException

	{// ����DFA��״̬ת��ͼ���
		int syn = 0;
		int i, count = 0;// count������token[]��ָʾ�����ռ������ַ�
		int j = 0;
		char[] token = new char[100];
		String[] take = new String[3];
		txtExport.creatTxtFile("����");

		for (int k = 0; k < number; k++) {//k��Ϊ�����±�
			count = 0;
			while (r[k] == ' ') {// ���˿ո�
				k++;
			}
			for (i = 0; i < 20; i++) {// ÿ���ռ�ǰ������
				token[i] = '\0';
			}
			if (Check.IsLetter(r[k])) {// ��ͷΪ��ĸ
				token[count] = r[k];// �ռ�
				count++;
				k++;// ����
				while (Check.IsLetter(r[k]) || Check.IsDigit(r[k])) {// �����ĸ������
					token[count] = r[k];// �ռ�
					count++;
					k++;
				}
				k--;
				syn = Check.searchReserve(reserveWord, token);// ����ҵ��ֱ���
				if (syn == -1) {// �����Ǳ��������Ǳ�ʶ��
					syn = 100;// ��ʶ���ֱ���
				}

			} else if (Check.IsDigit(r[k])) {// ���ַ�Ϊ����
				while (Check.IsDigit(r[k])) {// �������
					token[count] = r[k];// �ռ�
					count++;
					k++;
				}
				k--;
				syn = 99;// �����ֱ���
			} else if (r[k] == '+' || r[k] == '-' || r[k] == '*' || r[k] == '/' || r[k] == ';' || r[k] == '('
					|| r[k] == ')' || r[k] == '^' || r[k] == ',' || r[k] == '~'
					|| r[k] == '#' || r[k] == '%' || r[k] == '[' || r[k] == ']' || r[k] == '{' || r[k] == '}'
					|| r[k] == '.' || r[k] == '?' || r[k] == ':') {
				token[0] = r[k];
				token[1] = '\0';
				syn = Check.searchReserve(operatorOrDelimiter, token);

			} else if (r[k] == '<') {// <,<=,<<
				token[count] = r[k];
				count++;
				k++;// ���ƣ���ǰ����
				if (r[k] == '=') {
					token[count] = r[k];
					syn = 38;
				} else if (r[k] == '<') {// ����
					token[count] = r[k];
					k--;
					syn = 58;
				} else {
					k--;
					syn = 37;
				}

			} else if (r[k] == '>') {// >,>=,>>
				token[count] = r[k];
				count++;
				k++;
				if (r[k] == '=') {
					token[count] = r[k];
					syn = 40;
				} else if (r[k] == '>') {
					token[count] = r[k];
					syn = 59;
				} else {
					k--;
					syn = 39;
				}

			} else if (r[k] == '=') {// =.==
				token[count] = r[k];
				count++;
				k++;
				if (r[k] == '=') {
					token[count] = r[k];

					syn = 42;
				} else {
					k--;
					syn = 41;
				}

			} else if (r[k] == '!') {// !,!=
				token[count] = r[k];
				count++;
				k++;
				if (r[k] == '=') {
					token[count] = r[k];
					syn = 43;
				} else {
					syn = 68;
					k--;
				}

			} else if (r[k] == '&') {// &,&&
				token[count] = r[k];
				count++;
				k++;
				if (r[k] == '&') {
					token[count] = r[k];
					syn = 53;
				} else {
					k--;
					syn = 52;
				}

			} else if (r[k] == '|') {// |,||
				token[count] = r[k];
				count++;
				k++;
				if (r[k] == '|') {
					token[count] = r[k];
					syn = 55;
				} else {
					k--;
					syn = 54;
				}

			} else if (r[k] == '$') {// ������
				syn = 0;// �ֱ���Ϊ0
			} else {// ���ܱ����ϴʷ�����ʶ�������
					System.out.println("error:"+ r[k]);
					System.exit(0);

			}
			if(token[0]=='\0') {
				continue;
			}
			for (j = 0; j < token.length; j++) {
				System.out.print(token[j]);
				if (token[j + 1] == '\0') {
					break;
				}
			}
			String str=new String(token,0,j+1);
			take[0]=str;
			take[1] = String.valueOf(syn);
			txtExport.writeTxtFile(take[0]+','+take[1]);
			System.out.println("," + syn);
		}
	}

	private static String String(char[] token, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
