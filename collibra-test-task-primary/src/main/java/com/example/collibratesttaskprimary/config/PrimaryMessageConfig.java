package com.example.collibratesttaskprimary.config;

import com.example.collibratesttaskprimary.message.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrimaryMessageConfig {

    @Bean
    public MessageRepository<Long, Object> messageRepository() {
        return new MessageRepository<>();
    }
}
