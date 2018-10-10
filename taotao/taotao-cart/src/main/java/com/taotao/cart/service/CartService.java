package com.taotao.cart.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.abel533.entity.Example;
import com.taotao.cart.bean.Item;
/*import com.taotao.cart.bean.User;*/
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.threadlocal.UserThreadLocal;
import com.taotao.sso.query.bean.User;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;
    /**
     * 加入商品到购物车
     * 判断加入的商品原有购物车是否存在，存在数量相加，不存在，直接写入
     * @param itemId
     */
    public void addItemToCart(Long itemId) {
     // 判断该商品在购物车中是否存在
        User user = UserThreadLocal.get();
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        Cart cart = this.cartMapper.selectOne(record);

        if (null == cart) {
            // 购物车中不存在该商品
            cart = new Cart();
            cart.setUserId(user.getId());
            cart.setNum(1); // TODO 先默认为1
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());

            //商品的具体信息，需要去后台查询
            Item item = this.itemService.queryItemById(itemId);
            cart.setItemId(itemId);
            cart.setItemImage(StringUtils.split(item.getImage(), ',')[0]);
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
            
            cart.setNum(1);//TODO

            // 将Cart保存到数据库
            this.cartMapper.insert(cart);
        } else {
            // 该商品已经存在购物车中
            cart.setNum(cart.getNum() + 1); // TODO 先默认为1
            cart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKey(cart);
        }

    }

    public List<Cart> queryCartList(Long userId) {
        Example example=new Example(Cart.class);
        example.setOrderByClause("created DESC");

        example.createCriteria().andEqualTo("userId", userId);
        return this.cartMapper.selectByExample(example);
    }
    
    /**
     * 按照加入购物车时间倒叙排列
     * @return
     */
    public List<Cart> queryCartList() {
        return this.queryCartList(UserThreadLocal.get().getId());
    }

    
    
    
    /**
     * 修改购买商品的数量
     * @param itemId
     * @param num
     */
    public void updateNum(Long itemId, Integer num) {
        //更新条件
        Example example=new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", UserThreadLocal.get().getId())
        .andEqualTo("itemId", itemId);
         
        //更新内容
        Cart  record = new Cart();
        record.setNum(num);
        record.setUpdated(new Date());
        
        //执行更新
        this.cartMapper.updateByExampleSelective(record, example);
    }

    /**
     * 删除购物车中的商品
     * @param itemId
     */
    public void deleteItem(Long itemId) {
       //实现物理删除
        Cart record=new Cart();
        record.setItemId(itemId);
        record.setUserId(UserThreadLocal.get().getId());
        this.cartMapper.delete(record);
        
    }



}
