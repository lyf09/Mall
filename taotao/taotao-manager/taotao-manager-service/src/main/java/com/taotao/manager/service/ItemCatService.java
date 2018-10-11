package com.taotao.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.commons.bean.ItemCatData;
import com.taotao.commons.bean.ItemCatResult;
import com.taotao.commons.service.RedisService;
import com.taotao.manager.pojo.ItemCat;

@Service
public class ItemCatService extends BasicService<ItemCat>{
    
    @Autowired
    private RedisService redisService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper(); 
    
    private static final String REDIS_KEY ="TAOTAO_MANAGER_ITEM_CAL_ALL";
    private static final Integer REDIS_TIME =60*60*24*30*3;//只计算一次
    
    /**
     * 全部查询，并且生成树状结构
     *      * 先从缓存中命中，命中则返回
     * 查询数据库中所有类目数据，按照前端系统所需要的格式返回

     * 
     * @return
     */
    public ItemCatResult queryAllToTree() {
        ItemCatResult result = new ItemCatResult();
        
       //缓存逻辑不能影响原有的业务逻辑执行。
        // 先从缓冲命中，如果命中就返回，未命中继续往下执行
        try {
            String cacheData = this.redisService.get(REDIS_KEY);
            if(StringUtils.isNotEmpty(cacheData)){
                //命中
                return MAPPER.readValue(cacheData, ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.querryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }
        
        try {
            //将结果集写入缓存
           this.redisService.set(REDIS_KEY, MAPPER.writeValueAsString(result),REDIS_TIME);//缓存时间3个月
        } catch (Exception e) {
          e.printStackTrace();
        }
        return result;
    }


   //用了泛型注入后，子类中 啥都不用写
   // @Autowired
   // private ItemCatMapper itemCatMapper;


/*    //有了BasicService中的查询方法
 * public List<ItemCat> queryItemCatListByParentId(long pid) {
        ItemCat record = new ItemCat();
        record.setParentId(pid);//查询参数
        return this.itemCatMapper.select(record);
    }*/


   /* @Override
    public Mapper<ItemCat> getMapper() {
        
        return this.itemCatMapper;
    }*/
    
    
    
}
