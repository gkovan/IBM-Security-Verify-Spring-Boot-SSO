package com.gkovan.client.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.gkovan.client.web.model.FooModel;
import com.gkovan.client.web.model.UserInfoModel;

@Controller
public class FooClientController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FooClientController.class);

    @Value("${resourceserver.api.url}")
    private String fooApiUrl;
    
    @Value("${resourceserver.userinfo.url}")
    private String userInfoApiUrl;

    @Autowired
    private WebClient webClient;

    @GetMapping("/foos")
    public String getFoos(Model model) {
    	
        List<FooModel> foos = this.webClient.get()
            .uri(fooApiUrl)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<FooModel>>() {
            })
            .block();
        model.addAttribute("foos", foos);
        return "foos";
    }
    
    @GetMapping("/home")
    public String getHome(Model model) {
    	
        model.addAttribute("attr1", "valueOfAttr1");
        return "home";
    }
    
    @GetMapping("/profile")
    public String getProfile(Model model) {
        Map<String,String> profile = this.webClient.get()
                .uri(userInfoApiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,String>>() {
                })
                .block();
        
        List<UserInfoModel> userInfoList = new ArrayList<UserInfoModel>();
        for (String key:  profile.keySet()) {
        	userInfoList.add(new UserInfoModel(key, profile.get(key)));
        }
        
        model.addAttribute("profile", profile);
        return "profile";
    }
    
}
