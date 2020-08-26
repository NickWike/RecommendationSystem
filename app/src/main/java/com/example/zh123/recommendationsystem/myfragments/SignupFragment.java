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
import android.widget.RadioGroup;
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

public class SignupFragment extends Fragment{
    private View mMainView;

    private EditText mEditUserName;
    private EditText mEditPassword;
    private EditText mEditConfirmPassword;
    private Button mBtnCommitSignup;
    private RadioGroup mGender;
    private MyHandler mMyHandler;
    private ImageView mVisibilityPassword;
    private ImageView mVisibilityConfirmPassword;
    private ImageView mClearUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_signup,container,false);
        initView();
        initListener();
        return mMainView;
    }

    private void initView(){
        mMyHandler = new MyHandler();
        mEditUserName = mMainView.findViewById(R.id.edit_user_name);
        mEditPassword = mMainView.findViewById(R.id.edit_user_password);
        mEditConfirmPassword = mMainView.findViewById(R.id.edit_confirm_password);
        mBtnCommitSignup = mMainView.findViewById(R.id.btn_start_signup);
        mGender = mMainView.findViewById(R.id.gender_choose);
        mVisibilityPassword = mMainView.findViewById(R.id.id_visibility_password);
        mVisibilityConfirmPassword = mMainView.findViewById(R.id.id_visibility_confirm_password);
        mClearUsername = mMainView.findViewById(R.id.id_clear_username);
        mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEditConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void initListener(){
        mBtnCommitSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mEditPassword.getText().toString();
                String confirmPassword = mEditConfirmPassword.getText().toString();
                if(!password.equals(confirmPassword)){
                    sendToast("两次输入的密码不一致");
                    return;
                }
                String username = mEditUserName.getText().toString();
                if (username.length() >= 24 || username.length() < 6){
                    sendToast("用户名的长度请在6~24之间");
                    return;
                }
                if (password.length() >= 16 || password.length() < 6){
                    sendToast("密码的长度请在6~16之间");
                    return;
                }

                String gender = mMainView.findViewById(mGender.getCheckedRadioButtonId()).getTag().toString();
                HashMap<String,String> d = new HashMap<>();
                d.put("user_name",username);
                d.put("password",password);
                d.put("gender",gender);
                new SignUpThread(mMyHandler,d).start();
//                Toast.makeText(getContext(),"注册成功" + gender,Toast.LENGTH_LONG).show();
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

        mVisibilityConfirmPassword.setOnClickListener(new View.OnClickListener() {
            private boolean passwordIsVisible = false;

            @Override
            public void onClick(View view) {
                this.passwordIsVisible = !this.passwordIsVisible;
                if(passwordIsVisible){
                    mEditConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mVisibilityConfirmPassword.setBackgroundResource(R.mipmap.icon_hide_text);
                }
                else{
                    mEditConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mVisibilityConfirmPassword.setBackgroundResource(R.mipmap.icon_display_text);
                }
            }
        });

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case -1:
                    sendToast("注册异常");
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    UserBean userBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
                    sendToast(userBean.getMessage());
                    LoginActivity parentActivity  = (LoginActivity) getActivity();
                    if(parentActivity != null && userBean.getStatus_code()==ServerResultStatus.OK){
                        parentActivity.startMainActivity(bundle);
                    }
                    break;
            }
        }
    }

    class SignUpThread extends Thread{
        MyHandler hander;
        HashMap<String,String> data;
        SignUpThread(MyHandler h, HashMap<String,String> d){
            hander = h;
            data = d;
        }

        private void commitSignup(){
            Message message = new Message();
            message.what = -1;
            try{
                data.put("password",SecretKeyUtil.encryptWithRSA(data.get("password")));
                Response response = RequestUtil.Post(MyServerConfig.SERVER_PATH+"/user/signup",data);
                if(response != null && response.isSuccessful()){
                    String bodyString = response.body().string();
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(bodyString,UserBean.class);
                    if(userBean !=null){
                       Bundle bundle = new Bundle();
                       if(userBean.getStatus_code() == ServerResultStatus.OK){
                           String session = RequestUtil.getSession(response);
                           userBean.setSession(session);
                       }
                       bundle.putSerializable(UserBean.getClassName(), userBean);
                       message.setData(bundle);
                       message.what = 1;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            hander.sendMessage(message);
        }

        @Override
        public void run() {
            commitSignup();
        }

    }


    @Override
    public void onDestroy() {
        mMyHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void sendToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

}
