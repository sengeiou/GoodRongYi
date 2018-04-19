package com.lichi.goodrongyi.utill;

/**
 * Author:Jenny
 * Date:16/4/23 22:16
 * E-mail:fishloveqin@gmail.com
 * 参数常量
 */
public class Constants {
    public static final String WEIXIN_APP_ID = "wxf91e04313b38b17e"; //微信ID
    public static String BASE_URL = "http://app.lichijituan.cn:11000/"; //基础接口地址
    //public static final String BASE_H5URL = "http://app.lichijituan.cn:8081/"; //h5
    public static String BASE_H5URL = "http://192.168.100.116/workspace/local/lichi_crm/"; //h5
    public static final String KEY_ACCOUNT_FILE = "account";
    public static final int HTTP_LOGIN_ERROR = 10001;
    public static final int HTTP_LOGIN_OUT = 20077;


    private static final boolean IS_TEST = false;

    static {
        if (IS_TEST) { //线上
            BASE_URL="http://app.lichijituan.cn:11000/";
            BASE_H5URL = "http://app.lichijituan.cn:8081/";
        } else { //线下
            BASE_URL = "http://192.168.100.109:8080/";
            //BASE_URL = "http://192.168.100.22:8080/";
            BASE_H5URL = "http://192.168.100.116/workspace/local/lichi_crm/";
    }

    }

    public static interface Navigation {

        String HOME_TAB = "home";
        String CREDITCARD_TAB = "creditcard";
        String FIND_TAB = "find";
        String ME_TAB = "me";

        int HOME_INDEX = 0;
        int SEE_INDEX = 1;
        int PLAY_INDEX = 3;
        int ME_INDEX = 4;
    }


    public static interface IntentParams {
        String ID = "id";
        String ID2 = "id2";
        String ID3 = "id3";
        String ID4 = "id4";
        String INTENT_TYPE = "type";
        String VIDEO_URL = "video_url";
        String INDEX = "index";
        String LOGIN_OUT = "login_out";
        String DATA = "data";
    }

    /**
     * QQ平台
     */
    public static interface TencentShare {
        String APP_KEY = "1106169374";
    }

    public static interface Types {

        int LOAD_MORE_STYLE_1 = 1;
        int LOAD_MORE_STYLE_2 = 2;

    }

}
