package com.pigrz.controller;

import com.pigrz.pojo.*;
import com.pigrz.service.*;
import com.pigrz.util.Content;
import com.pigrz.util.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    GoodService goodService;
    @Autowired
    OrderService orderService;
    @Autowired
    RepairItemService repairItemService;
    @Autowired
    RepairService repairService;
    @Autowired
    AdminPostService adminPostService;
    @Autowired
    UserPostService userPostService;
    @Autowired
    PartyMemberService partyMemberService;
    @Autowired
    HealthService healthService;
    @Autowired
    AdminService adminService;
    @Autowired
    ChatService chatService;


    @RequestMapping("/userList")
    public List<User> getUserList() {
        System.out.println("查询所有用户");
        return userService.getAllUser();
    }

    @RequestMapping("/searchUser")
    public List<User> getUserByNameOrTelephone(@RequestParam String nameOrTelephone) {
        if (nameOrTelephone == null)
            return new ArrayList<>();
        System.out.println("通过姓名或手机号查询用户");
        return userService.getUserByNameOrTelephone(nameOrTelephone);
    }

    @RequestMapping("/selectUser")
    public List<User> getUserBySection(@RequestParam String section, @RequestParam String health) {
        if (section == null || health == null)
            return new ArrayList<>();
        System.out.println("通过楼号和健康状态筛选用户");
        return userService.getUserBySectionAndHealth(section, health);
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {
        if (id == null)
            return Content.FAIL;
        System.out.println("删除用户" + id);
        if (userService.deleteUser(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        if (user == null)
            return Content.FAIL;
        System.out.println("修改用户" + user);
        if (userService.updateUser(user))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        if (user == null)
            return Content.FAIL;
        System.out.println("添加用户" + user);
        if (userService.addUser(user)) {
            User u = userService.getUserByTelephone(user.getTelephone());
            if (u != null)
                return String.valueOf(u.getId());
        }
        return Content.FAIL;
    }


    @RequestMapping("/goodList")
    public List<Good> getGoodList() {
        System.out.println("查询所有商品");
        List<Good> goods = goodService.getAllGood();
        System.out.println("查询到" + goods.size() + "个商品");
        return goods;
    }


    @RequestMapping("/addGood")
    public String addGood(@RequestBody Good good) {
        if (good == null)
            return Content.FAIL;
        System.out.println("添加商品" + good);
        if (goodService.addGood(good)) {
            Good g = goodService.getGoodByUniqueName(good.getName());
            if (g != null)
                return String.valueOf(g.getId());
        }
        return Content.FAIL;
    }

    @RequestMapping("/addTempGoodImg")
    public String addTempGoodImg(@RequestBody MultipartFile file) throws IOException {
        return Content.TEMP_IMG_PATH + ImgUtil.uploadAvatarHandler(Content.TEMP_IMG_PATH, file, Content.FAIL);
    }

    @RequestMapping("/deleteGood")
    public String deleteGood(@RequestParam String id) {
        System.out.println("删除商品id" + id);
        if (goodService.deleteGood(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updateGood")
    public String updateGood(@RequestBody Good good) {
        if (good == null)
            return Content.FAIL;
        System.out.println("更新商品" + good);
        if (goodService.updateGood(good))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/selectGood")
    public List<Good> selectGood(@RequestParam String type) {
        if (type == null) {
            return new ArrayList<>();
        } else if (type.equals("")) {
            return goodService.getAllGood();
        } else {
            System.out.println("查找类型为" + type + "的商品");
            return goodService.getGoodByType(type);
        }
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


    @RequestMapping("/orderList")
    public List<Order> getOrderList() {
        System.out.println("查询所有订单");
        List<Order> orders = orderService.getAllOrder();
        System.out.println("查询到" + orders.size() + "个订单");
        return orders;
    }

    @RequestMapping("/addOrder")
    public String addOrder(@RequestBody Order order) {
        return Content.FAIL;
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam String id) {
        System.out.println("删除订单id" + id);
        if (orderService.deleteOrder(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/completeOrder")
    public String completeOrder(@RequestParam int id) {
        System.out.println("完成订单id" + id);
        if (orderService.completeOrder(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updateOrder")
    public String updateOrder(@RequestBody Order order) {
        if (order == null)
            return Content.FAIL;
        System.out.println("更新订单" + order);
        if (orderService.updateOrder(order))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }


    @RequestMapping("/searchOrderByUsername")
    public List<Order> searchOrderByUsername(@RequestParam String username) {
        System.out.println("查询居民姓名包含了" + username + "的订单");
        return orderService.getOrderByUsername(username);
    }

    @RequestMapping("/searchOrderByGoodname")
    public List<Order> searchOrderByGoodname(@RequestParam String goodname) {
        System.out.println("查询商品名称包含了" + goodname + "的订单");
        return orderService.getOrderByGoodname(goodname);
    }


    @RequestMapping("/repairItemList")
    public List<RepairItem> getRepairItemList() {
        System.out.println("查询所有维修项目");
        List<RepairItem> repairItems = repairItemService.getAllRepairItem();
        System.out.println("查询到" + repairItems.size() + "个维修项目");
        return repairItems;
    }

    @RequestMapping("/addRepairItem")
    public String addRepairItem(@RequestBody RepairItem repairItem) {
        if (repairItem == null)
            return Content.FAIL;
        System.out.println("添加维修项目" + repairItem);
        if (repairItemService.addRepairItem(repairItem)) {
            RepairItem r = repairItemService.getRepairItemByUniqueName(repairItem.getName());
            if (r != null)
                return String.valueOf(r.getId());
        }
        return Content.FAIL;
    }

    @RequestMapping("/deleteRepairItem")
    public String deleteRepairItem(@RequestParam String id) {
        System.out.println("删除维修项目id" + id);
        if (repairItemService.deleteRepairItem(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updateRepairItem")
    public String updateRepairItem(@RequestBody RepairItem repairItem) {
        if (repairItem == null)
            return Content.FAIL;
        System.out.println("更新维修项目" + repairItem);
        if (repairItemService.updateRepairItem(repairItem))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/selectRepairItem")
    public List<RepairItem> selectRepairItem(@RequestParam String type) {
        if (type == null) {
            return new ArrayList<>();
        } else if (type.equals("")) {
            return repairItemService.getAllRepairItem();
        } else {
            System.out.println("查找类型为" + type + "的维修项目");
            return repairItemService.getRepairItemByType(type);
        }
    }

    @RequestMapping("/searchRepairItem")
    public List<RepairItem> searchRepairItem(@RequestParam String name) {
        if (name == null) {
            return new ArrayList<>();
        } else if (name.equals("")) {
            return repairItemService.getAllRepairItem();
        } else {
            System.out.println("查找名称为" + name + "的维修项目");
            return repairItemService.getRepairItemByName(name);
        }
    }


    @RequestMapping("/repairList")
    public List<Repair> getRepairList() {
        System.out.println("查询所有报修记录");
        List<Repair> repairs = repairService.getAllRepair();
        System.out.println("查询到" + repairs.size() + "个报修记录");
        return repairs;
    }

    @RequestMapping("/completeRepair")
    public String completeRepair(@RequestParam int id) {
        System.out.println("完成维修id" + id);
        if (repairService.completeRepair(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/addRepair")
    public String addRepair(@RequestBody Repair repair) {
        return Content.FAIL;
    }

    @RequestMapping("/deleteRepair")
    public String deleteRepair(@RequestParam String id) {
        System.out.println("删除报修记录id" + id);
        if (repairService.deleteRepair(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updateRepair")
    public String updateRepair(@RequestBody Repair repair) {
        if (repair == null)
            return Content.FAIL;
        System.out.println("更新报修记录" + repair);
        if (repairService.updateRepair(repair))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }


    @RequestMapping("/searchRepairByUsername")
    public List<Repair> searchRepairByUsername(@RequestParam String username) {
        System.out.println("查询居民姓名包含了" + username + "的报修记录");
        return repairService.getRepairByUsername(username);
    }

    @RequestMapping("/searchRepairByRepairitemname")
    public List<Repair> searchRepairByRepairitemname(@RequestParam String repairitemname) {
        System.out.println("查询维修项目名称包含了" + repairitemname + "的报修记录");
        return repairService.getRepairByRepairitemname(repairitemname);
    }


    @RequestMapping("/adminPostList")
    public List<AdminPost> getAdminPostList(@RequestParam String adminid) {
        System.out.println("查询该管理员发帖记录");
        List<AdminPost> adminPosts = adminPostService.getAllAdminPostByAdminid(adminid);
        System.out.println("查询到" + adminPosts.size() + "个报修记录");
        return adminPosts;
    }


    @RequestMapping("/addAdminPost")
    public String addAdminPost(@RequestBody AdminPost adminPost) {
        System.out.println("发布帖子:" + adminPost.getTitle() + "\n" + adminPost.getContent());
        if (adminPostService.addAdminPost(adminPost))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/deleteAdminPost")
    public String deleteAdminPost(@RequestParam String id) {
        System.out.println("删除帖子:" + id);
        if (adminPostService.deleteAdminPost(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }


    @RequestMapping("/userPostList")
    public List<UserPost> getUserPostList() {
        System.out.println("查询所有剧名发帖记录");
        List<UserPost> userPosts = userPostService.getAllUserPost();
        System.out.println("查询到" + userPosts.size() + "个报修记录");
        return userPosts;
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


    @RequestMapping("/partyMemberList")
    public List<PartyMember> getPartyMemberList() {
        System.out.println("查询所有党员信息");
        List<PartyMember> partyMembers = partyMemberService.getAllPartyMember();
        System.out.println("查询到" + partyMembers.size() + "个党员");
        return partyMembers;
    }

    @RequestMapping("/addPartyMember")
    public String addPartyMember(@RequestBody PartyMember partyMember) {
        if (partyMember == null)
            return Content.FAIL;
        System.out.println("添加党员记录" + partyMember);
        if (partyMemberService.addPartyMember(partyMember)) {
            PartyMember p = partyMemberService.getPartyMemberByTelephone(partyMember.getTelephone());
            if (p != null)
                return String.valueOf(p.getId());
        }
        return Content.FAIL;
    }

    @RequestMapping("/deletePartyMember")
    public String deletePartyMember(@RequestParam String id) {
        System.out.println("删除党员id" + id);
        if (partyMemberService.deletePartyMember(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/updatePartyMember")
    public String updatePartyMember(@RequestBody PartyMember partyMember) {
        if (partyMember == null)
            return Content.FAIL;
        System.out.println("更新党员" + partyMember);
        if (partyMemberService.updatePartyMember(partyMember))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/searchPartyMember")
    public List<PartyMember> searchPartyMember(@RequestParam String name) {
        if (name == null) {
            return new ArrayList<>();
        } else if (name.equals("")) {
            return partyMemberService.getAllPartyMember();
        } else {
            System.out.println("查找名称为" + name + "的党员");
            return partyMemberService.getPartyMemberByName(name);
        }
    }

    @RequestMapping("/setPartyMemberWork")
    public String setPartyMemberWork(@RequestParam String id) {
        if (partyMemberService.setPartyMemberWork(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/setPartyMemberNoWork")
    public String setPartyMemberNoWork(@RequestParam String id) {
        if (partyMemberService.setPartyMemberNoWork(id))
            return Content.SUCCESS;
        else
            return Content.FAIL;
    }

    @RequestMapping("/getHealthByUserid")
    public List<Health> getHealthByUserid(@RequestParam int userid) {
        return healthService.getHealthByUserid(userid);
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam int id, HttpSession session) {
        Integer adminid = (Integer) session.getAttribute("adminid");
        if (adminid != null && id == adminid) {
            session.removeAttribute("adminid");
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/changePwd")
    public String changePwd(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        String oldPwd = (String) data.get("oldPwd");
        String newPwd = (String) data.get("newPwd");
        System.out.println("修改密码id:" + id + " 旧密码：" + oldPwd + " 旧新密码：" + newPwd);
        if (adminService.changePwd(id, oldPwd, newPwd)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/getChatMessageList")
    public List<ChatMessage> getChatMessageList() {
        return chatService.getChatMessageList();
    }

    @RequestMapping("/addTextMessage")
    public String addTextMessage(@RequestBody ChatMessage chatMessage) {
        if (chatService.addAdminTextMessage(chatMessage)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }

    @RequestMapping("/addImageMessage")
    public String addImageMessage(@RequestBody ChatMessage chatMessage) {
        if (chatService.addAdminImageMessage(chatMessage)) {
            return Content.SUCCESS;
        } else {
            return Content.FAIL;
        }
    }
    @RequestMapping("/getAdminById")
    public Admin getAdminById(@RequestParam int id){
        return adminService.getAdminById(id);
    }
}
