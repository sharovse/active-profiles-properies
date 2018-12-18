package ru.sharovse.spring.utils.db.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.PropertySource;

/**
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

	static final Object PROFILE_DELIMETER = ",";
	Map<String, Map<String, String>> map;
	String[] profiles;
	String allProfiles;

	class StringValue {
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

	private void setIfExistStringValue(StringValue value, String profile, String nameVar) {
		if (map.containsKey(profile)) {
			Map<String, String> v = map.get(profile);
			if (v != null && v.containsKey(nameVar)) {
				value.value = v.get(name);
			}
		}
	}

	public ActiveProfilesPropertySource(String name, String[] profiles, Map<String, Map<String, String>> map) {
		super(name);
		this.map = map;
		this.profiles = profiles;
		StringBuilder sb = new StringBuilder();
		for (String p : profiles) {
			if (!p.isEmpty()) {
				sb.append(PROFILE_DELIMETER);
			}
			sb.append(p);
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
		return containsPropertyAndSetCached(nameVar);
	}

	@Override
	public Object getProperty(String nameVar) {
		return getValue(nameVar);
	}

}
