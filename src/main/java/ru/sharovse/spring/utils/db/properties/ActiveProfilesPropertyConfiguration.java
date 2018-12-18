package ru.sharovse.spring.utils.db.properties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveProfilesPropertyConfiguration {
	/** List resources.
	 * @return array
	 */
	String [] values();
	String codepage() default "UTF-8";
}
