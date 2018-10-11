package com.taotao.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commons.bean.EasyUIResult;
import com.taotao.manager.mapper.ItemParamItemMapper;
import com.taotao.manager.pojo.ItemParamItem;
@Service
public class ItemParamItemService extends BasicService<ItemParamItem>{
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public EasyUIResult queryItemList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page,rows);
        Example example = new Example(ItemParamItem.class);
        
        //安排创建时间排序
        example.setOrderByClause("created DESC");
        List<ItemParamItem> itemParamItems= this.itemParamItemMapper.selectByExample(example);
        PageInfo<ItemParamItem> pageInfo = new PageInfo<ItemParamItem>(itemParamItems);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    public Integer updateItemParamItem(Long itemId, String itemParams) {
        //更新数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setUpdated(new Date());
       //根据自定义 条件更新
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return this.itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
    }
}
