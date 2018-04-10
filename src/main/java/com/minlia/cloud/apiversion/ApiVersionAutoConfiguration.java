package com.minlia.cloud.apiversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author will
 */
@Configuration
@ConditionalOnProperty(prefix = "system.api-version.handler", name = "enabled", havingValue = "true")
public class ApiVersionAutoConfiguration {

  @Configuration
  public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ApiVersionProperties versionProperties;

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
      return new ApiVersionRequestHandlerMapping(versionProperties);
    }
  }
}
