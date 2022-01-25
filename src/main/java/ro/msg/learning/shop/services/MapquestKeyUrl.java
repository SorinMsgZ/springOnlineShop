package ro.msg.learning.shop.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mapquest")
@Data
public class MapquestKeyUrl {
    @Value("${mapquest.consumerKey}")
    private String consumerKey;
    @Value("${mapquest.consumerSecret}")
    private String consumerSecret;
    @Value("${mapquest.resourceUrl}")
    private String resourceUrl;
}
