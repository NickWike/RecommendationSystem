package com.example.zh123.recommendationsystem.entities;

import java.io.Serializable;

/**
 * Created by zh123 on 20-3-16.
 */

public class ProductBean implements Serializable{

    /**
     * area_code : 141100
     * area_name : 吕梁市
     * average_score : 5.0
     * comment_count : 51
     * general_count : 0
     * good_count : 10
     * good_rate : 1
     * inventory : 1643
     * poor_count : 0
     * price : 3.89
     * postage: 6.00
     * product_id : 98
     * product_image : http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000000098.jpg
     * product_name : ins糖果色滴油发夹金属波浪一字夹刘海夹边夹韩国发饰女 雾蓝圆形
     * product_status : 1
     * month_sales : 3122
     * shop_id : 1
     * shop_name : 朵特瑾饰品官方旗舰店
     */

    private int area_code;
    private String area_name;
    private String average_score;
    private int comment_count;
    private int general_count;
    private int good_count;
    private float good_rate;
    private int inventory;
    private int poor_count;
    private String price;
    private String postage;
    private int product_id;
    private String product_image;
    private String product_name;
    private int product_status;
    private int shop_id;
    private String shop_name;
    private int month_sales;

    public int getArea_code() {
        return area_code;
    }

    public void setArea_code(int area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAverage_score() {
        return average_score;
    }

    public void setAverage_score(String average_score) {
        this.average_score = average_score;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getGeneral_count() {
        return general_count;
    }

    public void setGeneral_count(int general_count) {
        this.general_count = general_count;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public float getGood_rate() {
        return good_rate;
    }

    public void setGood_rate(float good_rate) {
        this.good_rate = good_rate;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getPoor_count() {
        return poor_count;
    }

    public void setPoor_count(int poor_count) {
        this.poor_count = poor_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setMonth_sales(int month_sales){
        this.month_sales = month_sales;
    }

    public int getMonth_sales(){
        return this.month_sales;
    }

    public void setPostage(String postage){
        this.postage = postage;
    }

    public String getPostage(){
        return this.postage;
    }

    public static String getClassName(){
        String[] arr = ProductBean.class.getName().split("\\.");
        return arr[arr.length - 1];
    }
}
