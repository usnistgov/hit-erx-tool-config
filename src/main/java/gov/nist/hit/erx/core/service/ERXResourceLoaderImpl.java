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

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.nist.hit.core.domain.*;
import gov.nist.hit.core.service.ResourceLoader;
import gov.nist.hit.core.service.edi.EDIResourceLoaderImpl;
import gov.nist.hit.core.service.exception.ProfileParserException;
import gov.nist.hit.core.service.util.FileUtil;
import gov.nist.hit.core.service.xml.XMLResourceLoaderImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.resource.XMLResource;

@Component("erxResourceLoaderImpl")
public class ERXResourceLoaderImpl extends ResourceLoader {

  static final Logger logger = LoggerFactory.getLogger(ERXResourceLoaderImpl.class);
  
  @Autowired EDIResourceLoaderImpl edirb;

  @Autowired XMLResourceLoaderImpl xmlrb;

  public ERXResourceLoaderImpl() {}

  @PostConstruct
  public void load() throws JsonProcessingException, IOException, ProfileParserException {
    logger.info("loading ERXResourcebundle");
    super.load();
  }

  @Override public List<ResourceUploadStatus> addOrReplaceValueSet(String rootPath) throws IOException {
    System.out.println("AddOrReplace VS");

    List<Resource> resources;
    try {
      resources = this.getApiResources("*.xml",rootPath);
      if (resources == null || resources.isEmpty()) {
        ResourceUploadStatus result = new ResourceUploadStatus();
        result.setType(ResourceType.VALUESETLIBRARY);
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage("No resource found");
        return Arrays.asList(result);
      }
    } catch (IOException e1) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.VALUESETLIBRARY);
      result.setStatus(ResourceUploadResult.FAILURE);
      result.setMessage("Error while parsing resources");
      return Arrays.asList(result);
    }

    List<ResourceUploadStatus> results = new ArrayList<ResourceUploadStatus>();

    for (Resource resource : resources) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.VALUESETLIBRARY);
      String content = FileUtil.getContent(resource);
      try {
        VocabularyLibrary vocabLibrary = vocabLibrary(content);
        result.setId(vocabLibrary.getSourceId());
        VocabularyLibrary exist = this.getVocabularyLibrary(vocabLibrary.getSourceId());
        if (exist != null) {
          System.out.println("Replace");
          result.setAction(ResourceUploadAction.UPDATE);
          vocabLibrary.setId(exist.getId());
          vocabLibrary.setSourceId(exist.getSourceId());
        } else {
          result.setAction(ResourceUploadAction.ADD);
        }

        this.vocabularyLibraryRepository.save(vocabLibrary);
        result.setStatus(ResourceUploadResult.SUCCESS);

      } catch (Exception e) {
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage(e.getMessage());
      }
      results.add(result);
    }
    return results;
  }

  @Override
  public List<ResourceUploadStatus> addOrReplaceConstraints(String rootPath) {
    System.out.println("AddOrReplace Constraints");

    List<Resource> resources;
    try {
      resources = this.getApiResources("*.xml",rootPath);
      if (resources == null || resources.isEmpty()) {
        ResourceUploadStatus result = new ResourceUploadStatus();
        result.setType(ResourceType.CONSTRAINTS);
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage("No resource found");
        return Arrays.asList(result);
      }
    } catch (IOException e1) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.CONSTRAINTS);
      result.setStatus(ResourceUploadResult.FAILURE);
      result.setMessage("Error while parsing resources");
      return Arrays.asList(result);
    }

    List<ResourceUploadStatus> results = new ArrayList<ResourceUploadStatus>();

    for (Resource resource : resources) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.CONSTRAINTS);
      String content = FileUtil.getContent(resource);
      try {
        Constraints constraint = constraint(content);
        result.setId(constraint.getSourceId());
        Constraints exist = this.getConstraints(constraint.getSourceId());
        if (exist != null) {
          System.out.println("Replace");
          result.setAction(ResourceUploadAction.UPDATE);
          constraint.setId(exist.getId());
          constraint.setSourceId(exist.getSourceId());
        } else {
          result.setAction(ResourceUploadAction.ADD);
          System.out.println("Add");
        }

        this.constraintsRepository.save(constraint);
        result.setStatus(ResourceUploadResult.SUCCESS);

      } catch (Exception e) {
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage(e.getMessage());
      }
      results.add(result);
    }
    return results;
  }

  @Override
  public List<ResourceUploadStatus> addOrReplaceIntegrationProfile(String rootPath) {
    System.out.println("AddOrReplace integration profile");

    List<Resource> resources;
    try {
      resources = this.getApiResources("*.xml",rootPath);
      if (resources == null || resources.isEmpty()) {
        ResourceUploadStatus result = new ResourceUploadStatus();
        result.setType(ResourceType.PROFILE);
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage("No resource found");
        return Arrays.asList(result);
      }
    } catch (IOException e1) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.PROFILE);
      result.setStatus(ResourceUploadResult.FAILURE);
      result.setMessage("Error while parsing resources");
      return Arrays.asList(result);
    }

    List<ResourceUploadStatus> results = new ArrayList<ResourceUploadStatus>();
    for (Resource resource : resources) {
      ResourceUploadStatus result = new ResourceUploadStatus();
      result.setType(ResourceType.PROFILE);
      String content = FileUtil.getContent(resource);
      try {
        IntegrationProfile integrationP = integrationProfile(content);
        result.setId(integrationP.getSourceId());
        IntegrationProfile exist = this.integrationProfileRepository
            .findBySourceId(integrationP.getSourceId());
        if (exist != null) {
          System.out.println("Replace");
          result.setAction(ResourceUploadAction.UPDATE);
          integrationP.setId(exist.getId());
          integrationP.setSourceId(exist.getSourceId());
        } else {
          result.setAction(ResourceUploadAction.ADD);
          System.out.println("Add");
        }

        this.integrationProfileRepository.save(integrationP);
        result.setStatus(ResourceUploadResult.SUCCESS);
      } catch (Exception e) {
        result.setStatus(ResourceUploadResult.FAILURE);
        result.setMessage(e.getMessage());
      }
      results.add(result);
    }
    return results;

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
  public TestContext testContext(String path, JsonNode formatObj, TestingStage stage, String rootPath) throws IOException {
    TestContext res = edirb.testContext(path, formatObj, stage,rootPath);
    if (res != null) return res;

    res = xmlrb.testContext(path,formatObj,stage,rootPath);

    return res;
  }

  // Methods not meant to be public exposed
  @Override
  public ProfileModel parseProfile(String integrationProfileXml, String conformanceProfileId,
      String constraintsXml, String additionalConstraintsXml) throws ProfileParserException {
    return edirb.parseProfile(integrationProfileXml, conformanceProfileId, constraintsXml,
            additionalConstraintsXml);
  }

  @Override
  public VocabularyLibrary vocabLibrary(String content) throws JsonGenerationException,
      JsonMappingException, IOException {
    return edirb.vocabLibrary(content);
  }


}
