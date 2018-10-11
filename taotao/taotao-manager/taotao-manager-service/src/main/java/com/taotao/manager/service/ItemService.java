package com.taotao.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commons.bean.EasyUIResult;
import com.taotao.commons.service.ApiService;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.pojo.ItemParamItem;
@Service
public class ItemService extends BasicService<Item>{
    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    @Autowired
    private ApiService apiService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("{TAOTAO_WEB_URL}")
    private String TAOTAO_WEB_URL;
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    public Boolean saveItem(Item item, String desc,String itemParams) {
        // TODO Auto-generated method stub
        //初始值
        item.setStatus(1);
        item.setId(null);//出于安全考虑，强制设置id为Null,通过数据库自增长完成
        Integer count1= super.save(item);
        
        //用日志查看是否在同一个事务，两个save操作不在同一事务中，放在同一service中即可
        
        //保存商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2=this.itemDescService.save(itemDesc);
        
        //保存规格参数数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        Integer count3 = this.itemParamItemService.save(itemParamItem);
       //发送消息到MQ的交换机，通知其他系统 
        sendMsg(item.getId(),"insert");
        return count1.intValue() ==1 && count2.intValue()==1&& count3.intValue()==1;
        
    }

    public EasyUIResult queryItemList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page,rows);
        Example example = new Example(Item.class);
        
        //安排创建时间排序
        example.setOrderByClause("created DESC");
        List<Item> items= this.itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<Item>(items);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    //更新
    public Boolean updateItem(Item item, String desc,String itemParams) {
        item.setStatus(null);//强制设置状态不能修改
        Integer count1=super.updateSelective(item);
        
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        
        Integer count2 = this.itemDescService.updateSelective(itemDesc);
                                            //updateSelective(itemParams)，需要根据主键更新，ItemParamsId我们并没有获得
        //更新商品规格参数                                    //需自定义更新方法
        Integer count3 = this.itemParamItemService.updateItemParamItem(item.getId(),itemParams);
        
       /* //通知其他系统改该商品已更新
        String url=TAOTAO_WEB_URL+"/item/cache/"+item.getId()+".html";
        try {
            this.apiService.doPost(url);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        sendMsg(item.getId(),"update");
        
        return count1.intValue() ==1 && count2.intValue()==1 && count3.intValue()==1;
    }

    
    private void sendMsg(Long itemId, String type) {
        try {
            //消息内容，itemId,type,date
            Map<String, Object> msg=new HashMap<String, Object>();
            msg.put("type", type);
            msg.put("itemId", itemId);
            msg.put("date", System.currentTimeMillis());
            this.rabbitTemplate.convertAndSend("item.", type,MAPPER.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
