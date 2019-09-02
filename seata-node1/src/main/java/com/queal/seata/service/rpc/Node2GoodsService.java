package com.queal.seata.service.rpc;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "seata-node2", fallback = Node2GoodsServiceFallback.class)
public interface Node2GoodsService {
    @RequestMapping("/addGoods")
    public String addGoods() throws Exception;
}


@Component
class Node2GoodsServiceFallback implements Node2GoodsService {

    @Override
    public String addGoods() throws Exception {
        throw new Exception();
    }
}