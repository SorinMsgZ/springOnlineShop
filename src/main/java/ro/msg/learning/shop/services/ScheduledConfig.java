package ro.msg.learning.shop.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("TestProfile3")
public class ScheduledConfig {
}