package com.example.zh123.recommendationsystem.myfragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.product.DetailProductActivity;
import com.example.zh123.recommendationsystem.entities.ProductBean;

/**
 * Created by zh123 on 20-3-18.
 */

public class ProductDetailFragment extends Fragment {

    ScrollView mScrollView;
    TextView mProductPrice;
    TextView mProductName;
    TextView mProductAreaName;
    TextView mPostage;
    TextView mMonthSales;
    TextView mGoodCount;
    TextView mGeneralCount;
    TextView mPoorCount;
    ImageView mProductImage;

    ProductBean mProductBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        mProductBean = (ProductBean) b.getSerializable(ProductBean.getClassName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail,container,false);
        initView(v);
        loadViewContent();
        initListener();
        return v;
    }

    private void initView(View v){
        mScrollView = v.findViewById(R.id.id_scrollview);
        mProductPrice = v.findViewById(R.id.id_product_price);
        mProductName = v.findViewById(R.id.id_product_name);
        mProductAreaName = v.findViewById(R.id.id_product_area_name);
        mPostage = v.findViewById(R.id.id_postage);
        mMonthSales = v.findViewById(R.id.id_month_sales);
        mGoodCount = v.findViewById(R.id.id_good_count);
        mGeneralCount = v.findViewById(R.id.id_general_count);
        mPoorCount = v.findViewById(R.id.id_poor_count);
        mProductImage = v.findViewById(R.id.id_product_image);
    }

    private void loadViewContent(){
        mProductImage.setMaxHeight(mProductImage.getWidth());
        Glide.with(this).load(mProductBean.getProduct_image()).into(mProductImage);
        mProductPrice.setText(mProductBean.getPrice());
        mProductName.setText(mProductBean.getProduct_name());
        mProductAreaName.setText(mProductBean.getArea_name());
        mPostage.setText(mProductBean.getPostage());
        mMonthSales.setText(String.valueOf(mProductBean.getMonth_sales()));
        String strGoodCount = "好评("+mProductBean.getGood_count()+")";
        String strGeneralCount = "中评("+mProductBean.getGeneral_count()+")";
        String strPoorlCount = "差评("+mProductBean.getPoor_count()+")";
        mGoodCount.setText(strGoodCount);
        mGeneralCount.setText(strGeneralCount);
        mPoorCount.setText(strPoorlCount);
    }

    private void initListener(){
        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int nowX, int nowY, int oldX, int oldY) {
                DetailProductActivity activity = (DetailProductActivity) getActivity();
                int changedY = oldY - nowY;
                if(changedY < 0){
                    activity.hideBottomBar(true);
                }else if(changedY > 0){
                    activity.hideBottomBar(false);
                }
                activity.hideTopBar(nowY);
            }
        });
    }

    public interface sendMessageToActivity{
        public void sendMessage(Message msg);
    }
}
