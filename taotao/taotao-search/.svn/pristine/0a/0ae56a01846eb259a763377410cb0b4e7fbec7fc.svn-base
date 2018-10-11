package com.taotao.search.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.commons.service.ApiService;
import com.taotao.search.bean.Item;

@Service
public class ItemService {
    
    private static final ObjectMapper MAPPER=new ObjectMapper();
    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_MANAGER_URL}")
    private String TAOTAO_MANAGER_URL;
    
    public Item queryItemById(Long itemId) {
        String url=TAOTAO_MANAGER_URL+"/rest/api/item/"+itemId;
        try {
           String jsonData= this.apiService.doGet(url);
           if(StringUtils.isNotEmpty(jsonData)){
               return MAPPER.readValue(jsonData, Item.class);
           }
        
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

}
