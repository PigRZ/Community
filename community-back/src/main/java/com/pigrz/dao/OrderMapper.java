package com.pigrz.dao;

import com.pigrz.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    /**
     * 获取所有社区居民的订单
     * @return 所有社区居民的订单
     */
    List<Order> getAllOrder();

    List<Order> getNoDoneOrder();

    /**
     * 根据日期获取订单
     * @param date 订单创建日期
     * @return  该日期创建的所有订单
     */
    List<Order> getOrderByDate(Date date);


    /**
     * 查询某个用户的所有订单
     * @param userid 用户id
     * @return 某个用户的所有订单
     */
    List<Order> getOrderByUserid(int userid);

    /**
     * 查询某个用户的所有订单
     * @param username 用户名称
     * @return 某个用户的所有订单
     */
    List<Order> getOrderByUsername(String username);


    /**
     * 查询某个商品的所有订单
     * @param goodname 商品名称
     * @return 某个商品的所有订单
     */
    List<Order> getOrderByGoodname(String goodname);

    /**
     * 根据用户id和商品id查订单
     * @param userid 用户id
     * @param goodid 商品id
     * @return 订单
     */
    Order getOrderByUseridAndGoodid(int userid,int goodid);

    /**
     * 创建订单
     * @param order 要创建的订单
     * @return 影响的行数
     */
    int addOrder(Order order);

    int completeOrder(int id);

    /**
     * 修改订单
     * @param order 要修改的订单
     * @return 影响的行数
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     * @param id 订单id
     * @return 影响的行数
     */
    int deleteOrder(int id);
}
