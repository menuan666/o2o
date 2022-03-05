package com.shangpu.dao;

import com.shangpu.BaseTest;
import com.shangpu.dao.AreaDao;
import com.shangpu.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQuaryArea(){
        List<Area> areaList = areaDao.queryArea();
        System.out.println(areaList);
    }

}
