package com.example.zh123.recommendationsystem.entities;

/**
 * Created by zh123 on 20-3-26.
 */

public class ShippingInfoBean {
    /**
     * receiver_address : 番斗花园
     * receiver_city : 重庆
     * receiver_district : 渝中区
     * receiver_mobile : 13100000000
     * receiver_name : 胡图图
     * receiver_province : None
     * shipping_id : 1
     */

    private String receiver_address;
    private String receiver_city;
    private String receiver_district;
    private String receiver_mobile;
    private String receiver_name;
    private String receiver_province;
    private int shipping_id;

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public int getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(int shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getMergedAddressInfo(){
        return this.receiver_province +
                this.receiver_city +
                this.receiver_district +
                this.receiver_address;
    }
}