package 词法分析程序;

public class Filtration {
	public static char[] filtration(char[] r,int number){
		char[] C = new char[10000];
		 int count = 0;
		    for (int i = 0; i <= number; i++)
		    {
		        if (r[i] == '/'&&r[i + 1] == '/')
		        {//若为单行注释“//”,则去除注释后面的东西，直至遇到回车换行
		            while (r[i] != '\n')
		            {
		                i++;//向后扫描
		            }
		        }
		        if (r[i] == '/'&&r[i + 1] == '*')
		        {//若为多行注释“/* 。。。*/”则去除该内容
		            i += 2;
		            while (r[i] != '*' || r[i + 1] != '/')
		            {
		                i++;//继续扫描
		                if (r[i] == '$')
		                {
		                    System.out.println("注释出错，没有找到 */，程序结束！！！\n");
		                    System.exit(0);
		                }
		            }
		            i += 2;//跨过“*/”
		        }
		        if (r[i] != '\n'&&r[i] != '\t'&&r[i] != '\t'&&r[i] != '\r')
		        {//若出现无用字符，则过滤；否则加载
		            C[count++] = r[i];
		        }
		    }
		   C[count] = '\0';
		return C;
	}
}
