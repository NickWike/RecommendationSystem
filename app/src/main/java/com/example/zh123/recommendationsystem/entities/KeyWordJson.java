package com.example.zh123.recommendationsystem.entities;

import java.io.Serializable;

/**
 * Created by zh123 on 20-3-17.
 */

public class KeyWordJson extends RequestBaseJson {

    /**
     * data : {"keyword":"苹果台式电脑一体机","keyword_id":"00000000103"}
     * user_id : 00000000001
     */

    private KeywordBean data;

    public KeywordBean getData() {
        return data;
    }

    public void setData(KeywordBean data) {
        this.data = data;
    }

    public static String getClassName(){
        String[] arr = KeyWordJson.class.getName().split("\\.");
        return arr[arr.length - 1];
    }

    public static class KeywordBean implements Serializable{
        /**
         * keyword : 苹果台式电脑一体机
         * keyword_id : 00000000103
         */

        private String keyword;
        private String keyword_id;
        private String order_by = "id";
        private int page = 1;
        private Boolean isEndPage=false;
        private Boolean isReceived=true;
        private Boolean reverse = false;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword_id() {
            return keyword_id;
        }

        public void setKeyword_id(String keyword_id) {
            this.keyword_id = keyword_id;
        }

        public String getOrder_by() {
            return order_by;
        }

        public void setOrder_by(String order_by) {
            this.order_by = order_by;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void nextPage(){
            this.page += 1;
        }

        public Boolean getReverse() {
            return reverse;
        }

        public void setReverse(Boolean reverse) {
            this.reverse = reverse;
        }

        public Boolean isEndPage() {
            return isEndPage;
        }

        public void setEndPage(Boolean endPage) {
            this.isEndPage = endPage;
        }

        public Boolean isReceived(){
            return this.isReceived;
        }

        public void setIsReceived(Boolean isReceived){
            this.isReceived = isReceived;
        }

        public void resetAll(){
            this.keyword="";
            this.keyword_id="";
            this.order_by = "id";
            this.page = 1;
            this.isEndPage=false;
            this.reverse = false;
            this.isReceived = true;
        }

        public static String getClassName(){
            String[] arr = KeywordBean.class.getName().split("\\.");
            return arr[arr.length - 1];
        }
    }
}
