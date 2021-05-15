package com.pigrz.controller;

import com.pigrz.pojo.*;
import com.pigrz.service.*;
import com.pigrz.util.Content;
import com.pigrz.util.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    GoodService goodService;
    @Autowired
    OrderService orderService;
    @Autowired
    RepairItemService repairItemService;
    @Autowired
    RepairService repairService;
    @Autowired
    UserPostService userPostService;
    @Autowired
    UserService userService;
    @Autowired
    HealthService healthService;
    @Autowired
    ChatService chatService;
    @Autowired
    CommentService commentService;
    @Autowired
    AdminPostService adminPostService;
    @Autowired
    PartyMemberService partyMemberService;

    @RequestMapping("/goodList")
    public List<Good> getGoodList(HttpServletRequest request) {
        System.out.println("查询所有商品");
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        System.out.println(userid);
        List<Good> goods = goodService.getAllGood();
        System.out.println("查询到" + goods.size() + "个商品");
        return goods;
    }

    @RequestMapping("/searchGood")
    public List<Good> searchGood(@RequestParam String name) {
        if (name == null) {
            return new ArrayList<>();
        } else if (name.equals("")) {
            return goodService.getAllGood();
        } else {
            System.out.println("查找名称为" + name + "的商品");
            return goodService.getGoodByName(name);
        }
    }

    @RequestMapping("/goodTypeList")
    public List<Map<String, String>> goodTypeList() {
        System.out.println("查询商品的所有类型");
        List<Map<String, String>> types = goodService.getTypeList();
        System.out.println("查询到" + types.size() + "个类型");
        return types;
    }

    @RequestMapping("/buyGood")
    public String buyGood(@RequestBody List<Order> orders) {
        System.out.println("新建订单" + orders);
        if (orderService.createOrders(orders)) {
            System.out.println("新建成功");
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getGoodImagePath")
    public String getGoodImagePath(@RequestParam int id) {
        return goodService.getGoodImagePath(id);
    }

    @RequestMapping("/getOrderById")
    public List<Order> getOrderById(@RequestParam int id) {
        return orderService.getOrderByUserid(id);
    }


    @RequestMapping("/repairItemList")
    public List<RepairItem> repairItemList() {
        return repairItemService.getAllRepairItem();
    }

    @RequestMapping("/repairItemTypeList")
    public List<String> repairItemTypeList() {
        return repairItemService.getTypeList();
    }

    @RequestMapping("/addRepair")
    public String addRepair(@RequestBody Repair repair) {
        System.out.println("新增维修申请" + repair);
        if (repairService.addRepair(repair)) {
            System.out.println("新建成功");
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getRepairByUserid")
    public List<Repair> getRepairByUserid(@RequestParam int userid) {
        return repairService.getRepairByUserid(userid);
    }


    @RequestMapping("/getUserPostList")
    public List<UserPost> getUserPostList() {
        return userPostService.getAllUserPost();
    }

    @RequestMapping("/addUserPost")
    public String addUserPost(@RequestBody UserPost userPost) {
        if (userPostService.addUserPost(userPost)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getUserPostByUserid")
    public List<UserPost> getUserPostByUserid(@RequestParam int userid) {
        return userPostService.getAllUserPostByUserid(userid);
    }

    @RequestMapping("/getUserPostById")
    public UserPost getUserPostById(@RequestParam int id) {
        return userPostService.getUserPostById(id);
    }

    @RequestMapping("/deleteUserPost")
    public String deleteUserPost(@RequestParam String id) {
        System.out.println("删除帖子:" + id);
        if (userPostService.deleteUserPost(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/getUserById")
    public User getUserById(@RequestParam int id) {
        System.out.println("查询用户id" + id);
        return userService.getUserById(id);
    }

    @RequestMapping("/changePwd")
    public String changePwd(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        String oldPwd = (String) data.get("oldPwd");
        String newPwd = (String) data.get("newPwd");
        System.out.println("修改密码id:" + id + " 旧密码：" + oldPwd + " 旧新密码：" + newPwd);
        return userService.changePwd(id, oldPwd, newPwd);
    }

    @RequestMapping("/updaterUserInfo")
    public String updaterUserInfo(@RequestBody User user) {
        if (userService.updateUser(user)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam int id, HttpSession session) {
        Integer userid = (Integer) session.getAttribute("userid");
        if (userid != null && id == userid) {
            session.removeAttribute("userid");
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/uploadPhoto/{id}")
    public String uploadPhoto(@RequestBody MultipartFile file, @PathVariable int id) throws IOException {
        System.out.println(id);
        System.out.println(file.getName());
        return userService.uploadPhoto(id, file);
    }

    @RequestMapping("/uploadHealth")
    public String uploadHealth(@RequestBody Health health) {
        if (healthService.uploadHealth(health)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getHealthByUserid")
    public List<Health> getHealthByUserid(@RequestParam int userid) {
        return healthService.getHealthByUserid(userid);
    }

    @RequestMapping("/getChatMessageList")
    public List<ChatMessage> getChatMessageList() {
        return chatService.getChatMessageList();
    }

    @RequestMapping("/addTextMessage")
    public String addTextMessage(@RequestBody ChatMessage chatMessage) {
        if (chatService.addUserTextMessage(chatMessage)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/addImageMessage")
    public String addImageMessage(@RequestBody ChatMessage chatMessage) {
        if (chatService.addUserImageMessage(chatMessage)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getCommentByUserpostid")
    public List<Comment> getCommentByUserpostid(int userpostid) {
        return commentService.getCommentByUserpostid(userpostid);
    }

    @RequestMapping("/addComment")
    public String addComment(@RequestBody Comment comment) {
        if (commentService.addComment(comment))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }
    @RequestMapping("/getAdminPostList")
    public List<AdminPost> getAdminPostList(){
        return adminPostService.getAdminPostList();
    }

    @RequestMapping("/getPartyMemberList")
    public List<PartyMember> getPartyMemberList(){
        return partyMemberService.getWorkPartyMember();
    }

    @RequestMapping("/addTempGoodImg")
    public String addTempGoodImg(@RequestBody MultipartFile file) throws IOException {
        return Content.TEMP_IMG_PATH + ImgUtil.uploadAvatarHandler(Content.TEMP_IMG_PATH, file, Content.FAIL);
    }
}
