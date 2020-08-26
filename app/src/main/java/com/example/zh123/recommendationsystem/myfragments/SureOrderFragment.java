package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.OrderInfoJson;
import com.example.zh123.recommendationsystem.entities.OrderItemBean;
import com.example.zh123.recommendationsystem.entities.ProductBean;
import com.example.zh123.recommendationsystem.entities.ShippingInfoBean;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.network.request.RequestTag;
import com.example.zh123.recommendationsystem.network.request.task.OrderTask;
import com.example.zh123.recommendationsystem.network.response.ServerResultStatus;
import com.example.zh123.recommendationsystem.views.OrderCardView;
import com.example.zh123.recommendationsystem.views.PaymentWindow;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zh123 on 20-3-26.
 */

public class SureOrderFragment extends Fragment {

    private ShippingInfoBean mShippingInfoBean;

    private RelativeLayout mAddressBar;
    private TextView mBarAddressText;
    private ImageView mBack;

    private TextView mTotalPrice;
    private TextView mProductCount;
    private TextView mAddress;
    private TextView mMobile;
    private TextView mReceiverName;
    private TextView mCommitOrder;

    private RelativeLayout mMainShippingCard;

    private ScrollView mScrollView;
    private LinearLayout mOrderItemsView;
    private List<OrderItemBean> mOrderItemBeans=new ArrayList<>();
    private List<OrderCardView> mOrderCardViews=new ArrayList<>();

    private PaymentWindow mPaymentWindow;

    private MyHandler mMyHandler;
    private UserBean mUserBean;
    private String mToken;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sure_order,container,false);
        mMyHandler = new MyHandler();

        initView(v);
        initData();
        initListener();
        return v;
    }

    private void initData(){
        Bundle bundle = getArguments();
        int pageIdentity = -1;
        ProductBean productBean = null;
        if(bundle != null){
            mUserBean = (UserBean) bundle.getSerializable(UserBean.getClassName());
            productBean = (ProductBean) bundle.getSerializable(ProductBean.getClassName());
            pageIdentity = bundle.getInt("PageIdentity");
        }
        if(mUserBean != null){
            if(pageIdentity == PageIdentity.SHOP_CAR_FRAGMENT){
                new OrderTask(mUserBean.getSession(),null,mMyHandler,RequestTag.GET_ORDER_INFO_TO_SHOP_CAR).start();
            }
            else if(pageIdentity == PageIdentity.PRODUCT_DETAIL_FRAGMENT && productBean != null){
                HashMap<String,String> data = new HashMap<>();
                data.put("product_id",String.valueOf(productBean.getProduct_id()));
                data.put("quantity", "1");
                new OrderTask(mUserBean.getSession(),data,mMyHandler,RequestTag.GET_ORDER_INFO_TO_SHOP_CAR).start();
            }
        }

    }

    private void initView(View v){
        mOrderItemsView = v.findViewById(R.id.id_order_info_linear);
        mScrollView = v.findViewById(R.id.id_scrollview);
        mAddressBar = v.findViewById(R.id.id_address_info_bar);
        mBarAddressText = v.findViewById(R.id.id_receiver_address);
        mAddress = v.findViewById(R.id.id_receiver_address2);
        mReceiverName = v.findViewById(R.id.id_receiver_name);
        mMobile = v.findViewById(R.id.id_receiver_mobile);
        mTotalPrice = v.findViewById(R.id.id_total_price);
        mProductCount = v.findViewById(R.id.id_total_count);
        mBack = v.findViewById(R.id.id_back);
        mMainShippingCard = v.findViewById(R.id.id_main_shipping_card);
        mCommitOrder = v.findViewById(R.id.id_commit_order);

        mPaymentWindow = new PaymentWindow(this.getContext());
    }

    private void initListener(){
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int nowX, int nowY, int oldX, int oldY) {
                if(nowY < mMainShippingCard.getHeight()){
                    mAddressBar.setVisibility(View.INVISIBLE);
                }else{
                    mAddressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mCommitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitOrder();
            }
        });

        mPaymentWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getActivity().finish();
            }
        });
    }

    private void loadData(){
        mBarAddressText.setText(mShippingInfoBean.getMergedAddressInfo());
        mAddress.setText(mShippingInfoBean.getMergedAddressInfo());
        mMobile.setText(mShippingInfoBean.getReceiver_mobile());
        mReceiverName.setText(mShippingInfoBean.getReceiver_name());
        mProductCount.setText(String.valueOf("共"+mOrderItemBeans.size()+"件"));

        for(OrderItemBean item : mOrderItemBeans){
            OrderCardView orderCardView = new OrderCardView(getContext());
            mOrderCardViews.add(orderCardView);
            mOrderItemsView.addView(orderCardView);
            orderCardView.loadData(item);
        }
    }

    private void commitOrder(){
        HashMap<String,String> paymentType = new HashMap<>();
        for(int i=0;i<mOrderCardViews.size();i++){
            paymentType.put(String.valueOf(mOrderItemBeans.get(i).getProduct_id()),mOrderCardViews.get(i).getPymentType());
        }
        HashMap<String,String> requestData = new HashMap<>();
        requestData.put("payment_type",new Gson().toJson(paymentType));
        requestData.put("token",mToken);
        new OrderTask(mUserBean.getSession(),requestData,mMyHandler,RequestTag.COMMIT_ORDER).start();
    }

    @Override
    public void onDestroy() {
        mMyHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            OrderInfoJson orderInfoJson;

            switch (msg.what){
                case RequestTag.ERROR:
                    break;
                case RequestTag.GET_ORDER_INFO_TO_SHOP_CAR:
                case RequestTag.GET_ORDER_INFO_TO_BUY_BOW:
                    orderInfoJson = (OrderInfoJson) bundle.getSerializable(OrderInfoJson.getClassName());
                    if (orderInfoJson !=null && orderInfoJson.getStatus_code() == ServerResultStatus.OK){
                        mOrderItemBeans = orderInfoJson.getData().getItems();
                        mShippingInfoBean = orderInfoJson.getData().getShipping_info();
                        mTotalPrice.setText(orderInfoJson.getData().getTotal_price());
                        mToken = orderInfoJson.getData().getToken();
                        loadData();
                    }
                    break;
                case RequestTag.COMMIT_ORDER:
                    orderInfoJson = (OrderInfoJson) bundle.getSerializable(OrderInfoJson.getClassName());
                    if(orderInfoJson != null && orderInfoJson.getStatus_code() == ServerResultStatus.OK){
                        mPaymentWindow.show(orderInfoJson.getData().getPayment());
                    }
                    break;
            }
        }
    }

}
