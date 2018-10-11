package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
    
    @Autowired
    private ItemCatService  itemCatService;
    
    /**
     * 查询商品类目列表
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
            @RequestParam(value="id",defaultValue="0")Long pid){
       try {
          /* List<ItemCat> list = this.itemCatService.queryItemCatListByParentId(pid);*/
           ItemCat record = new ItemCat();
           record.setParentId(pid);
           List<ItemCat> list = this.itemCatService.queryListByWhere(record);
;           if(null == list || list.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
           }
           return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);   
    }
/*    
    *//**
     *由cid查询商品类目名称
     * @param cid
     * @return
     *//*
    @RequestMapping(value="{cid}" ,method=RequestMethod.GET)
    public ResponseEntity<String> queryItemCatById(
            @RequestParam(value="cid")Long cid){
        System.out.println(cid);
       try {
          ItemCat itemCat = this.itemCatService.querryById(cid);
           return ResponseEntity.ok(itemCat.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);   
    }*/
    

}
