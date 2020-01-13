package com.example.zh123.recommendationsystem.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zh123 on 20-1-12.
 */

// 作为一个商品的主体
public class ShopBean {
    // 商品的价格
    private BigDecimal shopPrice = new BigDecimal(0.00);
    // 商品价格的字符串形式
    private String shopPriceString="";
    // 商品的名称
    private String shopTitle = "";
    // 商品的图片地址
    private String shopImage = "";
    // 商品的评论数
    private int comments = 0;
    // 商品的好评数
    private int favorites = 0;
    // 商品所属店铺的名称
    private String shopName = "";
    // 商品所属店铺的ID
    private String shopID = "";
    // 商品的库存
    private int inventory=0;
    // 商品的月销售量
    private int monthlySales=0;
    // 商品好评率
    private int startLevel=3;
    // 设置商品价格的显示格式
    private DecimalFormat df = new DecimalFormat(".00");



    public ShopBean(String title){this.shopTitle = title;}
    public ShopBean(String title, BigDecimal price){
        this.shopTitle = title;
        this.shopPrice = price;
        this.shopPriceString = df.format(shopPrice);
    }
    public ShopBean(String title, BigDecimal price, String url){
        this.shopTitle = title;
        this.shopPrice = price;
        this.shopImage = url;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
        this.shopPriceString = df.format(shopPrice);
    }

    public String getShopPriceString(){
        return "¥"+shopPriceString;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
    }
}
