package tech.vaibhavlachhwani.cachingproxy.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import tech.vaibhavlachhwani.cachingproxy.model.CacheStatus;
import tech.vaibhavlachhwani.cachingproxy.model.CachedRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CacheUtil {
    public static ResponseEntity<?> toResponseEntity(CachedRequest cachedRequest, CacheStatus cacheStatus) {
        ByteArrayResource resource = new ByteArrayResource(cachedRequest.getBody());

        return ResponseEntity
                .status(cachedRequest.getStatusCode())
                .headers(cachedRequest.getHeadersWithCacheStatus(cacheStatus))
                .contentType(cachedRequest.getMediaType())
                .body(resource);
    }

    public static CachedRequest toCachedRequest(ResponseEntity<?> responseEntity) {
        byte[] bodyBytes;

        Object body = responseEntity.getBody();

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

        CachedRequest cachedRequest = new CachedRequest(
                responseEntity.getHeaders(),
                responseEntity.getStatusCode(),
                bodyBytes,
                responseEntity.getHeaders().getContentType()
        );

        return cachedRequest;
    }
}
