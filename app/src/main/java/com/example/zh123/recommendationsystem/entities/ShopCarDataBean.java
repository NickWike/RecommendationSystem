package com.example.zh123.recommendationsystem.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zh123 on 20-3-20.
 */

public class ShopCarDataBean implements Serializable{
    /**
     * items : [{"checked":1,"item_price":33.8,"product_id":205,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000000205.jpg","product_name":"倾饰阁小清新长耳朵小兔子发夹可爱发卡一字夹刘海夹子ins发饰头饰可爱甜美日韩风格 2#紫罗兰","quantity":2,"shop_name":"亨远数码专营店","unit_price":"16.90"},{"checked":1,"item_price":110.2,"product_id":13405,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000013405.jpg","product_name":"变形玩具金刚6大黄蜂甲壳虫威震V级天机器人汽车模型黑曼巴 电影原版【飞机威】合金原大版","quantity":1,"shop_name":"凯宏手机专营店","unit_price":"110.20"},{"checked":1,"item_price":138,"product_id":2405,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000002405.jpg","product_name":"花花公子秋冬季青少年棉鞋加绒冬天运动冬鞋中大童男鞋10初中12小学生15岁 5815(单鞋)黑色 41","quantity":1,"shop_name":"夏语装潢专营店","unit_price":"138.00"},{"checked":1,"item_price":78,"product_id":8899,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000008899.jpg","product_name":"中老年人加绒棉衣男 男装加绒加厚中老年棉衣男士外套冬季中年小棉袄父亲装棉服秋冬款 小棉衣 XL","quantity":1,"shop_name":"娜瑟薇时鲜岛专卖店","unit_price":"78.00"},{"checked":1,"item_price":1628,"product_id":5566,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000005566.jpg","product_name":"俞兆林白鹅绒羽绒服女2019新款修身收腰时尚爆款加厚保暖短款外套潮特价清仓 香槟色 S","quantity":1,"shop_name":"城旭佳男装专营店","unit_price":"1628.00"},{"checked":1,"item_price":338,"product_id":3568,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000003568.jpg","product_name":"羊羔绒短款仿皮毛一体颗粒短装外套2019秋冬季女宽松百搭加厚学生棉服绒高中生穿的洋气外衣 米白色 M/160","quantity":1,"shop_name":"利安医疗器械专营店","unit_price":"338.00"}]
     * total_price : 2326.00
     */

    private String total_price;
    private List<ShopCarItemsBean> items;

    public String getFormatTotalPrice(){
        return String.format("￥%s", total_price);
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<ShopCarItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ShopCarItemsBean> items) {
        this.items = items;
    }

    public static class ShopCarItemsBean implements Serializable{
        /**
         * checked : 1
         * item_price : 33.8
         * product_id : 205
         * product_image : http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000000205.jpg
         * product_name : 倾饰阁小清新长耳朵小兔子发夹可爱发卡一字夹刘海夹子ins发饰头饰可爱甜美日韩风格 2#紫罗兰
         * quantity : 2
         * shop_name : 亨远数码专营店
         * unit_price : 16.90
         */

        private int checked;
        private String item_price;
        private String product_id;
        private String product_image;
        private String product_name;
        private int quantity;
        private String shop_name;
        private String unit_price;

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
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

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

    }
}