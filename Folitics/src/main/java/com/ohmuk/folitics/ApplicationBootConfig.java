package com.ohmuk.folitics;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.ohmuk.folitics.notification.NotificationHandler;

/**
 * This configuration class has three responsibilities:
 * <ol>
 * <li>It enables the auto configuration of the Spring application context.</li>
 * <li>
 * It ensures that Spring looks for other components (controllers, services, and
 * repositories) from the <code>com.ohmuk.folitics.bootrest</code> package.</li>
 * <li>It launches our application in the main() method.</li>
 * </ol>
 * 
 * @author Jahid Ali
 */
@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
@ComponentScan(value = "com")
@EnableTransactionManagement
@EnableLoadTimeWeaving
public class ApplicationBootConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private NotificationHandler notificationHandler;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(
				ApplicationBootConfig.class, args);
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.registerModule(new Hibernate4Module());

		messageConverter.setObjectMapper(mapper);
		return messageConverter;

	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(jacksonMessageConverter());
		super.configureMessageConverters(converters);
	}

	/**
	 * View resolver mapping
	 * 
	 */

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("index");
		registry.addViewController("/403").setViewName("403");
	}

	/*
	 * 
	 * @return InternalResourceViewResolver
	 */

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");
		resolver.setSuffix(".html");
		return resolver;
	}

	/**
	 * @author Abhishek
	 * 
	 * 
	 *         This method basically loads the custom messages from the
	 *         messages.properties file which is kept in
	 *         src/main/resources/messages These messages will be returned if
	 *         server side validation fails.
	 * 
	 * @return ReloadableResourceBundleMessageSource - the message source for
	 *         custom messages
	 */
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(notificationHandler);
	}

}
