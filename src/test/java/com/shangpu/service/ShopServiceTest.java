package com.shangpu.service;

import com.shangpu.BaseTest;
import com.shangpu.dao.ShopDao;
import com.shangpu.dto.ShopExecution;
import com.shangpu.entity.Area;
import com.shangpu.entity.PersonInfo;
import com.shangpu.entity.Shop;
import com.shangpu.entity.ShopCategory;
import com.shangpu.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService ShopService;

    @Test
    public void testAddShopo() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("E:\\111.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = ShopService.addShop(shop,is,shopImg.getName());
        System.out.println(se.getState());
    }
}