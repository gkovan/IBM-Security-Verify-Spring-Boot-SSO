package com.gkovan.resource.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @GetMapping("api/token/info")
    public Map<String, String> getTokenInfo(@AuthenticationPrincipal Jwt principal) {
    	
    	Map<String,String> tokenInfo = new HashMap<String,String>();
    	tokenInfo.put("user_name", principal.getClaimAsString("preferred_username"));
    	tokenInfo.put("subject", principal.getClaimAsString("sub"));
    	tokenInfo.put("client_id", principal.getClaimAsString("client_id"));
    	tokenInfo.put("scope", principal.getClaimAsString("scope"));
    	
    	return tokenInfo;
    }
}