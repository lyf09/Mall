package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.IndexService;

@RequestMapping("index")
@Controller
public class IndexController {//TODO: 前台系统改造静态资源的访问。
    @Autowired
    private IndexService indexService;
    
    /**
     * 首页
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView index(){//TODO 淘淘快报
       ModelAndView mv = new ModelAndView("index");
       //首页大广告
       String indexAD1 = this.indexService.queryIndexAD1();
       //小广告位数据
       String indexAD2 = this.indexService.queryIndexAD2();
       mv.addObject("indexAD1",indexAD1);
       mv.addObject("indexAD2",indexAD2);
        return mv;
        
    }

}
