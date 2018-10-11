package com.taotao.web.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.commons.httpclient.HttpResult;
import com.taotao.commons.service.ApiService;
import com.taotao.sso.query.bean.User;
import com.taotao.web.bean.Order;
import com.taotao.web.threadlocal.UserThreadLocal;

@Service
public class OrderService {

    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_ORDER_URL}")
    private String TAOTAO_ORDER_URL;
    
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    /**
     * 提交订单到订单系统
     * @param order
     * @return
     */
    public String submit(Order order) {
        String url=TAOTAO_ORDER_URL+"/order/create";
        
        //填充当前用户的信息
        User user=UserThreadLocal.get();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        try {
            
            HttpResult httpResult=this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
            if(httpResult.getCode().intValue()==200){
                String jsonData=httpResult.getBody();
                JsonNode jsonNode=MAPPER.readTree(jsonData);
                if(jsonNode.get("status").asInt() == 200){
                    //订单提交成功，返回订单号
                    return jsonNode.get("data").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order queryByOrderId(String orderId) {
        String url=TAOTAO_ORDER_URL+"/order/query/"+orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
               return MAPPER.readValue(jsonData, Order.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
