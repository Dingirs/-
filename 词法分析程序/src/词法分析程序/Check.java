package �ʷ���������;

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
	        {//���ɹ����ң��򷵻��ֱ���
	        	if(reserveWord[i][j+1]=='\u0000') {	//char��Ĭ��ֵΪ'\u0000' s���鳤�Ȳ�������i����ʶ������
	        		if(s[j+1]=='\u0000')
	        			return i + 1;//�����ֱ���
	        	}
	        	j++;
	        }
	    }
	    return -1;//���򷵻�-1��������Ҳ��ɹ�����Ϊ��ʶ��
	}
	
	
}
