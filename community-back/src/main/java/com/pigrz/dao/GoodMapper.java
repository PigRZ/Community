package com.pigrz.dao;

import com.pigrz.pojo.Good;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GoodMapper {
    /**
     * 获取所有商品的信息
     * @return 数据库中所有商品
     */
    List<Good> getAllGood();

    /**
     * 根据商品类型获取该类型的所有商品
     * @param type 商品类型
     * @return  该类型的所有商品
     */
    List<Good> getGoodByType(String type);

    /**
     * 根据商品名称的关键词获取包含该名称的所有商品
     * @param name 商品全名所包含的名字
     * @return 包含该名字的所有商品
     */
    List<Good> getGoodByName(String name);

    /**
     * 根据商品全名获取商品
     * @param name 商品的全名
     * @return 全名为name的所有商品
     */
    Good getGoodByUniqueName(String name);

    Good getGoodById(int id);

    List<Map<String,String>> getTypeList();

    /**
     * 添加商品
     * @param good 要添加的商品
     * @return 影响的行数
     */
    int addGood(Good good);

    /**
     * 更新商品
     * @param good 要更新的商品
     * @return 影响的行数
     */
    int updateGood(Good good);

    /**
     * 根据id删除商品
     * @param id 要删除的商品
     * @return 影响的行数
     */
    int deleteGood(int id);

    String getGoodImagePath(int id);
}
