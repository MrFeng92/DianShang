package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController
{
    //通用转向方法  http://localhost:8081/page/pageName
    @RequestMapping("page/{pageName}")
    public String goHome(@PathVariable String pageName)
    {
        return pageName;    //prifex+logicName+suffix
    }
}
