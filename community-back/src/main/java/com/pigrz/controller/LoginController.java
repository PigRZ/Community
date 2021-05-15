package com.pigrz.controller;

import com.pigrz.pojo.Admin;
import com.pigrz.pojo.User;
import com.pigrz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/userlogin")
    public int userLogin(@RequestBody Map<String,String> info, HttpSession session,HttpServletResponse response) {
        String username=info.get("username");
        String password=info.get("password");
        System.out.println("userlogin:"+username+"----"+password);
        User user=loginService.userLogin(username, password);
        if (user!=null) {
            ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId()) // key & value
                    .httpOnly(true)		// 禁止js读取
                    .secure(false)		// 在http下也传输
//                    .domain(".rzwang.cloud")// 域名
                    .path("/")			// path
                    .maxAge(Duration.ofHours(24*7))	// 1个小时候过期
                    .secure(true)
                    .sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                    .build()
                    ;

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            session.setAttribute("userid",user.getId());
            return user.getId();
        }
        else {
            return -1;
        }
    }

    @RequestMapping("/adminlogin")
    public int adminLogin(@RequestBody Map<String,String> info, HttpSession session,HttpServletResponse response) {
        String username=info.get("username");
        String password=info.get("password");
        System.out.println("adminlogin:"+username+"----"+password);
        Admin admin= loginService.adminLoin(username, password);
        if (admin!=null) {
            session.setAttribute("adminid",admin.getId());
            ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId()) // key & value
                    .httpOnly(true)		// 禁止js读取
                    .secure(false)		// 在http下也传输
//                    .domain(".rzwang.cloud")// 域名
                    .path("/")			// path
                    .secure(true)
                    .maxAge(Duration.ofHours(24*7))	// 1个小时候过期
                    .sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                    .build()
                    ;

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return admin.getId();
        }
        else {
            return -1;
        }
    }
}
