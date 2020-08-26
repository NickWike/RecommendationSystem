package com.example.zh123.recommendationsystem.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.R;

/**
 * Created by zh123 on 20-3-27.
 */

public class PaymentWindow extends PopupWindow{

    private View mContentView;
    private OnWindowClickListener mOnWindowClickListener;
    private Context mContext;
    private ImageView mCancel;
    private TextView mPayment;

    public PaymentWindow(Context context){
        this(LayoutInflater.from(context).inflate(R.layout.payment_window,null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mContext = context;
    }

    private PaymentWindow(View contentView, int width, int height){
        super(contentView,width,height);
        this.mContentView =contentView;
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
        this.setOutsideTouchable(false);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.payment_window_anim_style);
        this.mCancel = (ImageView) mContentView.findViewById(R.id.id_cancel);
        this.mPayment = (TextView) mContentView.findViewById(R.id.id_payment_price);
    }

    public void show(String payment){
        mPayment.setText(payment);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentWindow.this.dismiss();
            }
        });
        this.showAtLocation(this.mContentView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        showSureBack();
    }

    private void superDismiss(){
        super.dismiss();
    }

    private void initListener() {

    }

    public void setOnWindowClickListener(OnWindowClickListener onWindowClickListener){
        this.mOnWindowClickListener = onWindowClickListener;
    }

    public interface OnWindowClickListener{
        void onSureButtonClick();
    }

    private void showSureBack(){
        AlertDialog alertDialog =  new AlertDialog.Builder(mContext).setTitle("放弃支付").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                superDismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

}
