package com.example.zh123.recommendationsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.zh123.recommendationsystem.entities.UserBean;
import com.example.zh123.recommendationsystem.myfragments.GoodsFragment;
import com.example.zh123.recommendationsystem.myfragments.HomeFragment;
import com.example.zh123.recommendationsystem.myfragments.MyFragment;
import com.example.zh123.recommendationsystem.myfragments.ShopCarFragment;
import com.example.zh123.recommendationsystem.views.NoScrollViewPager;

import java.lang.reflect.Field;

/**
 * Created by zh123 on 19-12-6.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    // 整个主界面的ViewPager对象
    NoScrollViewPager mainPager;
    // 整个APP底部的导航栏对象
    BottomNavigationView navigation;
    // 底部导航栏的菜单选项对象
    private MenuItem menuItem;
    // 定义一个底部导航栏的选择监听器
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * 方法重写此方法能够在点击底部导航栏时候调用
         * 传入的 item 当前底部导航栏被点击的item对象
         * 此方法在这里重写的更能是根据点击的item的id值将mainPager切换至item对应的界面
        */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mainPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_goods:
                    mainPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_shop_car:
                    mainPager.setCurrentItem(2);
                    mShopCarFragment.updateShopCarItems();
                    return true;
                case R.id.navigation_my:
                    mainPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
    // 用户数据
    public UserBean mUserBean;

    private HomeFragment mHomeFragment;
    private GoodsFragment mGoodsFragment;
    private ShopCarFragment mShopCarFragment;
    private MyFragment mMyFragment;


    /**
     * 继承的 Activity 的方法重写
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传入的用户数据对象
        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getSerializableExtra(UserBean.getClassName());
        // 配置不让整个窗口根据输入法变形
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 将此Activity使用activity_main.xml文件进行布局
        setContentView(R.layout.activity_main);
        // 根据传入的id值找到底部的导航栏对象
        navigation = findViewById(R.id.navigation);
        // 根据传入的id值只找到viewPager对象( 就是整个app 左右滑动的组件 )
        mainPager = findViewById(R.id.pager_main);
        mainPager.setScrollable(false);
        // 因为BottomNavigationView item的数量默认超过3就会不进行均分 下面这个方法就是将四个item进行均分
        disableShiftMode(navigation);

        // 将窗口滑动组件设置一个监听器 (因为本类实现了ViewPager.OnPageChangeListener) 所以传入this即可
        // 暂时页面不需要滑动所以注释了
        //mainPager.addOnPageChangeListener(this);

        // 将窗口滑动组件设置一个适配器,适配器的实现代码在下文
        mainPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mUserBean));
        // 将底部的导航栏设置一个选择监听器
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


    }

    /**
     * 此方法的作用就是将原本 BottomNavigationView 的 item 数量大于3时也强制使起进行均分
     */
    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**下面三个方法实现ViewPager.OnPageChangeListener*/


    /**
     * 此方法适用于窗口进行滑动过程中调用
     * 传入参数position表示当前页面所处的坐标
     * positionOffset表示页面的偏移百分比
     * positionOffsetPixels 表示页面的偏移像素值
    */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 此方法是窗口停止滑动时调用
     * 传入的参数表示当前页面所处的坐标值
     */
    @Override
    public void onPageSelected(int position) {
        // 根据传入的坐标值在底部导航栏找到其对应的item项
        menuItem = navigation.getMenu().getItem(position);
        // 将此item项的状态设置为被点击状态
        menuItem.setChecked(true);
    }

    /**
     * 此方法在手指操作屏幕的时候发生变化。有三个值：0(END)，1(PRESS) ，2(UP) 。
     * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，手指抬起时，
     * 如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。总共执行这个方法
     * 三次。一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方
     * 法两次，state值分别是1，0 。当setCurrentItem翻页时，会执行这个方法两次，
     * state值分别为2 ，0 。
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /********************************************************************************************/


    /**
     * 这里是定义了一个适配器对象继承自Fragment的页面适配器
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        // 初始化一个数组用于存放ViewPager中所有页面的Fragment对象
        private Fragment[] mFragments;

        // 这是适配器的初始化方法只需调用父类的方法即可
        ViewPagerAdapter(FragmentManager fm,UserBean u) {
            super(fm);
            Bundle bundle = new Bundle();
            bundle.putSerializable(UserBean.getClassName(),u);

            mHomeFragment = new HomeFragment();
            mGoodsFragment = new GoodsFragment();
            mShopCarFragment = new ShopCarFragment();
            mMyFragment = new MyFragment();

            mHomeFragment.setArguments(bundle);
            mGoodsFragment.setArguments(bundle);
            mShopCarFragment.setArguments(bundle);
            mMyFragment.setArguments(bundle);

            mFragments = new Fragment[]{mHomeFragment, mGoodsFragment, mShopCarFragment,mMyFragment};
        }
        // 此方法是重写父类的方法 此方法的作用是根据传入的坐标值返回其对应的fragment对象
        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }
        // 重写父类的方法 此方法的作用是获取页面的个数
        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

// add 2020-1-6
// 主要是为了解决EditText组件点击外部时 使得其失去焦点并隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    hideKeyboard(v.getWindowToken());
                    if (editText != null) {
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    EditText editText = null;

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            editText = (EditText) v;
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
// END

}
