package com.taotao.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.sso.query.bean.User;
import com.taotao.web.bean.Cart;
import com.taotao.web.bean.Item;
import com.taotao.web.bean.Order;
/*import com.taotao.web.bean.User;*/
import com.taotao.web.interceptor.UserLoginHandleInterceptor;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;

@RequestMapping("order")
@Controller
public class OrderController { //TODO:填写、核对、修改订单

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;
    
    @RequestMapping(value="{itemId}", method=RequestMethod.GET )
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
        ModelAndView mv=new ModelAndView("order");
        
       //设置模型数据
        Item item  = this.itemService.queryItemById(itemId);
        mv.addObject("item",item);
        return mv;
    }
    
    /**
     * 基于购物车下单，全部商品
     * @return
     */
    //TODO 只下单勾选了的商品
    @RequestMapping(value="create",method=RequestMethod.GET)
    public ModelAndView toCartOrder(){
        ModelAndView mv=new ModelAndView("order-cart");
        //通过购物车的接口查询购物车的数据
        List<Cart> carts = this.cartService.queryCartList();
        mv.addObject("carts",carts);
        return mv;
    }
       
    @RequestMapping(value="submit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(Order order,@CookieValue(UserLoginHandleInterceptor.COOKIE_NAME)String token){
        Map<String, Object> result =new HashMap<String, Object>();
      //查询user数据
        User user=UserThreadLocal.get();
        //设置当前登录用户信息
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        String orederId=this.orderService.submit(order);
        if(StringUtils.isEmpty(orederId)){
            //订单提交失败
            result.put("status", 500);
        }else{
            //提交成功
            result.put("status", 200);
            result.put("data", orederId);
        }
        return result;
    }
    
    /**
     * 订单提交成功页
     * @param orderId
     * @return
     */
    @RequestMapping(value="success",method=RequestMethod.GET)
    public ModelAndView success( @RequestParam("id") String orderId){
        ModelAndView mv =new ModelAndView("success");
        Order order= this.orderService.queryByOrderId(orderId);
        mv.addObject("order",order);
        mv.addObject("date",new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }
}
