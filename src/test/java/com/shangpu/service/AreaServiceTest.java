package com.shangpu.service;

import com.shangpu.BaseTest;
import com.shangpu.entity.Area;
import com.shangpu.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        List<Area> arealist = areaService.getAreaList();
        System.out.println(arealist.get(0).getAreaName());
    }
}
