package gov.nist.hit.erx.config;


import gov.nist.hit.core.service.edi.EDIMessageParser;
import gov.nist.hit.core.service.edi.EDIMessageParserImpl;
import gov.nist.hit.core.service.edi.EDIMessageValidator;
import gov.nist.hit.core.service.edi.EDIMessageValidatorImpl;
import gov.nist.hit.core.service.edi.EDIResourcebundleLoaderImpl;
import gov.nist.hit.core.service.edi.EDIValidationReportGenerator;
import gov.nist.hit.core.service.edi.EDIValidationReportGeneratorImpl;
import gov.nist.hit.core.service.xml.XMLMessageParser;
import gov.nist.hit.core.service.xml.XMLMessageParserImpl;
import gov.nist.hit.core.service.xml.XMLMessageValidator;
import gov.nist.hit.core.service.xml.XMLMessageValidatorImpl;
import gov.nist.hit.core.service.xml.XMLResourcebundleLoaderImpl;
import gov.nist.hit.core.service.xml.XMLValidationReportGenerator;
import gov.nist.hit.core.service.xml.XMLValidationReportGeneratorImpl;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.erx.core.service.ERXResourcebundleLoaderImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class ErxWebBeanConfig {

	static final Logger logger = LoggerFactory.getLogger(ErxWebBeanConfig.class);

	// ERX specific  

	@Bean
	public ResourcebundleLoader resourcebundleLoader() {
		return new XMLResourcebundleLoaderImpl();
	}

	// EDI specific

	//@Bean
	//public EDIResourcebundleLoaderImpl ediResourcebundleLoaderImpl() {
	//	return new EDIResourcebundleLoaderImpl();
	//}

	@Bean
	public EDIValidationReportGenerator  ediValidationReportGenerator() {
	  return new EDIValidationReportGeneratorImpl ();
	}

	@Bean
	public EDIMessageValidator ediMessageValidator() {
	 return new EDIMessageValidatorImpl();
	}

	@Bean
	public EDIMessageParser ediMessageParser() {
	return new EDIMessageParserImpl();
	}

	// XML specific

	//@Bean
	//public XMLResourcebundleLoaderImpl xmlResourcebundleLoaderImpl() {
	//	return new XMLResourcebundleLoaderImpl();
	//}

	@Bean
	public XMLValidationReportGenerator  xmlValidationReportGenerator() {
	  return new XMLValidationReportGeneratorImpl ();
	}

	@Bean
	public XMLMessageValidator xmlMessageValidator() {
	 return new XMLMessageValidatorImpl();
	}

	@Bean
	public XMLMessageParser xmlMessageParser() {
	return new XMLMessageParserImpl();
	}
	
}
