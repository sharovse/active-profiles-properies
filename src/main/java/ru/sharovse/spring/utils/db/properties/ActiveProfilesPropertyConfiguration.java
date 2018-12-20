package ru.sharovse.spring.utils.db.properties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveProfilesPropertyConfiguration {
	/** List resources.
	 * @return array
	 */
	String [] values();
	String codepage() default ActiveProfilesPropertyConstants.DEFAULT_CODEPAGE_PROPERTYES_FILE;
	
	/** String variable name for inject active profiles.
	 * @return
	 */
	String activeProfilesInjectName() default ActiveProfilesPropertyConstants.DEFAULT_ACTIVE_PROFILES_INJECT_NAME;
}
