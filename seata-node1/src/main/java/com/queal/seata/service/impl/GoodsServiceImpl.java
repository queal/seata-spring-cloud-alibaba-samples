package com.queal.seata.service.impl;

import com.queal.seata.mybatis.dao.GoodsMapper;
import com.queal.seata.mybatis.model.Goods;
import com.queal.seata.service.GoodsService;
import com.queal.seata.service.rpc.Node2GoodsService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private Node2GoodsService node2GoodsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGoods() throws Exception {
        Goods goods = new Goods();
        goods.setId("N1" + RandomStringUtils.randomAlphanumeric(30));
        goods.setCreateTime(new Date());
        goods.setModifyTime(new Date());
        goodsMapper.insert(goods);

        // 微服务调用
        node2GoodsService.addGoods();
    }

    @GlobalTransactional
    @Override
    public void addGoods1() throws Exception {
        Goods goods = new Goods();
        goods.setId("N1" + RandomStringUtils.randomAlphanumeric(30));
        goods.setCreateTime(new Date());
        goods.setModifyTime(new Date());

        goodsMapper.insert(goods);
        node2GoodsService.addGoods();

        System.out.println("xid: " + RootContext.getXID());
        Integer.parseInt("s");
    }
}
