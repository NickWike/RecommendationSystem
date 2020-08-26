package com.example.zh123.recommendationsystem.entities;

/**
 * Created by zh123 on 20-3-13.
 */

public class UserBean extends RequestBaseJson {
    /**
     * message : 登录成功
     * status_code : 1
     * status_text : OK
     * time_stamp : 1584088149099
     * user_id : 00000000001
     * user_name : root1
     */
    private String user_name;
    private String email;
    private String birthday;
    private String create_time;
    private String pub_key;
    private String session;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPub_key() {
        return pub_key;
    }

    public void setPub_key(String pub_key) {
        this.pub_key = pub_key;
    }

    public void setSession(String session){
        this.session = session;
    }

    public String getSession(){
        return this.session;
    }

    public static String getClassName(){
        String[] arr = UserBean.class.getName().split("\\.");
        return arr[arr.length - 1];
    }

}
