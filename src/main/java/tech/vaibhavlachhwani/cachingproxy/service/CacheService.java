package tech.vaibhavlachhwani.cachingproxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.vaibhavlachhwani.cachingproxy.model.CachedRequest;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private ConcurrentHashMap<String, CachedRequest> cache= new ConcurrentHashMap<>();

    public CachedRequest get(String url) {
        return cache.get(url);
    }

    public void put(String url, ResponseEntity<String> response) {
        cache.put(url, new CachedRequest(
                response.getHeaders(),
                response.getBody(),
                response.getStatusCode()
        ));
    }

    public void clearCache() {
        cache.clear();
    }
}
