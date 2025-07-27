package tech.vaibhavlachhwani.cachingproxy.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import tech.vaibhavlachhwani.cachingproxy.model.CacheStatus;
import tech.vaibhavlachhwani.cachingproxy.model.CachedRequest;
import tech.vaibhavlachhwani.cachingproxy.service.CacheService;
import tech.vaibhavlachhwani.cachingproxy.service.ConfigService;
import tech.vaibhavlachhwani.cachingproxy.util.CacheUtil;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ConfigService configService;
    private CacheService cacheService;

    public ProxyController(ConfigService configService, CacheService cacheService) {
        this.configService = configService;
        this.cacheService = cacheService;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> proxy(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("GET")) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        String origin = configService.getOriginUrl();
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String query = request.getQueryString();

        String fullUrl = origin + path;

        if (query != null) {
            fullUrl = fullUrl + "?" + query;
        }

        CachedRequest cachedRequest = cacheService.get(fullUrl);

        if (cachedRequest != null) {
            return CacheUtil.toResponseEntity(cachedRequest, CacheStatus.HIT);
        }

        RestClient restClient = RestClient.create();
        ResponseEntity<byte[]> result = restClient.get()
                .uri(fullUrl)
                .retrieve()
                .toEntity(byte[].class);

        cachedRequest = CacheUtil.toCachedRequest(result);
        cacheService.put(fullUrl, result);

        return CacheUtil.toResponseEntity(cachedRequest, CacheStatus.MISS);
    }
}
