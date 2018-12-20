package ru.sharovse.spring.utils.db.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.PropertySource;
import static ru.sharovse.spring.utils.db.properties.ActiveProfilesPropertyConstants.*
;/**
 * Property Source Active Propiles. Find value order:
 * <ol>
 * <li>Single profile</li>
 * <li>Combined profiles (overwrite single)</li>
 * </ol>
 * 
 * @author sharov
 *
 */
public class ActiveProfilesPropertySource extends PropertySource<String> {

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	Map<String, Map<String, String>> map;
	String[] profiles;
	String allProfiles = NONE_SECTION;
	String activePropertiesVarName;
	
	public static class StringValue {
		String value;
	}

	String getValue(String nameVar) {
		StringValue value = new StringValue();
		for (String profile : profiles) {
			setIfExistStringValue(value, profile, nameVar);
		}
		setIfExistStringValue(value, allProfiles, nameVar);
		return value.value;
	}

	void setIfExistStringValue(StringValue value, String profile, String nameVar) {
		if (map.containsKey(profile)) {
			Map<String, String> v = map.get(profile);
			if (v != null && v.containsKey(nameVar)) {
				value.value = v.get(nameVar);
			}
		}
	}

	public ActiveProfilesPropertySource(String name, String[] profiles, Map<String, Map<String, String>> map, String activePropertiesVarName) {
		super(name);
		this.map = map;
		this.profiles = profiles;
		this.activePropertiesVarName = activePropertiesVarName;
		if(profiles!=null){
			StringBuilder sb = new StringBuilder();
			for (String p : profiles) {
				if (sb.length()>0) {
					sb.append(PROFILE_DELIMETER);
				}
				sb.append(p);
			}
			this.allProfiles = sb.toString();
		}
	}

	Map<String, String> cache = new HashMap<>();

	boolean containsPropertyAndSetCached(String nameVar) {
		if (cache.containsKey(nameVar)) {
			return true;
		}
		final String value = getValue(nameVar);
		if (value != null) {
			cache.put(nameVar, value);
		}
		return cache.containsKey(nameVar);
	}

	String getCachedProperty(String nameVar) {
		return cache.get(nameVar);
	}

	@Override
	public boolean containsProperty(String nameVar) {
		if(activePropertiesVarName.equals(nameVar)){ 
			return true;
		}else{ 
			return containsPropertyAndSetCached(nameVar);
		}
	}

	@Override
	public Object getProperty(String nameVar) {
		if(activePropertiesVarName.equals(nameVar)){
			return allProfiles;
		}else{
			return getValue(nameVar);
		}
		
	}

}
