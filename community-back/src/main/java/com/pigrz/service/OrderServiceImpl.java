package com.pigrz.service;

import com.pigrz.dao.OrderMapper;
import com.pigrz.pojo.Good;
import com.pigrz.pojo.Order;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }

    @Override
    public List<Order> getOrderByDate(Date date) {
        if(date==null) {
            return new ArrayList<>();
        }
        else {
            return orderMapper.getOrderByDate(date);
        }
    }

    @Override
    public List<Order> getOrderByUserid(int id) {
        return orderMapper.getOrderByUserid(id);
    }

    @Override
    public List<Order> getOrderByUsername(String username) {
        if(username==null) {
            return new ArrayList<>();
        }
        else {
            return orderMapper.getOrderByUsername(username);
        }
    }

    @Override
    public List<Order> getOrderByGoodname(String goodname) {
        if(goodname==null) {
            return new ArrayList<>();
        }
        else {
            return orderMapper.getOrderByGoodname(goodname);
        }
    }

    @Override
    public boolean completeOrder(int id) {
        if(id<=0){
            return false;
        }
        else{
            int i = orderMapper.completeOrder(id);
            return i>0;
        }
    }

    @Override
    public boolean addOrder(Order order) {
        if(order==null) {
            return false;
        }
        else {
            return orderMapper.addOrder(order)>0;
        }
    }

    @Override
    public boolean createOrders(List<Order> orders) {
        for (Order order:orders ) {
            order.setDate(new Date(System.currentTimeMillis()));
            order.setDone("否");
            System.out.println(order);
            if(orderMapper.addOrder(order)<=0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        if(order==null) {
            return false;
        }
        else {
            return orderMapper.updateOrder(order)>0;
        }
    }

    @Override
    public boolean deleteOrder(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else{
            try {
                int i=Integer.parseInt(id);
                return orderMapper.deleteOrder(i)>0;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("删除订单时id错误，id："+id);
                return false;
            }
        }
    }

}
