package gov.nist.hit.erx.config;


import gov.nist.hit.core.service.edi.EDIMessageParser;
import gov.nist.hit.core.service.edi.EDIMessageParserImpl;
import gov.nist.hit.core.service.edi.EDIMessageValidator;
import gov.nist.hit.core.service.edi.EDIMessageValidatorImpl;
import gov.nist.hit.core.service.edi.EDIResourcebundleLoaderImpl;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.ValidationReportGenerator;
import gov.nist.hit.core.service.ValidationReportGeneratorImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ErxWebBeanConfig {

	  @Bean
	  public ResourcebundleLoader resourcebundleLoader() {
	      return new EDIResourcebundleLoaderImpl();
	  }

	  @Bean
	  public ValidationReportGenerator  ediValidationReportGenerator() {
	      return new ValidationReportGeneratorImpl ();
	  }

	  @Bean
	  public EDIMessageValidator ediMessageValidator() {
	     return new EDIMessageValidatorImpl();
	  }
	  
	  @Bean
	  public EDIMessageParser ediMessageParser() {
	    return new EDIMessageParserImpl();
	  }
	
}
