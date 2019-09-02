package com.queal.seata.controller;

import com.queal.seata.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/addGoods")
    public String addGoods() {
        System.out.println("node2 addGoods");
        goodsService.addGoods();
        return "ok";
    }
}
