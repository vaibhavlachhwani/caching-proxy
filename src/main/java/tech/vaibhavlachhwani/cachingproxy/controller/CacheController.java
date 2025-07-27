package tech.vaibhavlachhwani.cachingproxy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vaibhavlachhwani.cachingproxy.service.CacheService;
import tech.vaibhavlachhwani.cachingproxy.service.ConfigService;

import java.util.Map;

@RestController
@RequestMapping("/cache")
public class CacheController {
    private ConfigService configService;
    private CacheService cacheService;

    public CacheController(ConfigService configService, CacheService cacheService) {
        this.configService = configService;
        this.cacheService = cacheService;
    }

    @PostMapping("/origin")
    public ResponseEntity<?> setOrigin(@RequestBody Map<String, String> body) {
        String originUrl = body.get("origin");

        configService.setOriginUrl(originUrl);
        return ResponseEntity.ok("Origin set to : " + originUrl);
    }

    @DeleteMapping("")
    public ResponseEntity<?> clearCache() {
        cacheService.clearCache();

        return ResponseEntity.ok("Cache cleared.");
    }

    @GetMapping("/size")
    public ResponseEntity<?> getCacheSize() {
        int size = cacheService.getCacheSize();

        return ResponseEntity.ok("Cache size : " + size);
    }
}
