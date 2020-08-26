package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.user.LoginActivity;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.network.response.ServerResultStatus;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.example.zh123.recommendationsystem.utils.SecretKeyUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import okhttp3.Response;

/**
 * Created by zh123 on 20-3-14.
 */

public class LoginFragment extends Fragment{
    private EditText mEditUserName;
    private EditText mEditPassword;
    private Button mBtnStartLogin;
    private Button mBtnToSignup;
    private MyHandler mHandler;
    private ImageView mVisibilityPassword;
    private ImageView mClearUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        initView(v);
        initListener();
        return v;
    }

    private void initView(View v){
        mHandler = new MyHandler();
        mEditUserName = v.findViewById(R.id.edit_user_name);
        mEditPassword = v.findViewById(R.id.edit_user_password);
        mBtnStartLogin = v.findViewById(R.id.btn_start_login);
        mBtnToSignup = v.findViewById(R.id.btn_to_signup);
        mClearUsername = v.findViewById(R.id.id_clear_username);
        mVisibilityPassword = v.findViewById(R.id.id_visibility_password);
        mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

    }
    private void initListener(){
        mBtnStartLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEditUserName.getText().toString();
                String userPassword = mEditPassword.getText().toString();
                (new CommitLoginThread(userName,userPassword,mHandler)).start();
            }
        });

        mBtnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity loginActivity = (LoginActivity) getActivity();
                if(loginActivity != null){
                    loginActivity.changeFragment(PageIdentity.SIGNUP_FRAGMENT);
                }
            }
        });

        mClearUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditUserName.setText("");
            }
        });

        mVisibilityPassword.setOnClickListener(new View.OnClickListener() {
            private boolean passwordIsVisible = false;

            @Override
            public void onClick(View view) {
                this.passwordIsVisible = !this.passwordIsVisible;
                if(passwordIsVisible){
                    mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mVisibilityPassword.setBackgroundResource(R.mipmap.icon_hide_text);
                }
                else{
                    mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mVisibilityPassword.setBackgroundResource(R.mipmap.icon_display_text);
                }
            }
        });
    }

    class CommitLoginThread extends Thread{
        String username;
        String password;
        MyHandler handler;

        CommitLoginThread(String u,String p,MyHandler h){
            username = u;
            password = p;
            handler = h;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = -1;
            try {
                String cipherPassword = SecretKeyUtil.encryptWithRSA(password);

                HashMap<String,String> data = new HashMap<>();
                data.put("user_name",username);
                data.put("password",cipherPassword);

                Response response = RequestUtil.Post(MyServerConfig.SERVER_PATH + "/user/login",data);

                if(response!=null && response.isSuccessful()){
                    String bodyString = response.body().string();
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(bodyString,UserBean.class);
                    if(userBean !=null){
                        Bundle bundle = new Bundle();
                        if (userBean.getStatus_code() == ServerResultStatus.OK){
                            String session = RequestUtil.getSession(response);
                            userBean.setSession(session);
                        }
                        bundle.putSerializable(UserBean.getClassName(), userBean);
                        message.setData(bundle);
                        message.what = 1;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(message);
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case -1:
                    Toast.makeText(getContext(),"登录异常",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    LoginActivity parentActivity  = (LoginActivity) getActivity();
                    UserBean userBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
                    if(userBean == null) break;
                    Toast.makeText(getContext(), userBean.getMessage(),Toast.LENGTH_LONG).show();
                    if(parentActivity != null && userBean.getStatus_code()==ServerResultStatus.OK){
                        parentActivity.startMainActivity(bundle);
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
