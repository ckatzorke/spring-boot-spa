package io.katzorke.springbootspa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.katzorke.springbootspa.web.RouteAwareResourceResolver;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${spa.root.location}")
	private String spaRoot;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**/*").addResourceLocations(spaRoot).resourceChain(true)
				.addResolver(new RouteAwareResourceResolver(spaRoot, this.resourceLoader));
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addRedirectViewController("/", "/index.html");
	}

}
