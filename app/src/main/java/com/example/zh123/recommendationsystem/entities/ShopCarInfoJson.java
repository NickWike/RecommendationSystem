package com.example.zh123.recommendationsystem.entities;
import java.io.Serializable;


/**
 * Created by zh123 on 20-3-19.
 */

public class ShopCarInfoJson implements Serializable{
    /**
     * data : {"items":[{"checked":1,"item_price":33.8,"product_id":205,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000000205.jpg","product_name":"倾饰阁小清新长耳朵小兔子发夹可爱发卡一字夹刘海夹子ins发饰头饰可爱甜美日韩风格 2#紫罗兰","quantity":2,"shop_name":"亨远数码专营店","unit_price":"16.90"}],"total_price":"2326.00"}
     * status_code : 1
     * status_text : OK
     * time_stamp : 1584612175571
     * user_id : 00000000001
     */

    private ShopCarDataBean data;
    private int status_code;
    private String status_text;
    private long time_stamp;
    private String user_id;

    public ShopCarDataBean getData() {
        return data;
    }

    public void setData(ShopCarDataBean data) {
        this.data = data;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static String getClassName(){
        String[] arr = ShopCarInfoJson.class.getName().split("\\.");
        return arr[arr.length - 1];
    }

}
