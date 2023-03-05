package com.collibra.collibratesttaskweb;

import com.collibra.collibratesttaskweb.config.WebMessagingConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(
        WebMessagingConfigProperties.class
)
@SpringBootApplication
public class CollibraTestTaskWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollibraTestTaskWebApplication.class, args);
    }

}
