package com.example.zh123.recommendationsystem.entities;

import java.util.List;

/**
 * Created by zh123 on 20-3-26.
 */

public class OrderInfoJson extends RequestBaseJson {
    /**
     * data : {"items":[{"area_name":"昌吉回族自治州","postage":"0.00","product_id":6394,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000006394.png","product_name":"京东超市【奔富专卖店】奔富BIN系列红酒 澳洲原瓶进口干红葡萄酒 奔富bin128设拉子 750ml整箱6支装","quantity":1,"shop_name":"奔富红酒品尚专卖店","subtotal_fee":"1788.00","unit_price":"1788.00"},{"area_name":"邵阳市","postage":"6.00","product_id":5656,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000005656.jpg","product_name":"【官方旗舰店】新品上市休闲羽绒服男短款轻薄款中年立领夹克商务反季节大鹅清仓特价 男立领黑色 S","quantity":1,"shop_name":"梵希内衣服饰专营店","subtotal_fee":"315.00","unit_price":"309.00"}],"order_from":0,"shipping_info":{"receiver_address":"番斗花园","receiver_city":"重庆","receiver_district":"渝中区","receiver_mobile":"13100000000","receiver_name":"胡图图","receiver_province":"None","shipping_id":1},"token":"aa2f7901c88baa851700a7b21eef4b3c","total_price":"2103.00"}
     * status_code : 1
     * status_text : OK
     * time_stamp : 1585192764077
     * user_id : 00000000001
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static String getClassName(){
        String[] arr = OrderInfoJson.class.getName().split("\\.");
        return arr[arr.length - 1];
    }

    public static class DataBean {
        /**
         * items : [{"area_name":"昌吉回族自治州","postage":"0.00","product_id":6394,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000006394.png","product_name":"京东超市【奔富专卖店】奔富BIN系列红酒 澳洲原瓶进口干红葡萄酒 奔富bin128设拉子 750ml整箱6支装","quantity":1,"shop_name":"奔富红酒品尚专卖店","subtotal_fee":"1788.00","unit_price":"1788.00"},{"area_name":"邵阳市","postage":"6.00","product_id":5656,"product_image":"http://recommendation-system.oss-cn-chengdu.aliyuncs.com/image_res/00000005656.jpg","product_name":"【官方旗舰店】新品上市休闲羽绒服男短款轻薄款中年立领夹克商务反季节大鹅清仓特价 男立领黑色 S","quantity":1,"shop_name":"梵希内衣服饰专营店","subtotal_fee":"315.00","unit_price":"309.00"}]
         * order_from : 0
         * shipping_info : {"receiver_address":"番斗花园","receiver_city":"重庆","receiver_district":"渝中区","receiver_mobile":"13100000000","receiver_name":"胡图图","receiver_province":"None","shipping_id":1}
         * token : aa2f7901c88baa851700a7b21eef4b3c
         * total_price : 2103.00
         */

        private int order_from;
        private ShippingInfoBean shipping_info;
        private String token;
        private String total_price;
        private String payment;
        private List<OrderItemBean> items;

        public int getOrder_from() {
            return order_from;
        }

        public void setOrder_from(int order_from) {
            this.order_from = order_from;
        }

        public ShippingInfoBean getShipping_info() {
            return shipping_info;
        }

        public void setShipping_info(ShippingInfoBean shipping_info) {
            this.shipping_info = shipping_info;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public List<OrderItemBean> getItems() {
            return items;
        }

        public void setItems(List<OrderItemBean> items) {
            this.items = items;
        }

        public String getPayment(){
            return this.payment;
        }

        public void setPayment(String payment){
            this.payment = payment;
        }

    }
}
