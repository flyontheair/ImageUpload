package com.springapp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pcdalao on 2017/4/19.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value="/test",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody
    String test() {
        return "hello";
    }
}
