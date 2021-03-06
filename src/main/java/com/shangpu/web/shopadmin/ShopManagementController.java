package com.shangpu.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shangpu.dto.ShopExecution;
import com.shangpu.entity.Area;
import com.shangpu.entity.PersonInfo;
import com.shangpu.entity.Shop;
import com.shangpu.entity.ShopCategory;
import com.shangpu.enums.ShopStateEnum;
import com.shangpu.service.AreaService;
import com.shangpu.service.ShopCategoryService;
import com.shangpu.service.ShopService;
import com.shangpu.util.CodeUtil;
import com.shangpu.util.HttpServletRequestUtil;
import com.shangpu.util.ImageUtil;
import com.shangpu.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;@Autowired
    private AreaService areaService;
    @RequestMapping(value="/getshopinitinfo" , method=RequestMethod.GET)@ResponseBody
    private Map<String, Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try{
            shopCategoryList =shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            //System.out.println(shopCategoryList);
            //System.out.println(11);
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "???????????????????????????");
            return modelMap;
        }
            //1.?????????????????????????????????????????????????????????????????????
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "????????????????????????");
            return modelMap;
        }
            //2.????????????
            if (shop != null && shopImg != null) {
                PersonInfo owner = new PersonInfo();
                //Session TODO
                owner.setUserId(1L);
                shop.setOwner(owner);
                File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
//                try {
//                    shopImgFile.createNewFile();
//                } catch (IOException e) {
//                    modelMap.put("success", false);
//                    modelMap.put("errMsg", e.getMessage());
//                    return modelMap;
//                }
//                try {
//                    inputStreamToFile(shopImg.getInputStream(), shopImgFile);
//                } catch (IOException e) {
//                    modelMap.put("success", false);
//                    modelMap.put("errMsg", e.getMessage());
//                    return modelMap;
//                }
                ShopExecution se = null;
                try {
                    se = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
                    if (se.getState() == ShopStateEnum.CHECK.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", se.getStateInfo());
                    }
                } catch (IOException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                }

                return modelMap;

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "?????????????????????");
                return modelMap;
            }
        }
//        private static void inputStreamToFile (InputStream ins, File file){
//            FileOutputStream os = null;
//            try {
//                os = new FileOutputStream(file);
//                int bytesRead = 0;
//                byte[] buffer = new byte[1024];
//                while ((bytesRead = ins.read(buffer)) != -1) {
//                    os.write(buffer, 0, bytesRead);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("??????inputStreamToFile????????????: " + e.getMessage());
//            } finally {
//                try {
//                    if (os != null) {
//                        os.close();
//                    }
//                    if (ins != null) {
//                        ins.close();
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(" inputStreamToFile??????io????????????:" + e.getMessage());
//                }
//            }
//        }
    }