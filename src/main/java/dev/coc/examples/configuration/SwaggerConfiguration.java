package dev.coc.examples.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Swagger configuration
 * @author Mario Diaz
 *
 */
@Configuration
@PropertySource({"classpath:swagger.properties"})
public class SwaggerConfiguration {  

	private @Value("${swagger.info.title}") String name;
	private @Value("${swagger.info.description}") String description;
	private @Value("${swagger.info.version}") String version;
	private @Value("${swagger.info.termsOfServiceUrl}") String termsOfServiceUrl;
	private @Value("${swagger.license.name}") String licenseName;
	private @Value("${swagger.license.url}") String licenseUrl;
	private @Value("${swagger.contact.name}") String contactName;
	private @Value("${swagger.contact.url}") String contactUrl;
	private @Value("${swagger.contact.email}") String contactEmail;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(name)
						.description(description)
						.version(version)
						.termsOfService(termsOfServiceUrl)
						.contact(new Contact().name(contactName).url(contactUrl).email(contactEmail))
						.license(new License().name(licenseName).url(licenseUrl)));
	}

}
