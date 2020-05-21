package com.master.mobile.backend;

import com.fanatics.argos.context.ApplicationInfo;
import com.fanatics.argos.context.ArgosIdentity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JunoApplicationInfo implements InitializingBean {

  @Value("${spring.application.name}")
  private String name;

  @Value("${spring.application.version}")
  private String version;

  @Override
  public void afterPropertiesSet() throws Exception {
    ArgosIdentity.setApplicationInfo(new ApplicationInfo(name, version));
  }
}
