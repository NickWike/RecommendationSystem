package com.example.zh123.recommendationsystem.network.request;

/**
 * Created by zh123 on 20-3-26.
 */

public class RequestTag {
    public static final int ERROR = -0x01;
    public static final int LOGIN_TO_SESSOIN = 0x00;
    public static final int LOGIN_TO_PASSWORD = 0x01;
    public static final int SIGN_UP= 0x02;
    public static final int GET_HOME_ITEMS_INFO= 0x03;
    public static final int GET_HOT_KEY_WORD = 0x04;
    public static final int SEARCH_PRODUCT = 0x05;
    public static final int GET_CAR_ITEMS = 0x06;
    public static final int UPDATE_ITEMS_QUANTITY = 0x07;
    public static final int REMOVE_CAR_ITEMS = 0x08;
    public static final int CHECKED_CAR_ITEMS = 0x09;
    public static final int CLOSE_ACCOUNT = 0x10;
    public static final int BUY_NOW = 0x11;
    public static final int GET_PRODUCT_DETAIL_INFO = 0x12;
    public static final int ADD_TO_SHOP_CAR = 0x13;
    public static final int COLLECTION = 0x14;
    public static final int GET_ORDER_INFO_TO_SHOP_CAR = 0x15;
    public static final int GET_ORDER_INFO_TO_BUY_BOW = 0x16;
    public static final int COMMIT_ORDER = 0x17;

}
