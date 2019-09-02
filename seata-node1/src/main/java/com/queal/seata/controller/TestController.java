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
        System.out.println("node1 addGoods");
        try {
            goodsService.addGoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("/addGoods1")
    public String addGoods1() {
        System.out.println("node1 addGoods1");
        try {
            goodsService.addGoods1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
