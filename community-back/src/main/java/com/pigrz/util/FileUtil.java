package com.pigrz.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtil {
    public static String ROOT_PATH="";
    static{
        try {
            // 项目的编译文件的根目录
            String path = URLDecoder.decode(FileUtil.class.getResource("/").getPath(), String.valueOf(StandardCharsets.UTF_8));
            if (path.startsWith("file:")) {
                int i = path.indexOf(".jar!");
                path = path.substring(0, i);
                path = path.replaceFirst("file:", "");
            }
            // 项目所在的目录
            String absolutePath=new File(path).getParentFile().getAbsolutePath();
            if(absolutePath.endsWith("/target")||absolutePath.endsWith("\\target")){
                absolutePath=absolutePath.substring(0,absolutePath.length()-7);
            }
            ROOT_PATH = absolutePath;
            System.out.println(ROOT_PATH);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            ROOT_PATH = FileUtil.class.getResource("/").getPath();
        }
    }
//    public static String getRootPath(){
//        try {
//            // 项目的编译文件的根目录
//            String path = URLDecoder.decode(FileUtil.class.getResource("/").getPath(), String.valueOf(StandardCharsets.UTF_8));
//            if (path.startsWith("file:")) {
//                int i = path.indexOf(".jar!");
//                path = path.substring(0, i);
//                path = path.replaceFirst("file:", "");
//            }
//            // 项目所在的目录
//            String absolutePath=new File(path).getParentFile().getAbsolutePath();
//            if(absolutePath.endsWith("/target")||absolutePath.endsWith("\\target")){
//                absolutePath=absolutePath.substring(0,absolutePath.length()-7);
//            }
//            return absolutePath;
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//            return FileUtil.class.getResource("/").getPath();
//        }
//    }

    public static boolean moveFile(String oldDir,String newDir,String filename){
        System.out.println(oldDir+"---"+newDir+"---"+filename);
        try {
//            String path = ResourceUtils.getURL("classpath:").getPath();
//            String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath()+"/static/";
//            path = URLDecoder.decode(path, "UTF-8") + "/static/";
            // 打开输入流
            File oldDirPath=new File(ROOT_PATH,"/static/"+oldDir);
            File newDirPath=new File(ROOT_PATH,"/static/"+newDir);
            System.out.println(oldDirPath.getAbsolutePath()+"/" + filename);
            System.out.println(newDirPath.getAbsolutePath()+"/" + filename);
            FileInputStream fis = new FileInputStream(oldDirPath.getAbsolutePath()+"/" + filename);
            // 打开输出流
            FileOutputStream fos = new FileOutputStream(newDirPath.getAbsolutePath()+"/" +  filename);
            // 读取和写入信息
            int len = 0;
            // 创建一个字节数组，当做缓冲区
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                fos.write(b);
            }

            // 关闭流  先开后关  后开先关
            fos.close(); // 后开先关
            fis.close(); // 先开后关
            File file=new File(ROOT_PATH, "/static/"+oldDir+"/" + filename);
            if(file.isFile()) {
                System.out.println("删除文件"+file.getName());
                boolean delete = file.delete();
                if(delete)
                    System.out.println("成功");
                else
                    System.out.println("失败");
            }
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
