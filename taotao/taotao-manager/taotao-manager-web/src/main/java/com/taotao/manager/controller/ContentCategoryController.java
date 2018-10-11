package com.taotao.manager.controller;

import java.util.List;

import net.sf.jsqlparser.expression.Parenthesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.ContentCategoryService;

@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    
    /**
     * 根据父节点id查询内容分类列表
     * @param pid
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
            @RequestParam(value="id",defaultValue="0")Long pid){
       try {
           ContentCategory record = new ContentCategory();
           record.setParentId(pid);
           List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
;           if(null == list || list.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
           }
           return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);   
    }
    
    /**
     * 新增节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
        try {
            this.contentCategoryService.saveContentCategory(contentCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     }
   
    /**
     * 重命名
     * @param contentCategory
     * @return
     */
    @RequestMapping(method= RequestMethod.PUT)
    public ResponseEntity<Void> rename(ContentCategory contentCategory){
        try {
            this.contentCategoryService.updateSelective(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    /**
     * 删除节点，包含它的所有子节点都删除
     * @param contentCategory
     * @return
     */
    @RequestMapping(method= RequestMethod.DELETE)
    public ResponseEntity<Void> delete(ContentCategory contentCategory){
      try {
        this.contentCategoryService.deleteAll(contentCategory);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
        e.printStackTrace();
    } 
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
}