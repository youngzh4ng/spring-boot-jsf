package de.fred4jupiter.spring.boot.jsf;

import com.sun.faces.config.ConfigureListener;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class JsfConfig extends SpringBootServletInitializer implements ServletContextAware {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    }

    @Bean
    public ServletRegistrationBean facesServlet() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.xhtml");
        registration.setName("FacesServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new CustomScopeRegisteringBeanFactoryPostProcessor();
    }
}
