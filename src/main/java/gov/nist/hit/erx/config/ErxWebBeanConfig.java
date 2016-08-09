package gov.nist.hit.erx.config;


import gov.nist.hit.core.service.CachedRepository;
import gov.nist.hit.core.service.edi.*;
import gov.nist.hit.core.service.xml.*;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.erx.core.service.ERXResourcebundleLoaderImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class ErxWebBeanConfig {

	static final Logger logger = LoggerFactory.getLogger(ErxWebBeanConfig.class);

//	@Bean
//	public CachedRepository cachedRepository() {
//		logger.info("bean for CachedRepository");
//		return new CachedRepository();
//	}

	// ERX specific  

	@Bean
	public ResourcebundleLoader resourcebundleLoader() {
		return new ERXResourcebundleLoaderImpl();
	}

	// EDI specific

	@Bean
	public EDIResourcebundleLoaderImpl ediResourcebundleLoaderImpl() {
		return new EDIResourcebundleLoaderImpl();
	}

	@Bean
	public EDIValidationReportConverter ediValidationReportConverter() {
	  return new EDIValidationReportConverterImpl ();
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

	@Bean
	public XMLResourcebundleLoaderImpl xmlResourcebundleLoaderImpl() {
		return new XMLResourcebundleLoaderImpl();
	}

	@Bean
	public XMLValidationReportConverter xmlValidationReportConverter() { return new XMLValidationReportConverterImpl(); }

	@Bean
	public XMLMessageValidator xmlMessageValidator() {
	 return new XMLMessageValidatorImpl();
	}

	@Bean
	public XMLMessageParser xmlMessageParser() {
	return new XMLMessageParserImpl();
	}
	
}
