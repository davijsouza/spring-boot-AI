package com.TechEnd.AI;

import com.TechEnd.AI.entity.ValidatorBean;
import com.TechEnd.AI.util.authenticationValidator;
import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.send.MessengerSendClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "com.TechEnd" })
@EnableAutoConfiguration

public class SpringDocBotApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringDocBotApplication.class);

	/**
	 * Initializes the {@code MessengerSendClient}.
	 *
	 * @param pageAccessToken the generated {@code Page Access Token}
	 */
	@Bean
	public MessengerSendClient messengerSendClient(@Value("${messenger4j.pageAccessToken}") String pageAccessToken) {
		logger.debug("Initializing MessengerSendClient - pageAccessToken: {}", pageAccessToken);
		return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
	}

    @Bean
	public ValidatorBean validatorBean(@Value("${messenger4j.appSecret}") final String appSecret,
									   @Value("${messenger4j.verifyToken}") final String verifyToken) {
        return new ValidatorBean(appSecret, verifyToken);
    }
    @Bean
	public authenticationValidator authenticationValidator(ValidatorBean validatorBean,MessengerSendClient sendClient){
		return new authenticationValidator(validatorBean,sendClient);
	}
	@Bean
	public CallBackHandler callBackHandler(authenticationValidator authenticationValidator){
		return new CallBackHandler(authenticationValidator);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDocBotApplication.class, args);
	}
}
