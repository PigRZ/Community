package com.pigrz.util;

import com.pigrz.pojo.User;
import com.pigrz.pojo.UserPost;

public class Content {
    public static final String ERROR_IMAGE="404.png";
    public static final String SUCCESS="success";
    public static final String FAIL="fail";
    public static final String GOOD_IMG_PATH="/image/good/";
    public static final String USER_PHOTO_PATH="/image/user/";
    public static final String TEMP_IMG_PATH="/image/temp/";
    public static final String USERPOST_IMG_PATH="/image/userpost/";
    public static final String COMMENT_IMG_PATH="/image/comment/";
    public static String getUsrPhotoDir(User user){
        return "/image/user/"+user.getId()+"/";
    }

    public static String getUsrPostDir(UserPost userPost) {
        return USERPOST_IMG_PATH;
    }
}
