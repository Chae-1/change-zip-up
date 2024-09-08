package com.kosa.chanzipup.config.payment;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentModuleConfig {
    @Bean
    public IamportClient iamportClient(@Value("${iamport.api-key}") String apiKey,
                                       @Value("${iamport.secret-key}") String secretKey) {
        return new IamportClient(apiKey, secretKey);
    }
}
