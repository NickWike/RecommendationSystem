package com.example.zh123.recommendationsystem.myfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.order.OrderInfoActivity;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.ShopCarDataBean;
import com.example.zh123.recommendationsystem.entities.ShopCarInfoJson;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myadapters.ShopCarAdapter;
import com.example.zh123.recommendationsystem.utils.ComputingUtil;
import com.example.zh123.recommendationsystem.utils.RequestUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Response;

/**
 * Created by zh123 on 19-12-6.
 */

/**
 * 此fragment作为'购物车'选项对应的界面
 */
public class ShopCarFragment extends Fragment {

    RecyclerView mRecyclerView;
    ShopCarAdapter mShopCarAdapter;
    MyHandler mMyHandler;
    List<ShopCarDataBean.ShopCarItemsBean> mShopCarItems;

    enum ManagementOrFinished{MANAGEMENT, FINISHED}    // 前者代表显示管理按钮 后者代表完成按钮

    ManagementOrFinished mIdentity = ManagementOrFinished.MANAGEMENT;   // 初始化为管理按钮


    CheckBox mCheckAllButton;
    TextView mCloseAccountButton;
    RelativeLayout mCloseAccountArea;
    TextView mDeleteButton;
    TextView mManagementOrFinishedButton;
    TextView mTotalPrice;


    UserBean mUserBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mUserBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_car,container,false);

        initView(v);

        LinearLayoutManager linearLayout = new LinearLayoutManager(v.getContext());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        if(mMyHandler == null){
            mMyHandler = new MyHandler();
        }


        if(mShopCarAdapter == null){
            mShopCarItems = new ArrayList<>();
            mShopCarAdapter = new ShopCarAdapter(getContext(),mShopCarItems);
            mShopCarAdapter.setOnShopCarItemChangeListener(new ShopCarAdapter.OnShopCarItemChangeListener() {
                @Override
                public void changed(Message msg) {
                    Bundle bundle = msg.getData();
                    int position = bundle.getInt("Position");
                    HashMap<String,String> requestArgs = new HashMap<>();
                    Set<String> keySet = bundle.keySet();
                    for(String key:keySet){
                        Object obj = bundle.get(key);
                        if(obj instanceof String){
                            requestArgs.put(key,bundle.getString(key));
                        }
                    }
                    new ShopCarRequestTask(mMyHandler, msg.what, mUserBean.getSession(),requestArgs).start();
                }

                @Override
                public void changedCount(int count) {
                    mCheckAllButton.setChecked(count == mShopCarItems.size());
                }

                @Override
                public void totalPriceChanged() {
                    String totalPrice = "0.00";
                    for(ShopCarDataBean.ShopCarItemsBean itemsBean:mShopCarItems){
                        if(itemsBean.getChecked() == 1){
                            totalPrice = ComputingUtil.StringNumberAdd(totalPrice,itemsBean.getItem_price());
                        }
                    }
                    mTotalPrice.setText(String.format("￥%s",totalPrice));
                }
            });
        }

        mRecyclerView.setAdapter(mShopCarAdapter);
        mRecyclerView.setLayoutManager(linearLayout);
        initListener();
        return v;
    }

    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.id_shop_car_recycler);
        mCheckAllButton = v.findViewById(R.id.id_shop_car_checked_all);
        mCloseAccountArea = v.findViewById(R.id.close_account_area);
        mCloseAccountButton = v.findViewById(R.id.id_close_account);
        mDeleteButton = v.findViewById(R.id.id_delete_item);
        mManagementOrFinishedButton = v.findViewById(R.id.id_shop_car_management);
        mTotalPrice = v.findViewById(R.id.id_total_price);
    }

    private void initListener(){

        mCheckAllButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mShopCarItems.size() == 0){mCheckAllButton.setChecked(false);}
                if(!mCheckAllButton.isPressed()){return;}

                mShopCarAdapter.checkedAllItems(b?1:0);
            }
        });

        mManagementOrFinishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 身份反转
                mIdentity = (mIdentity == ManagementOrFinished.MANAGEMENT)? ManagementOrFinished.FINISHED:ManagementOrFinished.MANAGEMENT;

                switch (mIdentity){
                    case MANAGEMENT:
                        mManagementOrFinishedButton.setText("管理");
                        mDeleteButton.setVisibility(View.INVISIBLE);
                        mCloseAccountArea.setVisibility(View.VISIBLE);
                        break;
                    case FINISHED:
                        mDeleteButton.setVisibility(View.VISIBLE);
                        mCloseAccountArea.setVisibility(View.INVISIBLE);
                        mManagementOrFinishedButton.setText("完成");
                        break;
                }

            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShopCarAdapter.removeCheckedItems();
            }
        });

        mCloseAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrderInfoActivity();
            }
        });

    }

    private void startOrderInfoActivity(){
        if(mShopCarItems.size() != 0){
            Intent intent = new Intent(getContext(), OrderInfoActivity.class);
            intent.putExtra(UserBean.getClassName(),mUserBean);
            intent.putExtra("PageIdentity",PageIdentity.SHOP_CAR_FRAGMENT);
            startActivity(intent);
        }
    }

    public void updateShopCarItems(){
        new ShopCarRequestTask(mMyHandler, ShopCarRequestTask.TaskTag.GET_CAR_ITEMS, mUserBean.getSession()).start();
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == ShopCarRequestTask.TaskTag.REQUEST_ERROR){return;}

            Bundle bundle = msg.getData();
            ShopCarInfoJson shopCarInfoJson = (ShopCarInfoJson) bundle.getSerializable(ShopCarInfoJson.getClassName());

            switch (msg.what){
                case ShopCarRequestTask.TaskTag.GET_CAR_ITEMS:
                    ShopCarDataBean shopCarDataBean = shopCarInfoJson.getData();
                    mShopCarItems = shopCarDataBean.getItems();
                    mTotalPrice.setText(shopCarDataBean.getFormatTotalPrice());
                    boolean stat = true;
                    for(ShopCarDataBean.ShopCarItemsBean item:mShopCarItems){stat = (item.getChecked()==1)&&stat;}
                    mCheckAllButton.setChecked(stat&&mShopCarItems.size() !=0 );
                    mShopCarAdapter.update(mShopCarItems);
                    break;
                case ShopCarRequestTask.TaskTag.UPDATE_ITEMS_QUANTITY:
                    break;
                case ShopCarRequestTask.TaskTag.REMOVE_CAR_ITEMS:
                    break;
                case ShopCarRequestTask.TaskTag.CHECKED_CAR_ITEMS:
                    break;
            }
        }

    }

    public class ShopCarRequestTask extends Thread{
        private MyHandler myHandler;
        private int taskTag;
        private String session;
        private HashMap<String,String> requestArgs;

        ShopCarRequestTask(MyHandler h, int taskTag, String s){
            this(h,taskTag,s,null);
        }

        ShopCarRequestTask(MyHandler h, int taskTag, String s,HashMap<String,String> data){
            this.myHandler = h;
            this.taskTag = taskTag;
            this.session = s;
            this.requestArgs = data;
        }


        @Override
        public void run() {
            Response response = null;
            Message message = new Message();

            switch (this.taskTag){
                case TaskTag.GET_CAR_ITEMS:
                    response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/car/get_shop_car_items.action",this.session);
                    break;
                case TaskTag.UPDATE_ITEMS_QUANTITY:
                    response = RequestUtil.Post(MyServerConfig.SERVER_PATH + "/product/car/add_to_car.action",requestArgs,this.session);
                    break;
                case TaskTag.REMOVE_CAR_ITEMS:
                    response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/car/remove_items_to_car.action",requestArgs,this.session);
                    break;
                case TaskTag.CHECKED_CAR_ITEMS:
                    response = RequestUtil.Get(MyServerConfig.SERVER_PATH +"/product/car/checked_car_items.action",requestArgs,this.session);
                    break;
            }
            try {
                if(response != null && response.isSuccessful()){
                    Gson gson = new Gson();
                    Bundle bundle = new Bundle();
                    String bodyString = response.body().string();
                    ShopCarInfoJson shopCarInfoJson = gson.fromJson(bodyString,ShopCarInfoJson.class);
                    bundle.putSerializable(ShopCarInfoJson.getClassName(),shopCarInfoJson);
                    message.setData(bundle);
                    message.what = taskTag;
                }
            }catch (Exception e){
                e.printStackTrace();
                message.what = TaskTag.REQUEST_ERROR;
            }
            finally {
                this.myHandler.sendMessage(message);
            }

        }

        public class TaskTag{
            // 请求异常
            static final int REQUEST_ERROR = -0x01;
            // 获取购物车中的items
            static final int GET_CAR_ITEMS = 0x01;
            // 更新购物中商品的选择数量
            public static final int UPDATE_ITEMS_QUANTITY = 0x02;
            // 删除购物车中的items
            public static final int REMOVE_CAR_ITEMS = 0x03;
            // 购物车中item的选中情况发生变化
            public static final int CHECKED_CAR_ITEMS = 0x04;
            // 结算
            public static final int CLOSE_ACCOUNT = 0x05;
        }
    }

    @Override
    public void onDestroy() {
        mMyHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
