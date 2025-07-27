package tech.vaibhavlachhwani.cachingproxy.service;

import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    private String originUrl;

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }
}
