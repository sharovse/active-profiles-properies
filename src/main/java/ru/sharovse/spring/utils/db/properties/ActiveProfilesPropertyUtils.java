package ru.sharovse.spring.utils.db.properties;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class ActiveProfilesPropertyUtils {

	static final String LINE_REGEXP = "\n";
	static final Pattern SECTION_REGEXP = Pattern.compile("#\\[(.*)\\]");
	static final String VAR_DELIMETER = "=";
	static final String VAR_COMMENT = "#";
	static final String CLASSPATH_PREFIX = "classpath:";
	static final String NONE_SECTION = "";
	
	Map<String, Map<String, String>> readVars(ClassLoader loader, String resurce, String codepage) throws IOException{
		Map<String, Map<String, String>> map = new HashMap<>();
		String body = readFile(loader, resurce, codepage);
		if(body==null)
			return map;  
	    
		String []lines = body.split(LINE_REGEXP);
		String currendSection = NONE_SECTION;
		for (String line : lines) {
			final String section = getSection(line);
			if(section!=null) {
				currendSection = section;
			}else {
				addVar(map, currendSection, line);
			}
		}
		return map;
	}
	
	boolean isEmpty(String value){
		return (value==null || value.trim().equals(""));
	}
	
	void addVar(Map<String, Map<String, String>> map, String currendSection, String line) {
		if(isEmpty(line)){
			return;
		}
		final Map<String, String> vars;
		if(!map.containsKey(currendSection)) {
			vars = new HashMap<>();
			map.put(currendSection, vars);
		} else {
			vars = 	map.get(currendSection);
		}
		addLine(vars, line);
	}

	void addLine(Map<String, String> vars, String line) {
		if(isEmpty(line)) return;
		if(line.contains(VAR_DELIMETER)) {
			int pos = line.indexOf(VAR_DELIMETER);
			final String var = line.substring(0, pos);
			final String value = line.substring(pos+1).trim();
			if(!value.startsWith(VAR_COMMENT)) {
				vars.put(var.trim(), value);
			}
		}
	}

	String getSection(String line) {
		Matcher m = SECTION_REGEXP.matcher(line);
		return !m.find() ? null : m.group(1);
	}
	
	
	String readFile(ClassLoader classLoader, String resurce, String codepage) throws IOException {
		if(resurce.startsWith(CLASSPATH_PREFIX)) {
			return readFileClasspath(classLoader, resurce.substring(CLASSPATH_PREFIX.length()), codepage);
		} else {
			return readFileAbsolute(resurce, codepage);
		}
	}

	String readFileAbsolute(String resource, String codepage) throws IOException {
	    return FileUtils.readFileToString(new File(resource), codepage);
	}

	String readFileClasspath(ClassLoader classLoader, String resource, String codepage) throws IOException {
		URL url =  classLoader.getResource(resource);
		if(url==null) {
			throw new IOException("Not found "+resource);
		}
	    File file = new File(url.getFile());
	    return FileUtils.readFileToString(file, codepage);
	}
	
}
