package 词法分析程序;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class FileRead {
	private static int sum ;
	
	public static int getsum() {
		return sum;
	}
	
	public static char[] FileReadB(String path) throws IOException {
		 //加载文件
		
		File  F=new File((System.getProperty("user.dir")+"\\"+path));
		FileInputStream fis=null;
		int count=0;
		
		char[] data= new char[10000];
		//创建文件读取流
		try {
			fis=new FileInputStream(F);	
			while((count=fis.read())!=-1){	//如果已到达文件末尾，则返回 -1。
				data[sum]=(char)count;
				sum++;
			}
			
		}
		catch (FileNotFoundException e) {		 
			e.printStackTrace();
		}
		finally{
			if(fis!=null){
				fis.close();
			}
		}
		return data;
		
	}
	
	public static char[][] FileReadS(String path) throws IOException {
		 //加载文件
		
		File  F=new File((System.getProperty("user.dir")+"\\"+path));
		FileInputStream fis=null;
		int z=0;		//记录"的数量
		int i=0;
		int count1=0;
		int s=0;
		char[][] data= new char[40][20];
		//创建文件读取流
		try {
			fis=new FileInputStream(F);	
			while((count1=fis.read())!=-1){	
				if((char)count1==',') {
					if(z>=2) {
						i++;
						s=0;
						z=0;
						continue;
					}
					else if(z==1) {
						data[i][s]=(char)count1;
						s++;
						continue;
					}
				}
				else if((char)count1=='"') {
					z++;//"标注
					if(z>=3) {
						data[i][s]=(char)count1;
						s++;
						continue;
					}
					continue;
				}
				else if((char)count1==' '||(char)count1=='\n'||(char)count1=='\r') {
					continue;
				}
				data[i][s]=(char)count1;
				s++;
			}
		}
		catch (FileNotFoundException e) {		 
			e.printStackTrace();
		}
		finally{
			if(fis!=null){
				fis.close();
			}
		}
		return data;
		
	}
}
