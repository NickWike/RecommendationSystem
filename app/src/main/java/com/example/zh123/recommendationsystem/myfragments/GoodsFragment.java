package com.example.zh123.recommendationsystem.myfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh123.recommendationsystem.MainActivity;
import com.example.zh123.recommendationsystem.R;
import com.example.zh123.recommendationsystem.activities.product.DetailProductActivity;
import com.example.zh123.recommendationsystem.conf.MyServerConfig;
import com.example.zh123.recommendationsystem.entities.KeyWordJson;
import com.example.zh123.recommendationsystem.entities.ProductBean;
import com.example.zh123.recommendationsystem.entities.ProductJson;
import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myadapters.HomeCardAdapter;
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
 * 此fragment作为'商品'选项对应的界面
 */

public class GoodsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ImageView mSearchIm;
    EditText mSearchEdit;
    boolean mSearchImIsRight=false;
    MyHandler mMyHandler;
    UserBean mUserBean;
    KeyWordJson.KeywordBean mKeywordBean;
    HomeCardAdapter mHomeCardAdapter;

    Button mOrderByPriceBtn;
    Button mOrderByScoreBtn;
    Button mOrderBySalesBtn;

    List<ProductBean> mList=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyHandler = new MyHandler();
        mUserBean = (UserBean) getArguments().getSerializable(UserBean.getClassName());
        new GetHotKeyword(mMyHandler, mUserBean.getSession()).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goods,container,false);
        initView(v);
        initListener();
        return v;
    }

    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.goods_recycler);

        if(mHomeCardAdapter == null){
            mHomeCardAdapter= new HomeCardAdapter(getContext(),mList);
        }
        mSearchImIsRight = false;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2);
        mRecyclerView.setAdapter(mHomeCardAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mSearchEdit = v.findViewById(R.id.id_goods_search_edit);
        mSearchIm = v.findViewById(R.id.id_goods_search_im);
        mOrderByPriceBtn = v.findViewById(R.id.id_goods_order_by_price);
        mOrderByScoreBtn = v.findViewById(R.id.id_goods_order_by_score);
        mOrderBySalesBtn = v.findViewById(R.id.id_goods_order_by_sales);
    }

    private void initListener(){
        mSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && !mSearchImIsRight){
                    // 当焦点在输入框且放大镜图标不在右边时 将其动画效果设置为从左往右平移
                    searchBtttonTranslation(true);

                }
                else if(!b && mSearchEdit.getText().length()==0){
                    // 如果当前的输入框内没有文本时 将动画置为从右至左进行平移
                    searchBtttonTranslation(false);
                }
                else {
                    // 否则直接退出什么也不干
                    return;
                }
            }
        });

        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // 监听会回车事件 当用户输入回车按钮时进行搜索操作
                if ((keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode() && KeyEvent.ACTION_DOWN == keyEvent.getAction())) {
//                        startSearch();
//                        return true;
                }
                return false;
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private final int minCount = 4;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int totalCount = gridLayoutManager.getItemCount();
                int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                if(!mRecyclerView.canScrollVertically(1)){
                    this.loadMoreProduct();
                }
                else if(totalCount > minCount && lastPosition == totalCount - minCount){
                    if (mKeywordBean.isEndPage()){return;}
                    this.loadMoreProduct();
                }
            }

            private void loadMoreProduct(){
                if(!mKeywordBean.isReceived()){return;}
                if(mKeywordBean.isEndPage()){
                    Toast.makeText(getContext(),"没有更多了",Toast.LENGTH_LONG).show();
                }else {
                    mKeywordBean.nextPage();
                    new SearchProduct(mMyHandler, mUserBean.getSession(),mKeywordBean).start();
                    mKeywordBean.setIsReceived(false);
                }
            }
        });

        mSearchIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).hideKeyboard(mSearchEdit.getWindowToken());
                startSearch(false);
            }
        });

        mHomeCardAdapter.setOnItemClickListener(new HomeCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                startProductDetailActivity(position);
            }
        });

        mOrderByPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mKeywordBean.getOrder_by().equals("price")){
                    mKeywordBean.setOrder_by("price");
                    mKeywordBean.setReverse(false);
                }else {
                    mKeywordBean.setReverse(!mKeywordBean.getReverse());
                }
                startSearch(true);
            }
        });


        mOrderByScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mKeywordBean.getOrder_by().equals("average_score")){
                    mKeywordBean.setOrder_by("average_score");
                    mKeywordBean.setReverse(false);
                }else {
                    mKeywordBean.setReverse(!mKeywordBean.getReverse());
                }
                startSearch(true);
            }
        });


        mOrderBySalesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mKeywordBean.getOrder_by().equals("total_sales")){
                    mKeywordBean.setOrder_by("total_sales");
                    mKeywordBean.setReverse(false);
                }else {
                    mKeywordBean.setReverse(!mKeywordBean.getReverse());
                }
                startSearch(true);
            }
        });
    }

    private void startProductDetailActivity(int position){
        ProductBean productBean = mList.get(position);
        Intent intent = new Intent(getContext(), DetailProductActivity.class);
        intent.putExtra(ProductBean.getClassName(),productBean);
        intent.putExtra(UserBean.getClassName(), mUserBean);
        startActivity(intent);
    }

    private void startSearch(boolean isChangedOrder){
        mList.clear();
        mHomeCardAdapter.update(mList);
        if(!isChangedOrder){
            mKeywordBean.resetAll();
        }
        mKeywordBean.setKeyword(mSearchEdit.getText().toString());
        mKeywordBean.setIsReceived(false);
        new SearchProduct(mMyHandler, mUserBean.getSession(),mKeywordBean).start();
    }

    private void searchBtttonTranslation(Boolean toRight){
        // 计算要将放大镜图标卫衣的距离
        int w = mSearchEdit.getMeasuredWidth() - mSearchIm.getMeasuredWidth();
        mSearchImIsRight = toRight;

        if(toRight){
//            // 左 -> 右 w 个单位 动画时长 500ms
            mSearchIm.animate().setDuration(500).translationX(w);
        }
        else{
            // 左 <- 右 w 个单位 动画时长 500ms
            mSearchIm.animate().setDuration(500).translationX(-w);
        }
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what){
                case -1:
                    break;
                case 1:
                    mKeywordBean = (KeyWordJson.KeywordBean) bundle.getSerializable(KeyWordJson.KeywordBean.getClassName());
                    new SearchProduct(this, mUserBean.getSession(),mKeywordBean).start();
                    mSearchEdit.setText(mKeywordBean.getKeyword());
                    searchBtttonTranslation(true);
                    break;
                case 2:
                    ProductJson productJson = (ProductJson) bundle.getSerializable(ProductJson.getClassName());
                    List<ProductBean> list = productJson.getItems();
                    if(list.size() == 0){
                        mKeywordBean.setEndPage(true);
                    }
                    else{
                        mList.addAll(list);
                        mHomeCardAdapter.update(mList);
                    }
                    break;
            }
            mKeywordBean.setIsReceived(true);
        }
    }

    class GetHotKeyword extends Thread{
        String session;
        MyHandler myHandler;

        GetHotKeyword(MyHandler h,String s){
            this.session = s;
            this.myHandler = h;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = -1;
            try{
                Response response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/get_hot_keyword.action",this.session);
                if(response.isSuccessful()){
                    String bodyString = response.body().string();
                    Gson gson = new Gson();
                    KeyWordJson keyWordJson = gson.fromJson(bodyString,KeyWordJson.class);
                    if(keyWordJson != null && keyWordJson.getData() != null){
                        KeyWordJson.KeywordBean keywordBean = keyWordJson.getData();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KeyWordJson.KeywordBean.getClassName(),keywordBean);
                        message.setData(bundle);
                        message.what = 1;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mMyHandler.sendMessage(message);
            }
        }
    }

    class SearchProduct extends Thread{

        KeyWordJson.KeywordBean keywordBean;
        String session;
        MyHandler myHandler;


        SearchProduct(MyHandler h, String s, KeyWordJson.KeywordBean k){
            this.keywordBean = k;
            this.session = s;
            this.myHandler = h;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = -1;
            try {
                HashMap<String,String> params = new HashMap<>();
                params.put("keyword",this.keywordBean.getKeyword());
                params.put("order_by",this.keywordBean.getOrder_by());
                params.put("page",String.valueOf(this.keywordBean.getPage()));
                params.put("reverse",String.valueOf(this.keywordBean.getReverse()));

                Response response = RequestUtil.Get(MyServerConfig.SERVER_PATH + "/product/search/get_items.action",params,session);
                if (response.isSuccessful()){
                    String bodyString = response.body().string();
                    Gson gson = new Gson();
                    ProductJson productJson = gson.fromJson(bodyString,ProductJson.class);
                    if(productJson != null ){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ProductJson.getClassName(),productJson);
                        message.setData(bundle);
                        message.what = 2;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                this.myHandler.sendMessage(message);
            }
        }
    }

}
