package com.pigrz.service;

import com.pigrz.dao.GoodMapper;
import com.pigrz.pojo.Good;
import com.pigrz.util.Content;
import com.pigrz.util.FileUtil;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService{
    @Autowired
    GoodMapper goodMapper;

    @Override
    public List<Good> getAllGood() {
        return goodMapper.getAllGood();
    }

    @Override
    public List<Good> getGoodByType(String type) {
        if(type==null) {
            return new ArrayList<>();
        }
        else {
            return goodMapper.getGoodByType(type);
        }
    }

    @Override
    public List<Good> getGoodByName(String name) {
        if(name==null) {
            return new ArrayList<>();
        }
        else {
            return goodMapper.getGoodByName(name);
        }
    }

    @Override
    public Good getGoodByUniqueName(String name) {
        if(name==null) {
            return null;
        }
        else {
            return goodMapper.getGoodByUniqueName(name);
        }
    }

    @Override
    public boolean addGood(Good good){
        if(good==null) {
            return false;
        }
        else {
            String imagepath = good.getImagepath();
            if(StringUtil.nullOrEmpty(imagepath))
                return false;
            else {
                int index = imagepath.lastIndexOf('/');
                good.setImagepath(imagepath.substring(index, imagepath.length()));
                if (FileUtil.moveFile(Content.TEMP_IMG_PATH, Content.GOOD_IMG_PATH, good.getImagepath())) {
//                    FileUtil.deleteDir(Content.TEMP_IMG_PATH);
                    return goodMapper.addGood(good) > 0;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public boolean updateGood(Good good) {
        if(good==null) {
            return false;
        }
        else {
            Good oldGood=goodMapper.getGoodById(good.getId());
            int index = good.getImagepath().lastIndexOf('/');
            if(index>=0) {
                good.setImagepath(good.getImagepath().substring(index));
            }
            index=oldGood.getImagepath().lastIndexOf('/');
            if(index>=0) {
                oldGood.setImagepath(oldGood.getImagepath().substring(index));
            }
            if(!oldGood.getImagepath().equals(good.getImagepath())){
                boolean b = FileUtil.moveFile(Content.TEMP_IMG_PATH, Content.GOOD_IMG_PATH, good.getImagepath());
                if(!b)
                    return false;

            }
            return goodMapper.updateGood(good) > 0;
        }
    }

    @Override
    public boolean deleteGood(String id) {
        if (StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                System.out.println(id);
                int i = Integer.parseInt(id);
                return goodMapper.deleteGood(i) > 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("删除商品时输入id错误非法");
                return false;
            }
        }
    }

    @Override
    public String getGoodImagePath(int id) {
        String goodImagePath = goodMapper.getGoodImagePath(id);
        if(StringUtil.nullOrEmpty(goodImagePath)){
            return Content.ERROR_IMAGE;
        }
        else{
            return goodImagePath;
        }
    }

    @Override
    public List<Map<String, String>> getTypeList() {
        return goodMapper.getTypeList();
    }
}
