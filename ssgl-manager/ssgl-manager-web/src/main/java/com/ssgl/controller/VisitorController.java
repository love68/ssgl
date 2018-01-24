package com.ssgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 23:09
 */
@Controller
@RequestMapping("/visitor/")
public class VisitorController {

    @RequestMapping(value = "toManagerVisitorUI")
    public String toManagerVisitorUI(){
        return "visitor";
    }
}
