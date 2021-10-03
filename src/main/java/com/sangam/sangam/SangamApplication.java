package com.sangam.sangam;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.ServletContextAware;

import com.sangam.sangam.utils.MyConfiguration;
import com.sangam.sangam.web.security.config.AppProperties;
import com.sun.faces.config.ConfigureListener;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(AppProperties.class)
@EnableAspectJAutoProxy
public class SangamApplication extends SpringBootServletInitializer implements ServletContextAware,CommandLineRunner {

	@Autowired
    private MyConfiguration myConfig;
	
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(SangamApplication.class);
	  }
	
	public static void main(String[] args) {
		SpringApplication.run(SangamApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
	            new FacesServlet(), "*.xhtml");
	    servletRegistrationBean.setLoadOnStartup(1);
	    return servletRegistrationBean;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
	    servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration",
	            Boolean.TRUE.toString());
	    servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
	    //More parameters...
	}
	
    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(
            new ConfigureListener());
    }

    public void run(String... args) throws Exception {
        System.out.println("Loaded Environment: " + myConfig.getName());
    }

}
