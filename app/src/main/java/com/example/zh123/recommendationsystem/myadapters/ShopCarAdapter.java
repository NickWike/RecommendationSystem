package com.example.zh123.recommendationsystem.myadapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.entities.ShopCarDataBean.*;
import com.example.zh123.recommendationsystem.myfragments.ShopCarFragment;
import com.example.zh123.recommendationsystem.utils.ComputingUtil;

import java.util.List;

/**
 * Created by zh123 on 20-1-17.
 */

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.ShopCarHolder> {

    private Context mContext;
    private List<ShopCarItemsBean> mShopCarItems;
    private OnShopCarItemChangeListener mOnShopCarItemChangeListener;

    public ShopCarAdapter(Context context,List<ShopCarItemsBean> list){
        // 接收传过来的商品列表
        mShopCarItems = list;
        // 设置上下文对象
        mContext = context;
    }

    // 此方法用于更新操作在外部调用 可以根据传入的商品列表更新显示
    public void update(List<ShopCarItemsBean> list){
        mShopCarItems = list;
        // 使得视图更新生效
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ShopCarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 设置Item对应的布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_car_item,parent,false);
        return new ShopCarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarHolder holder,int position) {
        ShopCarItemsBean shopCarItemsBean = mShopCarItems.get(position);
        // 设置商品的描述信息
        holder.goodsDesc.setText(shopCarItemsBean.getProduct_name());
        // 设置商品所属商店的名称
        holder.shopName.setText(shopCarItemsBean.getShop_name());
        // 设置商品的价格
        holder.goodsPrice.setText(shopCarItemsBean.getItem_price());
        // 设置商品被选择的数量
        holder.goodsCount.setText(String.valueOf(shopCarItemsBean.getQuantity()));
        // 初始化判定当前的商品是否被选中
        holder.shopChoose.setChecked(shopCarItemsBean.getChecked() == 1);

        // 设置当前Holder对应的索引值 并在holder内部进行设置相应的监听器
        Glide.with(mContext).load(shopCarItemsBean.getProduct_image()).into(holder.goodsImage);

        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        // 返回滑动列表中Item的个数 也就是商品列表的大小
        return mShopCarItems.size();
    }

//    private void initTestData(){
//        for(int i=0;i<100;i++){
//            ProductEntity productEntity = new ProductEntity("goods "+i);
//            productEntity.setChooseCount(1);
//            productEntity.setShopName("shop " + i);
//            productEntity.setGoodsPrice(new BigDecimal(i));
//            mShopList.add(productEntity);
//        }
//    }


    class ShopCarHolder extends RecyclerView.ViewHolder{
        CheckBox shopChoose;
        ImageView shopLogo;
        ImageView goodsImage;
        TextView shopName;
        TextView goodsDesc;
        TextView goodsPrice;
        TextView goodsCount;
        Button goodsAddBtn;
        Button goodsSubBtn;
        int position;
        boolean listenerIsSet = false;

        public ShopCarHolder(View itemView) {
            super(itemView);
            // 查找商品的选择按钮(CheckBox)对象
            shopChoose   = itemView.findViewById(R.id.id_shop_car_choose);
            // 查找商店的logo展示视图(ImageView)对象
            shopLogo     = itemView.findViewById(R.id.id_shop_logo);
            // 查找商品的图片展示视图(ImageView)对象
            goodsImage   = itemView.findViewById(R.id.id_shop_car_goods_im);
            // 查找商店名称文本信息的显示视图(TextView)对象
            shopName     =  itemView.findViewById(R.id.id_shop_name);
            // 查找商品的描述文本视图(TextView)对象
            goodsDesc    = itemView.findViewById(R.id.id_shop_car_goods_desc);
            // 查找商品价格显示的文本视图(TextView)对象
            goodsPrice   = itemView.findViewById(R.id.id_shop_car_goods_price);
            // 查找商品被加入购物车的数量的显示视图(TextView)对象
            goodsCount   = itemView.findViewById(R.id.id_shop_car_goods_num);
            // 查找增加商品被选择数量的按钮
            goodsAddBtn  = itemView.findViewById(R.id.id_shop_car_goods_add);
            // 查找减少商品被选择数量的按钮
            goodsSubBtn = itemView.findViewById(R.id.id_shop_car_goods_redu);
        }

        public void setPosition(int p){
            position = p;
            if(!listenerIsSet){
                setShopAddButtonListener();
                setShopChooseListener();
                setShopSubtractButtonListener();
                listenerIsSet = true;
            }
        }

        private void setShopAddButtonListener(){
            // 添加购物车内商品选择数量增加按钮的监听事件
            goodsAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 获取对应的shopBean
                    ShopCarItemsBean shopCarItemsBean = mShopCarItems.get(position);
                    // 获取改变后的值 在原来值的基础上加一
                    int changedValue = shopCarItemsBean.getQuantity() + 1;
                    if (changedValue > 99){
                        Toast.makeText(mContext,"不能再增加了",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 更新shopBean对应的值
                    shopCarItemsBean.setQuantity(changedValue);
                    // 更新视图上显示的值
                    goodsCount.setText(String.valueOf(shopCarItemsBean.getQuantity()));
                    // 改变当前item的价格
                    shopCarItemsBean.setItem_price(ComputingUtil.StringNumberMultiply(shopCarItemsBean.getUnit_price(),changedValue));
                    goodsPrice.setText(shopCarItemsBean.getItem_price());
                    updateItemsQuantityAction(shopCarItemsBean.getProduct_id(), "1", String.valueOf(shopCarItemsBean.getChecked()));
                }
            });
        }

        private void setShopSubtractButtonListener(){
            // 添加购物车内商品选择数量减少按钮的监听事件
            goodsSubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 获取到当前坐标对应的shopBean
                    ShopCarItemsBean shopCarItemsBean = mShopCarItems.get(position);
                    // 商品数量改变后的值
                    int changedValue = shopCarItemsBean.getQuantity() - 1;
                    // 为商品选择的数量重新设置值 关于改变值的约束问题会在ShopBean内部进行判断
                    if (changedValue<=0){
                        // 当ChangeValue 小于最小值时进行提示
                        Toast.makeText(mContext,"不能再减少了",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    shopCarItemsBean.setQuantity(changedValue);
                    // 更新前端视图展示的值
                    goodsCount.setText(String.valueOf(shopCarItemsBean.getQuantity()));
                    // 改变当前item的价格
                    shopCarItemsBean.setItem_price(ComputingUtil.StringNumberMultiply(shopCarItemsBean.getUnit_price(),changedValue));
                    goodsPrice.setText(shopCarItemsBean.getItem_price());
                    updateItemsQuantityAction(shopCarItemsBean.getProduct_id(), "-1", String.valueOf(shopCarItemsBean.getChecked()));
                }
            });
        }

        private void setShopChooseListener(){
            // 商品的选中情况--------------------------------------------
            // 先将以前设置的监听器清空
            shopChoose.setOnCheckedChangeListener(null);
            // 为每个选择按钮添加一个监听器
            shopChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!shopChoose.isPressed()){return;}
                    mShopCarItems.get(position).setChecked(b?1:0);
                    checkedAction(b?"1":"0",mShopCarItems.get(position).getProduct_id());
                    int mChooseCount = 0;
                    for(ShopCarItemsBean item : mShopCarItems){mChooseCount += item.getChecked();}
                    mOnShopCarItemChangeListener.changedCount(mChooseCount);
                }
            });
            // ---------------------------------------------------------
        }
    }

    private void updateItemsQuantityAction(String product_id,String quantity,String checked){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("product_id",product_id);
        bundle.putString("checked",checked);
        bundle.putString("quantity",quantity);
        message.what = ShopCarFragment.ShopCarRequestTask.TaskTag.UPDATE_ITEMS_QUANTITY;
        message.setData(bundle);
        mOnShopCarItemChangeListener.changed(message);
        mOnShopCarItemChangeListener.totalPriceChanged();
    }

    private void checkedAction(String checked,String items){
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = ShopCarFragment.ShopCarRequestTask.TaskTag.CHECKED_CAR_ITEMS;
        bundle.putString("checked",checked);
        bundle.putString("items",items);
        message.setData(bundle);
        mOnShopCarItemChangeListener.changed(message);
        mOnShopCarItemChangeListener.totalPriceChanged();
    }

    public void checkedAllItems(int checked){
        if(mShopCarItems.size() == 0){return;}
        StringBuilder commit_items = new StringBuilder();
        for(ShopCarItemsBean item : mShopCarItems){
            item.setChecked(checked);
            commit_items.append(item.getProduct_id()).append(";");
        }
        String items = commit_items.deleteCharAt(commit_items.length()-1).toString();
        checkedAction(checked==1?"1":"0", items);
        notifyDataSetChanged();
    }

    public void removeCheckedItems(){
        if (mShopCarItems.size() == 0){return;}
        StringBuilder commit_items = new StringBuilder();
        for(int i=mShopCarItems.size()-1;i>=0;i--){
            ShopCarItemsBean item = mShopCarItems.get(i);
            if(item.getChecked() == 1){
                mShopCarItems.remove(i);
                commit_items.append(item.getProduct_id()).append(";");
            }

        }
        String items = commit_items.deleteCharAt(commit_items.length()-1).toString();
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = ShopCarFragment.ShopCarRequestTask.TaskTag.REMOVE_CAR_ITEMS;
        bundle.putString("items",items);
        message.setData(bundle);
        mOnShopCarItemChangeListener.changed(message);
        mOnShopCarItemChangeListener.totalPriceChanged();
        notifyDataSetChanged();
    }

    // 用于外部来实现并设置监听器
    public void setOnShopCarItemChangeListener(OnShopCarItemChangeListener listener){
        this.mOnShopCarItemChangeListener = listener;
    }

    // 监听购车中的item的变化
    public interface OnShopCarItemChangeListener{

        /*
        * 购物车中的items的信息发生改变 例如 选中数量,选中状态,移除等
        * 传入的 Message 对象
        * msg.what 的值用来表示请求的类型
        * msg.setData() 中bundle的所有 String 类型的值为Request中的参数
        * */
        void changed(Message msg);

        /*
        * 选中的数量发生变化
        * 用于决定外部bottomBar 中的全选按钮是否为选中状态
        * 传入的count 为当前被选中item的数量
        * */
        void changedCount(int count);

        /*
        * 当选中item的总价的价格会发生改变时进行触发
        * */
        void totalPriceChanged();
    }
}
