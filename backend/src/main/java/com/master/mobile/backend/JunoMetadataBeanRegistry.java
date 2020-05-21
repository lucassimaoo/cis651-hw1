package com.master.mobile.backend;

import com.fanatics.juno.metadata.JunoComponent;
import com.fanatics.juno.metadata.JunoConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Register on the {@link BeanDefinitionRegistry} all {@link JunoComponent} and {@link
 * JunoConfiguration} available on the classpath.
 *
 * @author asantos
 * @see JunoConfiguration
 * @see PropertySource
 */
public class JunoMetadataBeanRegistry
    implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

  private static final String DEFAULT_PACKAGE_TO_SCAN = "com.fanatics";
  private static final String PROPERTY_DISABLED_FORMAT = "juno.metadata.%s.disabled";

  protected final Log logger = LogFactory.getLog(getClass());

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {}

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    for (Class<?> beanType : getConfigCandidates(getApplicationContext())) {
      final String beanName = beanType.getSimpleName();
      if (isBeanRegistered(beanType)) {
        logger.info("JunoComponent already registered: " + beanType);
      } else if (isBeanDisabled(beanName)) {
        logger.info("JunoComponent disabled by configuration: " + beanType);
      } else {
        logger.info("Registering JunoComponent/JunoConfiguration of type: " + beanType);
        registry.registerBeanDefinition(beanName, createBeanDefinition(beanType));
      }
    }
  }

  protected BeanDefinition createBeanDefinition(Class<?> beanType) {
    return BeanDefinitionBuilder.genericBeanDefinition(beanType).getBeanDefinition();
  }

  protected boolean isBeanRegistered(Class<?> beanType) {
    return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), beanType)
            .length
        > 0;
  }

  protected boolean isBeanDisabled(String beanName) {
    final String beanDisabledProperty = String.format(PROPERTY_DISABLED_FORMAT, beanName);
    return Boolean.valueOf(
        getApplicationContext().getEnvironment().getProperty(beanDisabledProperty));
  }

  private Set<Class<?>> getConfigCandidates(final BeanFactory beanFactory) {
    final ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(JunoComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(JunoConfiguration.class));
    return getConfigCandidates(beanFactory, scanner);
  }

  private Set<Class<?>> getConfigCandidates(
      final BeanFactory beanFactory, final ClassPathScanningCandidateComponentProvider scanner) {
    final Set<Class<?>> entitySet = new HashSet<Class<?>>();
    for (String basePackage : getMappingBasePackages(beanFactory)) {
      if (StringUtils.hasText(basePackage)) {
        for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
          try {
            entitySet.add(ClassUtils.forName(candidate.getBeanClassName(), null));
          } catch (ClassNotFoundException e) {
            logger.warn("JunoComponent candidate failed to load " + candidate.getBeanClassName());
          }
        }
      }
    }
    return entitySet;
  }

  private static Collection<String> getMappingBasePackages(final BeanFactory beanFactory) {
    final Set<String> packages = new LinkedHashSet<>();
    packages.add(DEFAULT_PACKAGE_TO_SCAN);
    packages.addAll(AutoConfigurationPackages.get(beanFactory));
    return packages;
  }
}
