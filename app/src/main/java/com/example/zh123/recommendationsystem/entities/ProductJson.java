package com.example.zh123.recommendationsystem.entities;

import java.util.List;

/**
 * Created by zh123 on 20-3-16.
 */

public class ProductJson extends RequestBaseJson {
    private List<ProductBean> items;

    public List<ProductBean> getItems() {
        return items;
    }

    public void setItems(List<ProductBean> items) {
        this.items = items;
    }

    public static String getClassName(){
        String[] arr = ProductJson.class.getName().split("\\.");
        return arr[arr.length - 1];
    }

}
