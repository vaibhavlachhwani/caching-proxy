package tech.vaibhavlachhwani.cachingproxy.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.vaibhavlachhwani.cachingproxy.model.CachedRequest;
import tech.vaibhavlachhwani.cachingproxy.util.CacheUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private ConcurrentHashMap<String, CachedRequest> cache= new ConcurrentHashMap<>();

    public CachedRequest get(String url) {
        return cache.get(url);
    }

    public void put(String url, ResponseEntity<?> response) {
        CachedRequest cachedRequest = CacheUtil.toCachedRequest(response);
        cache.put(url, cachedRequest);
    }

    public void clearCache() {
        cache.clear();
    }

    public int getCacheSize() {
        return cache.size();
    }

    public ConcurrentHashMap<String, CachedRequest> getCache() {
        return cache;
    }
}
