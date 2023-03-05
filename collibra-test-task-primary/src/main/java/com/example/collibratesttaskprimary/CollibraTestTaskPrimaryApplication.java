package com.example.collibratesttaskprimary;

import com.example.collibratesttaskprimary.config.PrimaryMessagingConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(
        PrimaryMessagingConfigProperties.class
)
@SpringBootApplication
public class CollibraTestTaskPrimaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollibraTestTaskPrimaryApplication.class, args);
    }

    /*@Bean
    ApplicationRunner runner(PulsarTemplate<String> pulsarTemplate) {
        return (args) -> pulsarTemplate.send("collibra-asset-promotion", "Hello Pulsar World!");
    }*/

}
