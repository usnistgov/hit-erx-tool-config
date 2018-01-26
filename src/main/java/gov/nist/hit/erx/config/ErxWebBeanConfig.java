package gov.nist.hit.erx.config;


import gov.nist.hit.core.service.BundleHandler;
import gov.nist.hit.core.service.ResourceLoader;
import gov.nist.hit.core.service.edi.*;
import gov.nist.hit.core.service.xml.*;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.erx.core.service.ERXBundleHandlerImpl;
import gov.nist.hit.erx.core.service.ERXResourceLoaderImpl;

import org.springframework.beans.factory.annotation.Qualifier;
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

	@Bean(name = "resourceLoader")
	public ResourceLoader resourceLoader() {
		return new ERXResourceLoaderImpl();
	}

	@Bean
	public BundleHandler bundleHandler() { return new ERXBundleHandlerImpl(); }

	// EDI specific

	@Bean(name = "ediResourceLoader")
	public EDIResourceLoader ediResourceLoader() {
		return new EDIResourceLoaderImpl();
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

	@Bean(name = "xmlResourceLoader")
	public XMLResourceLoader xmlResourceLoader() {
		return new XMLResourceLoaderImpl();
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
