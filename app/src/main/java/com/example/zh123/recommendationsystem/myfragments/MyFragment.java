package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.MainActivity;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.UserBean;

/**
 * Created by zh123 on 19-12-6.
 */

/**
 * 此fragment作为'我的'选项对应的界面
 */
public class MyFragment extends Fragment{
    private TextView mUserNameView;
    private TextView mUserIdView;

    UserBean mUserBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserBean = ((MainActivity)getActivity()).mUserBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my,container,false);
        mUserNameView = v.findViewById(R.id.id_my_user_name);
        mUserIdView = v.findViewById(R.id.id_my_user_id);
        mUserNameView.setText(mUserBean.getUser_name());
        mUserIdView.setText(mUserBean.getUser_id());
        return v;
    }
}
