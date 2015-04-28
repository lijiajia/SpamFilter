package com.lijj.spam.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SpamFilter {
	
	public String readContent(File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuilder content = new StringBuilder();
			boolean findSpace = false;
			for (String line=null; (line=br.readLine()) != null;) {
				if (line.length() == 0) {
					findSpace = true;
					continue;
				}
				if (findSpace) {
					content.append(line);
				} else {
					continue;
				}
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public static void main(String[] args) {
    	SpamFilter spamFilter = new SpamFilter();
    	File file = new File("D:\\mails\\spam");
    	File[] files = file.listFiles();
    	HashMap<String, Integer> spamMap = new HashMap<String, Integer>();
    	for (int i=0; i<files.length; i++) {
    		String content = spamFilter.readContent(files[i]);
    		String[] words = content.split(" ");
    		for (int j=0; j<words.length; j++) {
    			String key = words[j].toLowerCase();
    			if (key.length() > 0) {
    				if (spamMap.containsKey(key)) {
        				int num = spamMap.get(key);
        				spamMap.put(key, num + 1);
        			} else {
        				spamMap.put(key, 1);
        			}
    			}
    		}
    	}
    	
    	FileOutputStream fop = null;
    	File spamDictFile = new File("D:\\mails\\spam.dict");
    	try {
			fop = new FileOutputStream(spamDictFile);
	    	if (!spamDictFile.exists()) {
	    		spamDictFile.createNewFile();
	    	}
	    	byte[] contentInBytes = spamMap.toString().getBytes();
	    	fop.write(contentInBytes);
	    	fop.flush();
	        fop.close();
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    }
}
