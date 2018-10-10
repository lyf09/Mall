package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/*import com.taotao.cart.bean.User;*/
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;
import com.taotao.sso.query.bean.User;

@RequestMapping("cart")
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartCookieService cartCookieService;
    
    /**
     * 查询购物车列表
     * @return
     */
    @RequestMapping(value="list",method=RequestMethod.GET)
    public ModelAndView cartList(HttpServletRequest request){
        ModelAndView mv= new ModelAndView("cart");
        List<Cart> cartList=null;
        User user=UserThreadLocal.get();
        if(null == user){
           //未登录 
            cartList=this.cartCookieService.queryCartList(request);
        }else{
            //登录状态
            cartList=this.cartService.queryCartList();
        }
        mv.addObject("cartList",cartList);
        return mv;
    }
   
    /**
     * 加入商品到购物车
     * @param itemId
     * @return
     */
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public String  addItemToCart(@PathVariable("itemId")Long itemId,HttpServletRequest request,
            HttpServletResponse response){
        User user=UserThreadLocal.get();
        if(null == user){
            //未登录
            this.cartCookieService.addItemToCart(itemId, request, response);
        }else{
            //登录状态
            this.cartService.addItemToCart(itemId);
        }
        //重定向到购物车列表
        return "redirect:/cart/list.html";
    }
    
    /**
     * 修改购买商品的数量
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping(value="update/num/{itemId}/{num}",method=RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId") Long itemId,
            @PathVariable("num")Integer num,HttpServletRequest request,HttpServletResponse response ){
        User user=UserThreadLocal.get();
        if(null == user){
            //未登录状态
            this.cartCookieService.udpateNum(itemId, num, request, response);
        }else{
            this.cartService.updateNum(itemId,num);
        }
        //204,执行成功，但是没有内容返回
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    /**
     * 删除购物车中的商品
     * @param itemId
     * @return
     */
    @RequestMapping(value="delete/{itemId}",method=RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId")Long itemId,HttpServletRequest request,
            HttpServletResponse response){
        //判断用户是否登录
        User user=UserThreadLocal.get();
        if(null == user){
            //未登录状态
            this.cartCookieService.deleteItem(itemId, request, response);
        }else{
            //已登录
            this.cartService.deleteItem(itemId);
        }
        return "redirect:/cart/list.html";
    }
}
