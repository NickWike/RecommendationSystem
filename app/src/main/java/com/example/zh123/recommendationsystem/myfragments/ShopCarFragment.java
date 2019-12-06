package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zh123.recommendationsystem.R;

/**
 * Created by zh123 on 19-12-6.
 */

/**
 * 此fragment作为'购物车'选项对应的界面
 */
public class ShopCarFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_car,container,false);
        return v;
    }

}
