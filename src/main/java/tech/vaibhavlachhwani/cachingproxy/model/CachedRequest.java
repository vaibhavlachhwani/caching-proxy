package tech.vaibhavlachhwani.cachingproxy.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;

public class CachedRequest {
    private HttpHeaders httpHeaders;
    private HttpStatusCode statusCode;
    private byte[] body;
    private MediaType mediaType;

    public CachedRequest(HttpHeaders httpHeaders, HttpStatusCode statusCode, byte[] body, MediaType mediaType) {
        this.httpHeaders = httpHeaders;
        this.statusCode = statusCode;
        this.body = body;
        this.mediaType = mediaType;
    }

    public HttpHeaders getHeadersWithCacheStatus(CacheStatus cacheStatus){
        HttpHeaders httpHeadersWithCacheStatus = new HttpHeaders();

        httpHeadersWithCacheStatus.addAll(this.httpHeaders);
        httpHeadersWithCacheStatus.add("X-Cache", cacheStatus.name());

        return httpHeadersWithCacheStatus;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
