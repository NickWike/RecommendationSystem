package com.example.zh123.recommendationsystem.activities.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.ProductBean;
import com.example.zh123.recommendationsystem.entities.ProductJson;
import com.example.zh123.recommendationsystem.entities.RequestBaseJson;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myfragments.ProductDetailFragment;
import com.example.zh123.recommendationsystem.network.response.ServerResultStatus;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by zh123 on 20-3-18.
 */

public class DetailProductActivity extends AppCompatActivity implements ProductDetailFragment.sendMessageToActivity{


    private RelativeLayout mTopBar;
    private RelativeLayout mBottomBar;
    private TextView mBuyNow;
    private TextView mAddToShopCar;

    private TextView mShopName;
    private ProductBean mProductBean;
    private UserBean mUserBean;

    private boolean isBottomBarHide=false;

    private MyHandler mMyHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        mProductBean = (ProductBean) intent.getSerializableExtra(ProductBean.getClassName());
        mUserBean = (UserBean) intent.getSerializableExtra(UserBean.getClassName());
        initView();
        initListener();
    }

    private void loadFragment(){
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable(ProductBean.getClassName(),mProductBean);
        productDetailFragment.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.id_detail_fragment_container,productDetailFragment);
        ft.commit();
    }

    private void initView(){
        mTopBar = findViewById(R.id.id_detail_top_bar);
        mBottomBar = findViewById(R.id.id_detail_bottom_bar);
        mShopName = findViewById(R.id.id_shop_name);
        mBuyNow = findViewById(R.id.id_buy_now);
        mAddToShopCar = findViewById(R.id.id_add_to_car);

        // 初始化MyHandler 用于接收和处理requests请求后返回的数据
        mMyHandler  = new MyHandler();

        // 设置Activity的沉浸模式也就是布局的顶部消除状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 向服务器发送 获取商品详细信息的请求
        // -*/----start----/*-
        HashMap<String,String> requestData = new HashMap<>();
        requestData.put("items",String.valueOf(mProductBean.getProduct_id()));
        new RequestAction(mUserBean.getSession(),requestData,mMyHandler, RequestAction.TaskTag.GET_DETAIL_INFO).start();
        // -*/----end----/*-

    }

    private void initListener(){
        mAddToShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> requestData = new HashMap<>();
                requestData.put("product_id",String.valueOf(mProductBean.getProduct_id()));
                requestData.put("checked","1");
                requestData.put("quantity","1");
                new RequestAction(mUserBean.getSession(),requestData,mMyHandler, RequestAction.TaskTag.ADD_TO_SHOP_CAR).start();
            }
        });
    }

    @Override
    public void sendMessage(Message msg) {

    }

    public void hideTopBar(int offsetY){
        float alpha = (offsetY+ 0f) / (mTopBar.getHeight()+80f);
        alpha = alpha <= 1? alpha:1;
        mTopBar.setAlpha(alpha);
    }

    public void hideBottomBar(boolean isHide){
        if(isHide && !isBottomBarHide){
            mBottomBar.animate().setDuration(500).translationY(mBottomBar.getHeight());
        }else if(!isHide && isBottomBarHide){
            mBottomBar.animate().setDuration(500).translationY(0);
        }
        this.isBottomBarHide = isHide;
    }

    class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            String bodyString = bundle.getString("BodyString");
            Gson gson = new Gson();
            switch (msg.what){
                case -1:
                    break;
                case RequestAction.TaskTag.GET_DETAIL_INFO:
                    ProductJson productJson = gson.fromJson(bodyString,ProductJson.class);
                    mProductBean = productJson.getItems().get(0);
                    mShopName.setText(mProductBean.getShop_name());
                    loadFragment();
                    break;
                case RequestAction.TaskTag.ADD_TO_SHOP_CAR:
                    RequestBaseJson requestBaseJson = gson.fromJson(bodyString,RequestBaseJson.class);
                    if(requestBaseJson.getStatus_code() == ServerResultStatus.OK){
                        Toast toast = Toast.makeText(getApplicationContext(),"加入购车成功",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
            }
        }
    }

    class RequestAction extends Thread{
        private String session;
        private MyHandler myHandler;
        private int taskType;
        private HashMap<String,String> requestData;

        RequestAction(String s, HashMap<String,String> rd, MyHandler h, int type){
            this.taskType = type;
            this.requestData=rd;
            this.session = s;
            this.myHandler = h;
        }

        class TaskTag{
            final static int REQUEST_ERROR = -0x01;
            final static int GET_DETAIL_INFO = 0x01;
            final static int ADD_TO_SHOP_CAR = 0x02;
            final static int COLLECTION = 0x03;
            final static int BUY_NOW = 0x04;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = -1;
            Response response=null;

            switch (taskType){
                case TaskTag.GET_DETAIL_INFO:
                    response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/detail_info.action",this.requestData,this.session);
                    break;
                case TaskTag.ADD_TO_SHOP_CAR:
                    response = RequestUtil.Post(MyServerConfig.SERVER_PATH + "/product/car/add_to_car.action",this.requestData,this.session);
                    break;
                case TaskTag.COLLECTION:
                    break;
                case TaskTag.BUY_NOW:
                    break;
            }
            try{
                if(response !=null && response.isSuccessful()){
                    String bodyString = response.body().string();
                    Bundle bundle = new Bundle();
                    bundle.putString("BodyString",bodyString);
                    message.setData(bundle);
                }

            }catch (Exception e){
                message.what = TaskTag.REQUEST_ERROR;
            }finally {
                message.what = taskType;
                myHandler.sendMessage(message);
            }
        }
    }
}
