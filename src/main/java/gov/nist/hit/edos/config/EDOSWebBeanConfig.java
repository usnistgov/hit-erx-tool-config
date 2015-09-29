package gov.nist.hit.edos.config;


import gov.nist.hit.core.hl7v2.service.HL7V2MessageParser;
import gov.nist.hit.core.hl7v2.service.HL7V2MessageParserImpl;
import gov.nist.hit.core.hl7v2.service.HL7V2MessageValidator;
import gov.nist.hit.core.hl7v2.service.HL7V2MessageValidatorImpl;
import gov.nist.hit.core.hl7v2.service.HL7V2ResourcebundleLoaderImpl;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.ValidationReportGenerator;
import gov.nist.hit.core.service.ValidationReportGeneratorImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EDOSWebBeanConfig {

	@Bean
	  public ResourcebundleLoader resourcebundleLoader() {
	      return new HL7V2ResourcebundleLoaderImpl();
	  }

	  @Bean
	  public ValidationReportGenerator  hl7v2ValidationReportGenerator() {
	      return new ValidationReportGeneratorImpl ();
	  }

	  @Bean
	  public HL7V2MessageValidator hl7v2MessageValidator() {
	     return new HL7V2MessageValidatorImpl();
	  }
	  
	  @Bean
	  public HL7V2MessageParser hl7v2MessageParser() {
	    return new HL7V2MessageParserImpl();
	  }
	
}
