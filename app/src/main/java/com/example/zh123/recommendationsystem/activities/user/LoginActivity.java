package com.example.zh123.recommendationsystem.activities.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.MainActivity;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myfragments.LoginFragment;
import com.example.zh123.recommendationsystem.myfragments.PageIdentity;
import com.example.zh123.recommendationsystem.myfragments.SignupFragment;
import com.example.zh123.recommendationsystem.utils.SecretKeyUtil;

/**
 * Created by zh123 on 20-3-13.
 */


public class LoginActivity extends AppCompatActivity {
    private LoginFragment mLoginFragment;
    private SignupFragment mSignupFragment;
    private TextView mBarText;
    private ImageView mBackLogin;
    private String mPubKey;

    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFragmentManager = getSupportFragmentManager();
        mBarText = findViewById(R.id.login_bar_text);
        mBackLogin = findViewById(R.id.back_login);
        mBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(PageIdentity.LOGIN_FRAGMENT);
                view.setVisibility(View.INVISIBLE);
            }
        });
        Intent intent = getIntent();
        mPubKey = intent.getStringExtra("PubKey");
        SecretKeyUtil.loadPublicKey(mPubKey);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadDefaultFragment();
    }

    private void loadDefaultFragment(){
        mLoginFragment = new LoginFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.login_fragment_content,mLoginFragment);
        ft.commit();
    }

    public void changeFragment(int id){
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        hideFragment(ft);
        switch (id){
            case PageIdentity.LOGIN_FRAGMENT:
                if(mLoginFragment == null){
                    mLoginFragment = new LoginFragment();
                    ft.add(R.id.login_fragment_content,mLoginFragment);
                }else{
                    ft.show(mLoginFragment);
                }
                mBarText.setText("登录");
                break;
            case PageIdentity.SIGNUP_FRAGMENT:
                if(mSignupFragment == null){
                    mSignupFragment = new SignupFragment();
                    ft.add(R.id.login_fragment_content,mSignupFragment);
                } else{
                  ft.show(mSignupFragment);
                }
                mBackLogin.setVisibility(View.VISIBLE);
                mBarText.setText("注册");
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft){
        if(mLoginFragment != null){
            ft.hide(mLoginFragment);
        }
        if(mSignupFragment != null){
            ft.hide(mSignupFragment);
        }
    }

    public void startMainActivity(Bundle bundle){
        Intent intent = new Intent(this, MainActivity.class);
        UserBean userBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
        if(userBean !=null){
            SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Session", userBean.getSession());
            editor.apply();
            intent.putExtra(UserBean.getClassName(), userBean);
            startActivity(intent);
            finish();
        }
    }

}
