package �ʷ���������;

public class Filtration {
	public static char[] filtration(char[] r,int number){
		char[] C = new char[10000];
		 int count = 0;
		    for (int i = 0; i <= number; i++)
		    {
		        if (r[i] == '/'&&r[i + 1] == '/')
		        {//��Ϊ����ע�͡�//��,��ȥ��ע�ͺ���Ķ�����ֱ�������س�����
		            while (r[i] != '\n')
		            {
		                i++;//���ɨ��
		            }
		        }
		        if (r[i] == '/'&&r[i + 1] == '*')
		        {//��Ϊ����ע�͡�/* ������*/����ȥ��������
		            i += 2;
		            while (r[i] != '*' || r[i + 1] != '/')
		            {
		                i++;//����ɨ��
		                if (r[i] == '$')
		                {
		                    System.out.println("ע�ͳ���û���ҵ� */���������������\n");
		                    System.exit(0);
		                }
		            }
		            i += 2;//�����*/��
		        }
		        if (r[i] != '\n'&&r[i] != '\t'&&r[i] != '\t'&&r[i] != '\r')
		        {//�����������ַ�������ˣ��������
		            C[count++] = r[i];
		        }
		    }
		   C[count] = '\0';
		return C;
	}
}
