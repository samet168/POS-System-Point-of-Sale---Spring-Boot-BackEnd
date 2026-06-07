package com.example.StoreMSI.Service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void revokeToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenRevoked(String token) {
        return blacklist.contains(token);
    }
}