package com.example.zh123.recommendationsystem.myfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.product.DetailProductActivity;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.ProductBean;
import com.example.zh123.recommendationsystem.entities.ProductJson;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myadapters.HomeCardAdapter;
import com.example.zh123.recommendationsystem.network.response.ServerResultStatus;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zh123 on 19-12-6.
 */

/**
 * 此fragment作为'主页'选项对应的界面
 */
public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HomeCardAdapter mAdapter;
    private List<ProductBean> mProductItems = new ArrayList<ProductBean>();
    private MyHandler mMyHandler;
    private UserBean mUserBean;
    private ImageView mRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyHandler = new MyHandler();
        mAdapter = new HomeCardAdapter(getContext(), mProductItems);
        mUserBean = (UserBean) getArguments().getSerializable(UserBean.getClassName());
        new GetMainProduct(mMyHandler, mUserBean.getSession(),null).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        initView(v);
        initListener();
        return v;
    }


    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.recycler_home);
        mRefresh = v.findViewById(R.id.id_refresh);
        if (mAdapter != null){
            mAdapter = new HomeCardAdapter(getContext(), mProductItems);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetMainProduct(mMyHandler, mUserBean.getSession(),null).start();
            }
        });

        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // 设置主页当中滑动组件的 非中心item点击时 将此item跳转到中心 和 中心item被点击时触发的事件
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
                final int p = recyclerView.getChildAdapterPosition(v);
                Intent intent = new Intent(getContext(), DetailProductActivity.class);
                intent.putExtra(UserBean.getClassName(), mUserBean);
                intent.putExtra(ProductBean.getClassName(),mProductItems.get(p));
                startActivity(intent);
            }
        },mRecyclerView,layoutManager);

    }


    class GetMainProduct extends Thread{
        String session;
        MyHandler handler;
        HashMap<String,String> requestArgs;

        GetMainProduct(MyHandler h,String s,HashMap<String,String> args){
            this.session = s;
            this.handler = h;
            this.requestArgs = args;
        }

        @Override
        public void run() {
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = -1;
            try{
                Response response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/get_main_items.action",
                        session);
                if(response.isSuccessful()) {
                    String bodyString = response.body().string();
                    Gson gson = new Gson();
                    ProductJson productJson = gson.fromJson(bodyString, ProductJson.class);
                    if (productJson != null && productJson.getStatus_code() == ServerResultStatus.OK) {
                        bundle.putSerializable(ProductJson.getClassName(), productJson);
                        message.what = 1;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }

        class TaskType{
            static final int REQUEST_ERROR = -0x01;
            static final int GET_HOME_ITEMS = 0x01;
            static final int GET_ITEMS_DETAIL = 0x02;
        }

    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case -1:
                    Toast.makeText(getContext(),"获取主页信息失败",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    ProductJson productJson = (ProductJson) bundle.getSerializable(ProductJson.getClassName());
                    mProductItems.clear();
                    mProductItems = productJson.getItems();
                    mAdapter.update(mProductItems);
                    mRecyclerView.scrollToPosition(mProductItems.size()/2);
            }
        }
    }
}
