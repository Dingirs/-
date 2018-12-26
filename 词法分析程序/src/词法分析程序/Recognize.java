package 词法分析程序;

import java.io.IOException;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Recognize {

	static void Sscanner(char[] r, int number, char reserveWord[][], char operatorOrDelimiter[][]) throws IOException

	{// 根据DFA的状态转换图设计
		int syn = 0;
		int i, count = 0;// count用来做token[]的指示器，收集有用字符
		int j = 0;
		char[] token = new char[100];
		String[] take = new String[3];
		txtExport.creatTxtFile("数据");

		for (int k = 0; k < number; k++) {//k作为数字下标
			count = 0;
			while (r[k] == ' ') {// 过滤空格。
				k++;
			}
			for (i = 0; i < 20; i++) {// 每次收集前先清零
				token[i] = '\0';
			}
			if (Check.IsLetter(r[k])) {// 开头为字母
				token[count] = r[k];// 收集
				count++;
				k++;// 下移
				while (Check.IsLetter(r[k]) || Check.IsDigit(r[k])) {// 后跟字母或数字
					token[count] = r[k];// 收集
					count++;
					k++;
				}
				k--;
				syn = Check.searchReserve(reserveWord, token);// 查表找到种别码
				if (syn == -1) {// 若不是保留字则是标识符
					syn = 100;// 标识符种别码
				}

			} else if (Check.IsDigit(r[k])) {// 首字符为数字
				while (Check.IsDigit(r[k])) {// 后跟数字
					token[count] = r[k];// 收集
					count++;
					k++;
				}
				k--;
				syn = 99;// 常数种别码
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
				k++;// 后移，超前搜索
				if (r[k] == '=') {
					token[count] = r[k];
					syn = 38;
				} else if (r[k] == '<') {// 左移
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

			} else if (r[k] == '$') {// 结束符
				syn = 0;// 种别码为0
			} else {// 不能被以上词法分析识别，则出错。
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
