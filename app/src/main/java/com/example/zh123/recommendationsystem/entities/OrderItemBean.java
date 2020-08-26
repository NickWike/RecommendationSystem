package com.example.zh123.recommendationsystem.entities;

/**
 * Created by zh123 on 20-3-26.
 */

public class OrderItemBean {
    /**
     * area_name : 昌吉回族自治州
     * postage : 0.00
     * product_id : 6394
     * product_image : http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000006394.png
     * product_name : 京东超市【奔富专卖店】奔富BIN系列红酒 澳洲原瓶进口干红葡萄酒 奔富bin128设拉子 750ml整箱6支装
     * quantity : 1
     * shop_name : 奔富红酒品尚专卖店
     * subtotal_fee : 1788.00
     * unit_price : 1788.00
     */

    private String area_name;
    private String postage;
    private int product_id;
    private String product_image;
    private String product_name;
    private int quantity;
    private String shop_name;
    private String subtotal_fee;
    private String unit_price;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getSubtotal_fee() {
        return subtotal_fee;
    }

    public void setSubtotal_fee(String subtotal_fee) {
        this.subtotal_fee = subtotal_fee;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }
}