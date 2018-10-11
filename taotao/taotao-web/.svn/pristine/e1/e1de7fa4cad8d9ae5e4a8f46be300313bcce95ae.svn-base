package com.taotao.web.mq.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.commons.service.RedisService;
import com.taotao.web.service.ItemService;

public class ItemMQHandler {
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    @Autowired
    private RedisService redisService;
    
    public void execute(String msg){
        try {
            JsonNode jsonNode=MAPPER.readTree(msg);
            Long itemId=jsonNode.get("itemId").asLong();
            
            //删除redis中的缓存数据
            String key=ItemService.REDIS_KEY+itemId;
            this.redisService.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
