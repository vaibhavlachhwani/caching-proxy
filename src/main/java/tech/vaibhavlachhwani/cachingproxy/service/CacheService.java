package tech.vaibhavlachhwani.cachingproxy.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.vaibhavlachhwani.cachingproxy.model.CachedRequest;

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
        byte[] bodyBytes;

        Object body = response.getBody();

        if (body == null) {
            bodyBytes = new byte[0];
        } else if (body instanceof byte[]) {
            bodyBytes = (byte[]) body;
        } else if (body instanceof ByteArrayResource resource) {
            try {
                bodyBytes = resource.getInputStream().readAllBytes();
            } catch (IOException ex) {
                throw new RuntimeException("Error reading ByteArrayResource", ex);
            }
        } else {
            bodyBytes = body.toString().getBytes(StandardCharsets.UTF_8);
        }

        cache.put(url, new CachedRequest(
                response.getHeaders(),
                response.getStatusCode(),
                bodyBytes,
                response.getHeaders().getContentType()
        ));
    }

    public void clearCache() {
        cache.clear();
    }

    public int getCacheSize() {
        return cache.size();
    }
}
