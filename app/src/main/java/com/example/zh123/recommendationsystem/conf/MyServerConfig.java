package com.example.zh123.recommendationsystem.conf;

/**
 * Created by zh123 on 20-3-13.
 */

public class MyServerConfig {

    private static final String HOST="192.168.0.105";

    private static final int PORT = 1234;

    public static final String SERVER_PATH = "http://" + HOST + ":" + PORT;

    public static final String URL_GET_ORDER_INFO = SERVER_PATH + "/order/pull_order_info.action";

    public static final String URL_COMMIT_ORDER = SERVER_PATH + "/order/commit_order.action";

    public static final String URL_CHECKED_CAR_ITEMS = SERVER_PATH + "/product/car/checked_car_items.action";

    public static final String URL_GET_SHOP_CAR_ITEMS = SERVER_PATH + "/product/car/get_shop_car_items.action";

    public static final String URL_REMOVE_ITEMS_TO_CAR = SERVER_PATH + "/product/car/remove_items_to_car.action";

    public static final String URL_ADD_TO_CAR = SERVER_PATH + "/product/car/add_to_car.action";

    public static final String URL_SEARCH_ITEMS = SERVER_PATH + "/product/search/get_items.action";

    public static final String URL_PRODUCT_DETAIL_INFO = SERVER_PATH + "/product/detail_info.action";

    public static final String URL_GET_HOT_KEYWORD = SERVER_PATH + "/product/get_hot_keyword.action";

    public static final String URL_USER_UPDATE_INFO = SERVER_PATH + "/user/update_info.action";

    public static final String URL_USER_DETAIL_INFO = SERVER_PATH + "/user/detail_info.action";

    public static final String URL_GET_HOME_ITEMS = SERVER_PATH + "/product/get_main_items.action";

    public static final String URL_USER_SIGNUP = SERVER_PATH + "/user/signup";

    public static final String URL_USER_LOGIN = SERVER_PATH + "/user/login";

}
