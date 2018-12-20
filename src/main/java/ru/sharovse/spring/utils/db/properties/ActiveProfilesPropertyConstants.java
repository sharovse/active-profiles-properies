package ru.sharovse.spring.utils.db.properties;

import java.util.regex.Pattern;

public class ActiveProfilesPropertyConstants {

	public static final String DEFAULT_ACTIVE_PROFILES_INJECT_NAME = "active-profiles";
	public static final String DEFAULT_CODEPAGE_PROPERTYES_FILE = "UTF-8";
	
	public static final String LINE_REGEXP = "\n";
	public static final Pattern SECTION_REGEXP = Pattern.compile("#\\[(.*)\\]");
	
	public static final Object PROFILE_DELIMETER = ",";
	
	public static final String VAR_DELIMETER = "=";
	public static final String VAR_COMMENT = "#";
	public static final String CLASSPATH_PREFIX = "classpath:";
	public static final String NONE_SECTION = "";
	
	private ActiveProfilesPropertyConstants() {
		super();
	}
	
}
