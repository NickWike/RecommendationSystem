package com.example.zh123.recommendationsystem.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.ProductBean;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myfragments.SureOrderFragment;

/**
 * Created by zh123 on 20-3-26.
 */

public class OrderInfoActivity extends AppCompatActivity{

    UserBean mUserBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getSerializableExtra(UserBean.getClassName());
        ProductBean productBean = (ProductBean) intent.getSerializableExtra(ProductBean.getClassName());
        Bundle b = new Bundle();
        b.putSerializable(ProductBean.getClassName(),productBean);
        b.putSerializable(UserBean.getClassName(),mUserBean);
        b.putInt("PageIdentity",intent.getIntExtra("PageIdentity",-1));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SureOrderFragment sureOrderFragment = new SureOrderFragment();
        sureOrderFragment.setArguments(b);
        ft.add(R.id.id_order_info_container,sureOrderFragment);
        ft.commit();
    }

}
