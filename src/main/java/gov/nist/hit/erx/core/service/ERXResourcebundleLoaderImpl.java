/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 */

package gov.nist.hit.erx.core.service;

import gov.nist.hit.core.domain.ProfileModel;
import gov.nist.hit.core.domain.TestCaseDocument;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.domain.TestingStage;
import gov.nist.hit.core.domain.VocabularyLibrary;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.edi.EDIResourcebundleLoaderImpl;
import gov.nist.hit.core.service.exception.ProfileParserException;
import gov.nist.hit.core.service.xml.XMLResourcebundleLoaderImpl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ERXResourcebundleLoaderImpl extends ResourcebundleLoader {

  static final Logger logger = LoggerFactory.getLogger(ERXResourcebundleLoaderImpl.class);
  
  @Autowired
  EDIResourcebundleLoaderImpl edirb;

  @Autowired
  XMLResourcebundleLoaderImpl xmlrb;

  public ERXResourcebundleLoaderImpl() {}

  @PostConstruct
  public void postConstruct(){
    try{
      this.load();
    } catch (Exception e){
      logger.debug("[ERROR Loading Resource Bundle]",e);
    }
  }

  @Override
  public TestCaseDocument generateTestCaseDocument(TestContext c) throws IOException {
    if (c == null) return new TestCaseDocument();
    else {
      if ("edi".equals(c.getFormat())){
        return edirb.generateTestCaseDocument(c);
      }
      if ("xml".equals(c.getFormat())){
        return xmlrb.generateTestCaseDocument(c);
      }
      return new TestCaseDocument();
    }
  }

  @Override
  public TestContext testContext(String path, JsonNode formatObj, TestingStage stage) throws IOException {
    TestContext res = edirb.testContext(path, formatObj, stage);
    if (res != null) return res;

    res = xmlrb.testContext(path,formatObj,stage);
    if (res != null) return res;

    return res;
  }

  // Methods not meant to be public exposed
  @Override
  public ProfileModel parseProfile(String integrationProfileXml, String conformanceProfileId,
      String constraintsXml, String additionalConstraintsXml) throws ProfileParserException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected VocabularyLibrary vocabLibrary(String content) throws JsonGenerationException,
      JsonMappingException, IOException {
    throw new UnsupportedOperationException();
  }


}
