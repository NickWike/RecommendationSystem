package com.example.zh123.recommendationsystem.network.request.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.OrderInfoJson;
import com.example.zh123.recommendationsystem.network.request.RequestTag;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.google.gson.Gson;

import java.util.HashMap;


import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zh123 on 20-3-26.
 */

public class OrderTask extends Thread{
    private Handler mHandler;
    private HashMap<String,String> mRequestData;
    private String mSession;
    private int mTaskType;

    public OrderTask(String session,HashMap<String,String> data,Handler handler,int taskType){
        this.mSession = session;
        this.mRequestData = data;
        this.mHandler = handler;
        this.mTaskType = taskType;
    }

    @Override
    public void run() {
        Message message = new Message();
        Response response = null;
        Gson gson = new Gson();
        Bundle bundle = new Bundle();

        try{
            switch (this.mTaskType){
                case RequestTag.GET_ORDER_INFO_TO_SHOP_CAR:
                    response = RequestUtil.Get(MyServerConfig.URL_GET_ORDER_INFO,this.mSession);
                    break;
                case RequestTag.GET_ORDER_INFO_TO_BUY_BOW:
                    response = RequestUtil.Get(MyServerConfig.URL_GET_ORDER_INFO,this.mRequestData,this.mSession);
                    break;
                case RequestTag.COMMIT_ORDER:
                    response = RequestUtil.Post(MyServerConfig.URL_COMMIT_ORDER,this.mRequestData,this.mSession);
                    break;
            }
            if(response != null && response.isSuccessful()){
                ResponseBody body = response.body();
                assert body != null:"Response body is empty";
                String bodyString = body.string();
                OrderInfoJson orderInfoJson = (OrderInfoJson) gson.fromJson(bodyString,OrderInfoJson.class);
                assert orderInfoJson != null;
                bundle.putSerializable(OrderInfoJson.getClassName(),orderInfoJson);
                message.setData(bundle);
                message.what = this.mTaskType;
            }

        }catch (Exception e){
            message.what = RequestTag.ERROR;
        }finally {
            this.mHandler.sendMessage(message);
        }
    }
}
