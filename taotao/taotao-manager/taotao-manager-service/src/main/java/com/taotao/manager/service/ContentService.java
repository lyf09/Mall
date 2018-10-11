package com.taotao.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commons.bean.EasyUIResult;
import com.taotao.manager.mapper.ContentMapper;
import com.taotao.manager.pojo.Content;
@Service
public class ContentService extends BasicService<Content>{

    @Autowired
    private ContentMapper contentMapper;
    
    public EasyUIResult queryListBycategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Content> contents = this.contentMapper.queryListBycategoryId(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<Content>(contents);
        
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

}
