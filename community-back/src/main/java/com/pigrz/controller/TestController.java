package com.pigrz.controller;

import com.pigrz.pojo.Admin;
import com.pigrz.pojo.RepairItem;
import com.pigrz.pojo.Repair;
import com.pigrz.service.AdminService;
import com.pigrz.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/test")
    public String Test() {
        return "yes";
    }
}
