package com.pigrz.service;

import com.pigrz.pojo.Order;

import java.sql.Date;
import java.util.List;

public interface OrderService {


    //订单管理
    List<Order> getAllOrder();
    List<Order> getOrderByDate(Date date);
    List<Order> getOrderByUserid(int id);
    List<Order> getOrderByUsername(String username);
    List<Order> getOrderByGoodname(String goodname);
    boolean completeOrder(int id);
    boolean addOrder(Order order);
    boolean createOrders(List<Order> orders);
    boolean updateOrder(Order order);
    boolean deleteOrder(String id);

}
