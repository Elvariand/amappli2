package isika.p3.amappli.config;

import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(@NonNull ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(WebMvcConfig.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"SpringDispatcher", new DispatcherServlet(appContext));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		// Configure multipart handling
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
				System.getProperty("java.io.tmpdir"),  // Temporary location for files
                20971520,        // Max file size: 20MB
                41943040,        // Max request size: 40MB
                0                // File size threshold: Write directly to disk
        );
        dispatcher.setMultipartConfig(multipartConfig);

		// Security Filter
		FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		springSecurityFilterChain.addMappingForUrlPatterns(null,false,"/*");
	
	}

	

}
