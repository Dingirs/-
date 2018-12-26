package LL1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Fileread {
	private static ArrayList<String> str;
	
	private static HashMap<String ,TreeSet<Integer>> syn;

	public ArrayList<String> getStr() {
		return str;
	}

	public void setStr(ArrayList<String> str) {
		this.str = str;
	}

	public HashMap<String,TreeSet<Integer>> getsyn() {
		return syn;
	}

	public void setsyn(HashMap<String, TreeSet<Integer>> str1) {
		this.syn = str1;
	} 
	
	public void receiveC(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);  
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");  
        BufferedReader bufReader = new BufferedReader(isr);  
        
        str=new ArrayList<String>();
        syn=new	HashMap<String ,TreeSet<Integer>>();
        String[] divide =null;
        String line=null;
        while((line=bufReader.readLine())!=null) {
        	TreeSet<Integer> tem = new TreeSet<Integer>();
        	divide =line.split(",");
        	str.add(divide[0]);
        	tem.add(Integer.valueOf(divide[1]));
        	syn.put(divide[0], tem);
        }
	}
}
