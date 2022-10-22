
package com.example.crudweb.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;


/**
 * Thymeleaf configuration.
 */


public class ThymeleafConfig {

    private final String prefix = "/WEB-INF/templates/";
    private final String suffix = ".html";
    private final Long cacheTTLMs = 3600000L;
    private final TemplateEngine templateEngine;

    public ThymeleafConfig(final ServletContext ctx) {

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(ctx);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCacheTTLMs(cacheTTLMs);
        templateResolver.setCacheable(true);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}



