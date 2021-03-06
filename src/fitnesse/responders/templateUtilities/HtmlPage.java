// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package fitnesse.responders.templateUtilities;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeInstance;

import fitnesse.wiki.WikiPageActions;

public class HtmlPage {
  private static final String HEADER_TEMPLATE = "pageTitle.vm";
  private static final String TITLE = "FitNesse";

  private VelocityEngine velocityEngine;
  private VelocityContext velocityContext;
  
  private String templateFileName;
  
  public HtmlPage(VelocityEngine velocityEngine, String templateFileName, String theme) {
    super();
    
    this.velocityEngine = velocityEngine;
    this.templateFileName = templateFileName;

    velocityContext =  new VelocityContext();

    setHeaderTemplate(HEADER_TEMPLATE);
    setTitle("FitNesse");
    velocityContext.put("theme", theme);
  }

  public void setHeaderTemplate(String headerTemplate) {
    velocityContext.put("headerTemplate", ensureSuffix(headerTemplate));
  }
  
  public void setNavTemplate(String navTemplate) {
    velocityContext.put("navTemplate", ensureSuffix(navTemplate));
  }
  
  /**
   * Define main (article) template for the file. This file is also set as the default class
   * on the body tag (bodyClass).
   * 
   * @param mainTemplate
   */
  public void setMainTemplate(String mainTemplate) {
    setBodyClass(mainTemplate);
    velocityContext.put("mainTemplate", ensureSuffix(mainTemplate));
  }

  public void setFooterTemplate(String footerTemplate) {
    velocityContext.put("footerTemplate", ensureSuffix(footerTemplate));
  }

  public String ensureSuffix(String templateName) {
    if (templateName.endsWith(".vm")) {
      return templateName;
    }
    return templateName + ".vm";
  }

  public void put(String key, Object value) {
    velocityContext.put(key, value);
  }
  
  public String html() {
    StringWriter writer = new StringWriter();
    render(writer);
    return writer.toString();
  }

  public void render(Writer writer) {
    Template template = velocityEngine.getTemplate(templateFileName);
    template.merge(velocityContext, writer);
  }


  public void setTitle(String title) {
    velocityContext.put("title", title);
  }

  public void setPageTitle(PageTitle pageTitle) {
    velocityContext.put("pageTitle", pageTitle);
  }

  public void setBodyClass(String bodyClass) {
    velocityContext.put("bodyClass", bodyClass);
  }
}
