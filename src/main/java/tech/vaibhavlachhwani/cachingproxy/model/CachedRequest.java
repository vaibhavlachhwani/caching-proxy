package tech.vaibhavlachhwani.cachingproxy.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class CachedRequest {
    private HttpHeaders httpHeaders;
    private String body;
    private HttpStatusCode statusCode;

    public CachedRequest(HttpHeaders httpHeaders, String body, HttpStatusCode statusCode) {
        this.httpHeaders = httpHeaders;
        this.body = body;
        this.statusCode = statusCode;
    }

    public HttpHeaders getHeadersWithCacheStatus(CacheStatus cacheStatus){
        HttpHeaders httpHeadersWithCacheStatus = new HttpHeaders();

        httpHeadersWithCacheStatus.addAll(this.httpHeaders);
        httpHeadersWithCacheStatus.add("X-Cache", cacheStatus.name());

        return httpHeadersWithCacheStatus;
    }

    public String getBody() {
        return body;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
