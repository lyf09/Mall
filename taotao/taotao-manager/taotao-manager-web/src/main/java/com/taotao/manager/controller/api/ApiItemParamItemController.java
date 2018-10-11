package com.taotao.manager.controller.api;

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
import com.taotao.manager.controller.ItemParamItemController;
import com.taotao.manager.pojo.ItemParam;
import com.taotao.manager.pojo.ItemParamItem;
import com.taotao.manager.service.ItemParamItemService;
import com.taotao.manager.service.ItemParamService;

@RequestMapping("api/item/param/item")
@Controller
public class ApiItemParamItemController {
        private static final Logger LOGGER =LoggerFactory.getLogger(ItemParamItemController.class);
        
        @Autowired
        private ItemParamItemService itemParamItemService;
        
        @RequestMapping(value="{itemId}",method=RequestMethod.GET)
        public ResponseEntity<ItemParamItem> queryByItemId(@PathVariable("itemId")Long itemId){
            try {
                ItemParamItem record = new ItemParamItem();
                record.setItemId(itemId);
                ItemParamItem itemParamItem = this.itemParamItemService.querryOne(record);
                if(itemParamItem == null){
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                return ResponseEntity.ok(itemParamItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);    }
    }
