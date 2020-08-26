package com.example.zh123.recommendationsystem.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.OrderItemBean;

/**
 * Created by zh123 on 20-3-23.
 */

public class OrderCardView extends LinearLayout {

    // 用于监听 view 中状态改变时,外部注册对其进行相应处理
    private OnStateChangedListener mOnStateChangedListener;

    // 商品所属店铺名称
    private TextView mShopName;
    // 商品名称
    private TextView mProductName;
    // 商品发货地址
    private TextView mArea;
    // 商品单价
    private TextView mProductUnitPrice;
    // 商品选择数量
    private TextView mProductQuantity;
    // 商品运费
    private TextView mPostage;
    // 小计费用
    private TextView mSubtotalFee;
    // 配送方式
    private RadioGroup mPaymentType;
    // 备注
    private EditText mNote;
    // 商品图片
    private ImageView mProductImage;

    public OrderCardView(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.order_card, this,true);
        initView();
    }

    public OrderCardView(Context context, AttributeSet attrs) {
        super(context,attrs,0);

    }

    public OrderCardView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);

    }

    private void initView(){
        mShopName = (TextView) findViewById(R.id.id_shop_name);
        mProductName = (TextView) findViewById(R.id.id_product_name);
        mArea = (TextView) findViewById(R.id.id_product_area_name);
        mProductUnitPrice = (TextView) findViewById(R.id.id_product_unit_price);
        mProductQuantity = (TextView) findViewById(R.id.id_product_quantity);
        mPostage = (TextView) findViewById(R.id.id_postage);
        mSubtotalFee = (TextView) findViewById(R.id.id_subtotal_fee);
        mPaymentType = (RadioGroup) findViewById(R.id.id_payment_type);
        mNote = (EditText) findViewById(R.id.id_order_note);
        mProductImage = (ImageView) findViewById(R.id.id_product_image);
    }

    public void loadData(OrderItemBean item){
        mShopName.setText(item.getShop_name());
        mProductName.setText(item.getProduct_name());
        mArea.setText(item.getArea_name());
        mProductUnitPrice.setText('￥'+item.getUnit_price());
        mProductQuantity.setText(String.valueOf("×"+item.getQuantity()));
        mPostage.setText('￥'+item.getPostage());
        mSubtotalFee.setText('￥'+item.getSubtotal_fee());
        Glide.with(getContext()).load(item.getProduct_image()).into(mProductImage);
    }

    public interface OnStateChangedListener{
        void OnPaymentTypeChanged();
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener){
        this.mOnStateChangedListener = onStateChangedListener;
    }

    public String getNoteText(){
        return mNote.getText().toString();
    }

    public String getPymentType(){
        return findViewById(mPaymentType.getCheckedRadioButtonId()).getTag().toString();
    }
}
