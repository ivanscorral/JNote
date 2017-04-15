package jnote.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
	
	public static boolean save(String text, String path){	
		File file = new File(path);
		FileWriter fw;
		
		try{
			fw = new FileWriter(file);
			fw.write(text);
			fw.flush();
			fw.close();
			return true;
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public static String open(String path){
		File file = new File(path);
		String result = "";
		FileReader fr;
		BufferedReader bfr;
		
		try{
			fr = new FileReader(file);
			bfr = new BufferedReader(fr);
			String line = "";
			
			while((line = bfr.readLine()) != null){
				result += line;
			}
			bfr.close();
			fr.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<Integer> find(String text, String find){
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for(int i = -1; (i = text.indexOf(find, i+1)) != -1;){
			result.add(i);
		}
		
		System.out.println("FIND TEXT ARGUMENT: " + text);
		
		return result;
	}
	
	public static String replace(String find, String replace, String text){
		String result;
		
		result = text.replace(find, replace);
		
		return result;
	}

}
