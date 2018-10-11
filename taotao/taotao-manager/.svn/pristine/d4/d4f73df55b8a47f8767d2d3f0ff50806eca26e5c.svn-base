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
import com.taotao.manager.pojo.ItemParamItem;
import com.taotao.manager.service.ItemParamItemService;

@Controller
@RequestMapping("item/param/item") //TODO  查询规格参数模板列表
public class ItemParamItemController {
    private static final Logger LOGGER =LoggerFactory.getLogger(ItemParamItemController.class);
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    /**
     * 查询规格参数模板列表
     * @param page
     * @param rows
     * @return
     */
   /* @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemParamList(
            @RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="30")Integer rows){
      try {
          return ResponseEntity.ok(this.itemParamItemService.queryItemList(page,rows));
      } catch (Exception e) {
        LOGGER.error("查询规格参数模板列表出错！，page="+page+", rows="+rows, e);
      }          
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }*/
    
    /**
     * 根据商品id查询商品规格参数
     * @param itemId
     * @return
     */
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
