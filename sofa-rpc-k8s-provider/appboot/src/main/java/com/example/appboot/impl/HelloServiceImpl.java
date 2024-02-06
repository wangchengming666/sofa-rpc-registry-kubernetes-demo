package com.example.appboot.impl;

import com.example.app.api.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "call provider success" + name;
    }
}