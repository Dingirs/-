package LL1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws Exception {
		// // LL��1���ķ���������
		ArrayList<String> gsArray = new ArrayList<String>();
		String path = "rules.txt";
		String pathC ="E:\\�����ļ�\\�ʷ���������\\����.txt";
		/*
		Fileread fileread=new Fileread();
		fileread.receiveC(pathC);
		ArrayList<String> strC=fileread.getStr();
		HashMap<String ,TreeSet<Integer>> synC=fileread.getsyn();
		*/
		Gs gs = new Gs();
		initGs(gsArray,path);
		gs.setGsArray(gsArray);
		gs.getNvNt();
		gs.initExpressionMaps();
		gs.getFirst();
		// ���ÿ�ʼ��
		gs.setS('S');
		gs.getFollow();
		gs.getSelect();
		// ����һ��������
		Analyzer analyzer = new Analyzer();
		analyzer.setStartChar('S');
		analyzer.setLl1Gs(gs);
		analyzer.setStr("a=i+i*i+1#");
		analyzer.analyze();
		gs.genAnalyzeTable();
		System.out.println("");
	}


	/**
	 * ��ʼ��LL(1)�ķ�
	 * 
	 * @param gsArray
	 * @throws IOException 
	 */
	private static void initGs(ArrayList<String> gsArray,String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);  
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");  
        BufferedReader bufReader = new BufferedReader(isr);  
        String line=null;
        while((line=bufReader.readLine())!=null) {
        	gsArray.add(line);
        }
	}

}




