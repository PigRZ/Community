package com.pigrz.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ImgUtil {

    public static String uploadAvatarHandler(String dir,MultipartFile uploadFile,String fail) throws IOException {
        if(StringUtil.nullOrEmpty(dir)){
            return fail;
        }
        File uploadDir = new File(FileUtil.ROOT_PATH,"/static/"+dir);
        if (!uploadDir.exists()) {
            System.out.println("上传图片路径不存在，正在创建..."+uploadDir.getPath());
            if(uploadDir.mkdir()){
                System.out.println("创建成功");
            }
            else {
                System.out.println("创建失败");
            }
        }
        if ( uploadFile != null) {
            //获得上传文件的文件名
            String oldName = uploadFile.getOriginalFilename();
            System.out.println("[上传的文件名]：" + oldName);

            String extension = getFileExtension(uploadFile);  //获取文件后缀

            UUID uuid = UUID.randomUUID();  //这里调用了UUID，得到全宇宙唯一的命名
            String str = uuid.toString(); // 真正的UUID打印出来是这样的：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12)
            //所以我们可以去掉去掉"-"符号
            String picname = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) +   str.substring(19, 23) + str.substring(24);
            String relativeAddr = picname  + extension;  //唯一的名字接上后缀

            File avatar = new File( uploadDir.getAbsolutePath(), relativeAddr);
            try {
                //保存图片
                uploadFile.transferTo(avatar);
                //返回成功结果，附带文件的相对路径
                return relativeAddr;
            }catch (IOException e) {
                e.printStackTrace();
                return fail;
            }
        }else {
            System.out.println("上传的文件为空");
            return fail;
        }

    }

    private static String getFileExtension(MultipartFile File) {
        String originalFileName = File.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));

    }
}
