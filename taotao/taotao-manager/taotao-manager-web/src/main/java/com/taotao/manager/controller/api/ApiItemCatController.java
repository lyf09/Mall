package com.taotao.manager.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.taotao.commons.bean.ItemCatResult;
import com.taotao.manager.service.ItemCatService;

@RequestMapping("api/item/cat")
@Controller
public class ApiItemCatController {
    @Autowired
    private ItemCatService ItemCatService;

     
    /**
     * 对外提供接口服务，查询所有类目数据
     * @return
     *//*
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<String> queryItemCatList(
            @RequestParam(value="callback",required=false)String callback){
        try {
            ItemCatResult itemCatResult= this.ItemCatService.queryAllToTree();
            if(null == itemCatResult){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);            }
            String json = MAPPER.writeValueAsString(itemCatResult);
            if(StringUtils.isEmpty(callback)){
                //无需跨域支持
                return ResponseEntity.ok(json);
            }else{
                //需跨域支持
                return ResponseEntity.ok(callback+"("+json+");");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }*/
    
    // 统一支持jsonp,配置文件也taotao-manager.servlet.xml需配置
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatList(){
        try {
            ItemCatResult itemCatResult= this.ItemCatService.queryAllToTree();
            return ResponseEntity.ok(itemCatResult);     
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
   
   
}
