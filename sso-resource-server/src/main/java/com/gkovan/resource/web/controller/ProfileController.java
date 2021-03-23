package com.gkovan.resource.web.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("api/profile")
    public Map<String,String> getProfile(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String fullName = jwtAuth.getToken().getClaimAsString("fullName");
    	Map<String,String> profileMap = new HashMap<String,String>();
    	profileMap.put("fullName", fullName);
    	return profileMap;
    }
}
