package com.taotao.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.commons.bean.EasyUIResult;
import com.taotao.manager.pojo.ItemParam;
import com.taotao.manager.pojo.ItemParamItem;
import com.taotao.manager.service.ItemParamService;

@RequestMapping("item/param")
@Controller
public class ItemParamController {
    
    @Autowired
    private ItemParamService itemParamService;
    
    
    /**
     * 根据商品类目id查询规格参数模板
     * @param itemCatId
     * @return
     */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamByItemCatID(
            @PathVariable("itemCatId") Long itemCatId){
        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(itemCatId);
            ItemParam itemParam = this.itemParamService.querryOne(record);
            if(null == itemParam){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    
    /**
     * 新增规格参数模板
     * @param itemCatId
     * @param paramData
     * @return
     */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId,
            @RequestParam("paramData") String paramData){
        try {
            ItemParam record = new ItemParam();
            record.setId(null);
            record.setItemCatId(itemCatId);
            record.setParamData(paramData);
            this.itemParamService.save(record);
            return ResponseEntity.status(HttpStatus.CREATED).build();
          } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
  
}
