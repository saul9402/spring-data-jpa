package com.bolsadeideas.springboot.datajpa.app;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		// con esto se mapea todo lo que està haciendo referencia a /uploads/ a una ruta
		// externa que en este caso seria file:/C:/Temp/uploads/ (se configura como
		// recurso estatico)
		// toUri, agrega el esquema "file:" a la ruta
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}*/

}
