package de.informera.dev.nutchManager.fileOperations;

import java.io.File;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import de.informera.dev.nutchManager.thirdParty.RegexURLFilter;

public class UrlFilterTest {
	RegexURLFilter ruf;
	
	public UrlFilterTest(File file) {
		try {
			ruf = new RegexURLFilter(file);
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String test(String url) {
		if(ruf != null){
			String test;
			test = ruf.filter(url);
			return test;
		}else{
			return null;
		}
	}
}
