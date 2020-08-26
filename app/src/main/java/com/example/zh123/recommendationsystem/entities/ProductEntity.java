package com.example.zh123.recommendationsystem.entities;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zh123 on 20-1-12.
 */

// 作为一个商品的主体
public class ProductEntity {
    // 商品的价格
    private BigDecimal goodsPrice = new BigDecimal(0.00);
    // 商品价格的字符串形式
    private String goodsPriceString ="";
    // 商品的名称
    private String goodsTitle = "";
    // 商品的图片地址
    private String goodsImage = "";
    // 商店的图片地址
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
    // 被加在购物车内的数量
    private int chooseCount = 0;
    // 设置商品价格的显示格式
    private DecimalFormat df = new DecimalFormat("0.00");



    public ProductEntity(String title){this.goodsTitle = title;}
    public ProductEntity(String title, BigDecimal price){
        this.goodsTitle = title;
        this.goodsPrice = price;
        this.goodsPriceString = df.format(goodsPrice);
    }
    public ProductEntity(String title, BigDecimal price, String url){
        this.goodsTitle = title;
        this.goodsPrice = price;
        this.shopImage = url;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
        this.goodsPriceString = df.format(goodsPrice);
    }

    public String getGoodsPriceString(){
        return "¥"+ goodsPriceString;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
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

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public int getChooseCount() {
        return chooseCount;
    }

    public void setChooseCount(int chooseCount) {
        this.chooseCount = chooseCount >= 1 ? chooseCount:1;
    }
}
