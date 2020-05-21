package com.master.mobile.backend;

import com.fanatics.juno.metadata.JunoConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Bind {@link Environment} properties (loaded from the YAML) to a Configuration POJO.
 *
 * @author asantos
 * @see JunoConfiguration
 * @see PropertySource
 */
public class JunoConfigurationBeanBinding implements BeanPostProcessor, ApplicationContextAware {

  private static final String CONFIG_FOLDER = "config/";

  private static final String CONFIG_PREFIX = "juno-config-";

  protected final Log LOGGER = LogFactory.getLog(getClass());

  private ApplicationContext applicationContext;

  private ResourceLoader resourceLoader = new DefaultResourceLoader();

  private List<String> junoPropertySources = new LinkedList<>();

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  private ConfigurableEnvironment getConfigurableEnvironment() {
    return (ConfigurableEnvironment) getApplicationContext().getEnvironment();
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    JunoConfiguration annotation =
        AnnotationUtils.findAnnotation(bean.getClass(), JunoConfiguration.class);
    if (annotation != null) {
      processPropertySource(bean, beanName, annotation);
      processPropertyBean(bean, beanName, annotation);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  protected void processPropertyBean(
      final Object bean, final String beanName, final JunoConfiguration junoConfiguration) {

    LOGGER.info("Binding JunoConfiguration: " + junoConfiguration.value());

    Iterable<ConfigurationPropertySource> sources =
        ConfigurationPropertySources.get(getConfigurableEnvironment());

    Binder binder = new Binder(sources);
    try {
      binder.bind(junoConfiguration.value(), Bindable.ofInstance(bean));
    } catch (Exception bindErr) {
      String targetClass = ClassUtils.getShortName(bean.getClass());
      throw new BeanInitializationException(
          "Could not bind properties to JunoConfiguration: " + targetClass, bindErr);
    }
  }

  protected void processPropertySource(
      Object bean, String beanName, final JunoConfiguration junoConfiguration) {
    final String name = CONFIG_PREFIX + junoConfiguration.value();

    for (String extension : new YamlPropertySourceLoader().getFileExtensions()) {

      final String filename = junoConfiguration.value() + "." + extension;

      if (!tryLoadResource(name, CONFIG_FOLDER + filename)) {
        tryLoadResource(name, filename);
      }
    }
  }

  private boolean tryLoadResource(final String name, final String filename) {
    final Resource resource = this.resourceLoader.getResource(filename);
    try {
      if (resource.exists() && !isResourceLoaded(resource)) {
        LOGGER.info("Loading JunoConfiguration: " + resource.getURL());
        addPropertySource(resource, new YamlPropertySourceLoader().load(name, resource));
        return true;
      }
    } catch (Exception ex) {
      throw new BeanInitializationException(
          "Could not load default JunoConfiguration: " + resource.getFilename(), ex);
    }
    return false;
  }

  private void addPropertySource(Resource resource, final List<PropertySource<?>> propertySources)
      throws IOException {
    for (PropertySource<?> source : propertySources) {
      getConfigurableEnvironment().getPropertySources().addLast(source);
    }
    registerLoadedResource(resource);
  }

  private boolean isResourceLoaded(Resource resource) throws IOException {
    return this.junoPropertySources.contains(resource.getURL().toString());
  }

  private void registerLoadedResource(Resource resource) throws IOException {
    this.junoPropertySources.add(resource.getURL().toString());
  }
}
