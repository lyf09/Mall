package com.taotao.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.pojo.BasePojo;

public abstract class BasicService<T extends BasePojo> {
    
  //1.  public abstract Mapper<T> getMapper();
    
    //方法2、直接mapper注入,spring4中有的特性泛型注入
    @Autowired
    private Mapper<T> mapper;
    
    
    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public T querryById(Long id){
        return this.mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 查询所有
     * @return
     */
    public List<T> querryAll(){
        return this.mapper.select(null);
        
    }
    
    /**
     * 根据条件查询一条数据
     * 如果该条件查出多条数据会抛出异常
     * @param record
     * @return
     */
    public T querryOne(T record){
        return this.mapper.selectOne(record);
        
    }
    
    /**
     * 根据条件查询多条数据
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record){
        return this.mapper.select(record);   
    }
    
    /**
     * 根据条件分页查询数据
     * @param record
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryListByWhere(T record,Integer page,Integer rows){
        //设置分页参数
        PageHelper.startPage(page, rows);
        List<T> list = this.mapper.select(record);
        return new PageInfo<T>(list);
        
    }
    
    /**
     * 新增数据
     * @param t
     * @return
     */
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insert(t);
        
    }
    
    /**
     * 选择不为null 的数据作为插入
     * @param t
     * @return
     */
    public Integer saveSelective(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insertSelective(t); 
    }
    
    /**
     * 根据主键更新
     * @param t
     * @return
     */
    public Integer update(T t){
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(t);
        
    }
    
    /**
     * 选择不为null的字段进行更新
     * @param t
     * @return
     */
    public Integer updateSelective(T t){
        t.setUpdated(new Date());
        t.setCreated(null);//强制设置创建时间为null，永不更新
        return this.mapper.updateByPrimaryKeySelective(t); 
    }
    
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
        return this.mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除数据
     * @param ids
     * @param clazz
     * @param property
     * @return
     */
    public Integer deleteByIds(List<Object> ids,Class<T> clazz,String property){
        Example example = new Example(clazz);
        //设置条件
        example.createCriteria().andIn(property, ids);
        return this.mapper.deleteByExample(example);
    }
    
    /*
     * 根据条件删除数据
     */
    public Integer deleteByWhere(T record){
        return this.mapper.delete(record);
    }
}
