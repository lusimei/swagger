package com.kuang.controller;

import com.kuang.pojo.Users;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "<h1>hello</h1>";
    }

    @PostMapping("/user")
    public Users user(){
        return new Users("chad","lusimei1996");
    }

    @ApiOperation("报名接口")
    @PostMapping("/sayName")
    public String sayName(@RequestParam("name")@ApiParam("用户名") String name){
        return "hello,"+name;
    }

    @ApiOperation("保存接口")
    @PostMapping("/entity")
    public String entity(@RequestBody@ApiParam("对象") Users users){
        return users.toString();
    }
}
