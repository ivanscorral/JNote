package jnote.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Preferences {
	
	private static File file = new File(File.separator + "preferences.txt");
	
	public static void add(String preference, String value){
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(":"+preference + "=" + value + ";");
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//For now this method only supports the reading of boolean values
	
	public static boolean get(String preference){
		String result = "";
		try{
			FileReader fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String linea;
			while((linea = bfr.readLine()) != null){
				result += linea;
			}
			bfr.close();
			fr.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		String chars = "";
		
		for(int i = result.indexOf(preference) + preference.length() + 1; result.charAt(i) != ';'; i++){
			
			chars += result.charAt(i);
			
		}
		if(chars.equals("true")){
			return true;
		}else{
			return false;
		}
	}
	

}
