package com.flashbackmc.mcdiscordbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleOutput {

	public ConsoleOutput() {
		//execCmd(cmd);
	}
	
	public static String execCmd(String cmd) {
	    String result = null;
	    try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
	            Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
	        result = s.hasNext() ? s.next() : null;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
