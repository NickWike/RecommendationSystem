package com.example.zh123.recommendationsystem.utils;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zh123 on 20-3-15.
 */

public class RequestUtil {

    public static Response Post(String url,HashMap<String,String> data,String session){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder postBody = new FormBody.Builder();

        Response response = null;

        for(HashMap.Entry<String,String> m: data.entrySet()){
            postBody.add(m.getKey(),m.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie",session)
                .post(postBody.build())
                .build();

        try {
             response = client.newCall(request).execute();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public static Response Post(String url,HashMap<String,String> params){
        return Post(url,params,"");
    }


    public static Response Get(String url,HashMap<String,String> params,String session){

        Response response = null;

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();

        for(HashMap.Entry<String,String> m: params.entrySet()){
            urlBuilder.addQueryParameter(m.getKey(),m.getValue());
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .header("Cookie",session)
                .build();

        try{
            response = client.newCall(request).execute();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public static Response Get(String url){
        return Get(url,"");
    }

    public static Response Get(String url,String session){
        return Get(url,new HashMap<String, String>(),session);
    }

    public static String getSession(Response response){
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        return cookies.get(0).split(";")[0];
    }

}
