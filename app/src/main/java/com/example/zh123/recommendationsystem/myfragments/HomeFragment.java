package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.beans.ShopBean;
import com.example.zh123.recommendationsystem.myadapters.HomeCardAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh123 on 19-12-6.
 */

/**
 * 此fragment作为'主页'选项对应的界面
 */
public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<ShopBean> shopList = new ArrayList<ShopBean>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        mRecyclerView = v.findViewById(R.id.recycler_home);

        shopList.add(new ShopBean("shop1",new BigDecimal(3.00)));
        shopList.add(new ShopBean("shop2",new BigDecimal(5.00)));
        shopList.add(new ShopBean("shop3",new BigDecimal(6.00)));
        shopList.add(new ShopBean("shop4",new BigDecimal(8.00)));
        shopList.add(new ShopBean("shop5",new BigDecimal(7.00)));
        shopList.add(new ShopBean("shop6",new BigDecimal(2.00)));
        mAdapter = new HomeCardAdapter(getContext(),shopList);
        mRecyclerView.setAdapter(mAdapter);
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        return v;
    }

}
