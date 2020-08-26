package com.example.zh123.recommendationsystem.activities.promotion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.zh123.recommendationsystem.MainActivity;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.user.LoginActivity;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.network.response.ServerResultStatus;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by zh123 on 20-3-13.
 */


public class PromotionPageActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String mSessionValue;
    private String mPubKey;
    private MyLoginHandler mHandler;
    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        sp = this.getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        init();
    }

    private void init(){
        initHistoryData();
        mHandler = new MyLoginHandler();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        new LoginThread(this.mHandler).start();
    }

    private void initHistoryData(){
        mSessionValue = sp.getString("Session","");
        mPubKey = sp.getString("PubKey","");
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        mUserBean.setSession(mSessionValue);
        intent.putExtra(UserBean.getClassName(),this.mUserBean);
        startActivity(intent);
        finish();
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("PubKey",mPubKey);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PubKey",mPubKey);
        // commit 为阻塞型   apply 为非阻塞型后台执行
        editor.apply();
        startActivity(intent);
        finish();
    }

    class MyLoginHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if(msg.what == -1){
                return;
            }
            mUserBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
            switch (mUserBean.getStatus_code()){
                case ServerResultStatus.OK:
                    startMainActivity();
                    break;
                case ServerResultStatus.NO_LOGIN:
                    mPubKey = mUserBean.getPub_key();
                    startLoginActivity();
            }

        }
    }


    class LoginThread extends Thread{
        private MyLoginHandler handler;

        LoginThread(MyLoginHandler handler){
            this.handler = handler;
        }

        @Override
        public void run() {
            try{
                Message message = commitLogin(mSessionValue);
                this.handler.sendMessage(message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private Message commitLogin(String session) throws IOException {
            Message message = new Message();
            message.what = -1;

            Response response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/user/login",session);

            if(response!= null && response.isSuccessful()){
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    String jsonString = responseBody.string();
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(jsonString,UserBean.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("UserBean", userBean);
                    message.what = userBean.getStatus_code();
                    message.setData(bundle);
                }
            }
            return message;
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
