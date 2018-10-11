package com.taotao.manager.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.pojo.ContentCategory;

public interface ContentMapper extends Mapper<Content>{

    List<Content> queryListBycategoryId(Long categoryId);

}
