package com.example.zh123.recommendationsystem.myadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.beans.ShopBean;

import java.util.List;

/**
 * Created by zh123 on 20-1-12.
 */
// 主页猜您喜欢页面项的开片滑动适配器
public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.MyHolder>{
    // 要进行显示的商品列表
    private List<ShopBean> shopList;
    // 上下文对象
    private Context mContext;

    public HomeCardAdapter(Context context,List<ShopBean> list){
        this.shopList = list;
        this.mContext = context;
    }

    // 此方法用于更新操作在外部调用 可以根据传入的商品列表更新显示
    public void update(List<ShopBean> list){
        shopList = list;
        // 使得试图更新生效
        notifyDataSetChanged();
    }

    // 方法重写为每个Item绑定布局文件
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_item,parent,false);
        return new MyHolder(v);
    }
    // 方法重写此方法用作对每个item的view进行操作(事件绑定,设置组件内容等等)
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ShopBean shopBean = shopList.get(position);
        holder.shopPrice.setText(shopBean.getShopPriceString());
        holder.shopTitle.setText(shopBean.getShopTitle());
        // 加载商品图片

        // 根据商品好评率点亮星星
        switch (shopBean.getStartLevel()){
            case 5:{holder.shopStar5.setImageResource(R.mipmap.icon_star_yes);}
            case 4:{holder.shopStar4.setImageResource(R.mipmap.icon_star_yes);}
            case 3:{holder.shopStar3.setImageResource(R.mipmap.icon_star_yes);}
            case 2:{holder.shopStar2.setImageResource(R.mipmap.icon_star_yes);}
            case 1:{holder.shopStar1.setImageResource(R.mipmap.icon_star_yes);}
        }

    }

    // 方法重写获取Item的个数
    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView shopImage;
        TextView shopTitle;
        TextView shopPrice;
        ImageView shopStar1;
        ImageView shopStar2;
        ImageView shopStar3;
        ImageView shopStar4;
        ImageView shopStar5;

        public MyHolder(View itemView) {
            super(itemView);
            shopImage = itemView.findViewById(R.id.home_card_image);
            shopTitle = itemView.findViewById(R.id.home_card_title);
            shopPrice = itemView.findViewById(R.id.home_card_price);
            shopStar1 = itemView.findViewById(R.id.home_card_star_1);
            shopStar2 = itemView.findViewById(R.id.home_card_star_2);
            shopStar3 = itemView.findViewById(R.id.home_card_star_3);
            shopStar4 = itemView.findViewById(R.id.home_card_star_4);
            shopStar5 = itemView.findViewById(R.id.home_card_star_5);
        }

    }
}
