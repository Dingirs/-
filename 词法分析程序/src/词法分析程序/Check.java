package 词法分析程序;

public class Check {
	public static Boolean IsDigit(char digit)
	{
	    if (digit >= '0'&&digit <= '9')
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}
	
	public static Boolean IsLetter(char letter)
	{
	    if (letter >= 'a'&&letter <= 'z' || letter >= 'A'&&letter <= 'Z'|| letter=='_')
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}
	
	public static int searchReserve(char reserveWord[][], char s[])
	{
		int j=0;
	    for (int i = 0; i < 32; i++)
	    {
	    	j=0;
	        while (s[j]==reserveWord[i][j])
	        {//若成功查找，则返回种别码
	        	if(reserveWord[i][j+1]=='\u0000') {	//char的默认值为'\u0000' s数组长度不超过第i个标识符长度
	        		if(s[j+1]=='\u0000')
	        			return i + 1;//返回种别码
	        	}
	        	j++;
	        }
	    }
	    return -1;//否则返回-1，代表查找不成功，即为标识符
	}
	
	
}
