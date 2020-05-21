package com.master.mobile.backend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JunoMetadataConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public JunoMetadataBeanRegistry junoMetadataBeanRegistry() {
    return new JunoMetadataBeanRegistry();
  }

  @Bean
  @ConditionalOnMissingBean
  public JunoConfigurationBeanBinding junoConfigurationBinding() {
    return new JunoConfigurationBeanBinding();
  }
}
