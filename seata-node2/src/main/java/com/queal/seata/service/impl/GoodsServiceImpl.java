package com.queal.seata.service.impl;

import com.queal.seata.mybatis.dao.GoodsMapper;
import com.queal.seata.mybatis.model.Goods;
import com.queal.seata.service.GoodsService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addGoods() {
        Goods goods = new Goods();
        goods.setId("N2" + RandomStringUtils.randomAlphanumeric(30));
        goods.setCreateTime(new Date());
        goods.setModifyTime(new Date());
        goodsMapper.insert(goods);

        System.out.println("xid: " + RootContext.getXID());

//        Integer.parseInt("s");
    }
}
