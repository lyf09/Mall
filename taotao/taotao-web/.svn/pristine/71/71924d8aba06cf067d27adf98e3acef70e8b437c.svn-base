package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.commons.service.ApiService;
import com.taotao.sso.query.api.UserQueryService;
/*import com.taotao.web.bean.User;*/

import com.taotao.sso.query.bean.User;

@Service
public class UserService {


    
    @Value("${TAOTAO_SSO_URL}")
    public String TAOTAO_SSO_URL;
 
    /*    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    *//**
     * 根据token查询用户信息
     * @param token
     * @return
     *//*
    public User queryByToken(String token){
        try {
           String url=TAOTAO_SSO_URL+"/service/user/"+token;
           String jsonData=this.apiService.doGet(url);
           if(StringUtils.isNotEmpty(jsonData)){
               return MAPPER.readValue(jsonData, User.class);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }*/
    
    @Autowired
    private UserQueryService userQueryService;
    public User queryByToken(String token){
         
        return this.userQueryService.queryUserByToken(token);
        
    }
}
