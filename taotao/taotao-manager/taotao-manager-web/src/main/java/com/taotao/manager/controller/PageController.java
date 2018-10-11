package com.taotao.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 通用页面跳转
 * @author laiyu
 *
 */
@Controller
@RequestMapping("page")
public class PageController {
    /**
     * 具体的页面跳转逻辑
     * @param pageName
     * @return 视图名
     */
    @RequestMapping(value="{pageName}",method=RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
        
    }
}
