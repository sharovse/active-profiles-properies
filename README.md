# Active profiles properties

Append to Spring annotation @ActiveProfilesPropertyConfiguration. 
Read active profile section properties.

Example set active profiles:

    mvn clean test -Dspring.profiles.active=profile1,profile2...profileN

Add depepency your project:

    <dependency>
      <groupId>ru.sharovse.spring-utils</groupId>
      <artifactId>active-profiles-properties</artifactId>
      <version>$VERSION</version>
    </dependency>

Java Class sample:

    @ActiveProfilesPropertyConfiguration(values= {"classpath:active-profiles.properties"},codepage="UTF-8")
    public class TestStarter  implements CommandLineRunner {
	
	@Value("${URL}")
	private String url;

	@Value("${active-profiles}")
	private String profiles;		


Properties file sample for combination active profiles: local, dev, st, sigma, alpha.

    #[local,alpha]
      URL=local-alpha

    #[local,sigma]
      URL=local-sigma

    #[st,alpha]
      URL=st-alpha

    #[st,sigma]
      URL=st-sigma

    #[local]
      URL=local
      STEND=local
      SERVICE_ID=SE000123l

    #[st]
      STEND=st
      SERVICE_ID=SE000123s

    #[alpha]
      VALUE_DB_WRITE_PROPERTY=file.txt

    #[sigma]
      VALUE_DB_READ_PROPERTY=file.txt

Sample:

      mvn clean test -Dspring.profiles.active=local,alpha

Variables:

	VALUE_DB_WRITE_PROPERTY=file.txt
	STEND=local
	SERVICE_ID=SE000123l
	URL=local-alpha

Class value variable:

	url=local-alpha
	profiles=local,alpha
	

Find value order:
   * Single profile.
   * Combined profiles (overwrite single).
  
