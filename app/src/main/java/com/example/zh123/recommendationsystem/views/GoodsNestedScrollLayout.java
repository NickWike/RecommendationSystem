package com.example.zh123.recommendationsystem.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zh123 on 20-1-15.
 */

public class GoodsNestedScrollLayout extends LinearLayout {
    // 最后触摸屏幕的Y的坐标值(绝对坐标系)
    private  int mTouchLastY = 0;
    // 整个布局的头视图(也可以是一个View Group)  该对象即是我们需要向上滑动时脱离显示区进行隐藏的视图
    private View mHeader;
    // 上面需要隐藏的这个视图的高度
    private int mHeaderHeight;


    public GoodsNestedScrollLayout(Context context) {
        super(context);
    }

    public GoodsNestedScrollLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsNestedScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        // 返回true 允许嵌套滑动
        return true;
    }

    // 当嵌套滑动事件出发前进行回调
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        // 表示header视图是否为向上滑动  注意getScrollY()表示整个layout Y轴方向上的偏移量 上为正 下为负
        boolean headerScrollUp = dy > 0 && getScrollY() < mHeaderHeight;
        // 表示header视图是否为向下滑动,target 在这里表示的是recyclerView对象 后面对应的方法是判断recyclerView 是否滑到顶端 滑到顶端时返回false
        boolean headerScrollDown = dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1);
        // 当向上或向下移动任意一个事件满足时 对滑动距离进行消费
        if(headerScrollUp || headerScrollDown){
            // 调用内部方法将本layout 横向移动dx个单位 纵向移动dy个单位
            scrollBy(dx,dy);
            // 将Y轴方向的移动距离全部消费掉
            consumed[1]=dy;
        }
    }

    // 此方法在整个XML布局文件加载完毕后进行调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 初始化 header 对应的视图对象
        if(getChildCount() > 0){
            // 这里只是获取布局器中第一个view (为了简单写的比较粗暴)
            mHeader = getChildAt(0);
        }
    }

    // 此方法在对view 的大小进行设置完毕后进行掉用 也就是view的大小改变之时
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 在这里view的大小初始完毕后才能从header中获取到header的高度
        mHeaderHeight = mHeader.getMeasuredHeight();
    }

    // 在这里是对View(本layout)进行测量时进行调用
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置header的所需的空间和大小,这里应为要纵向移出到屏幕外所以将其高度的空间模式设置为不确定
        mHeader.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        // 将顶部要移出布局的View的高度加在总的高度里面也就是拓宽及整个布局的高度
        int totalHeight = MeasureSpec.getSize(heightMeasureSpec) + mHeader.getMeasuredHeight();
        // 获取原来高度的空间模式
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        // 调调用父类方法
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(totalHeight, mode));
    }

    // 此方法跟scrollBy类似 都是滑动整个layout 只不过前者是根据传入的XY值相对移动 而此方法是绝对移动
    @Override
    public void scrollTo(int x, int y) {
        // 让header最下面的边缘始终保持在屏幕的顶部 不能移出去(负值) 不能超过原来初始的位置(大于原来header的高度)
        if(y < 0){
            y = 0;
        } else if (y > mHeaderHeight){
            y = mHeaderHeight;
        }
        super.scrollTo(0, y);
    }

    // 此方法在layout里的view被触碰时进行回调
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            // 屏幕按下事件
            case MotionEvent.ACTION_DOWN:
                //记录按下时候Y轴方向的初值
                mTouchLastY = (int) event.getRawY();break;
            // 手指在屏幕上的移动事件
            case MotionEvent.ACTION_MOVE:
                // 获取当前手指在Y轴方向的坐标值
                int y = (int) event.getRawY();
                // 获取当前手指滑动的距离
                int movY = mTouchLastY - y;
                // 将整个layout也移动相应的距离
                scrollBy(0,movY);
                mTouchLastY = y;break;
        }
        // 是否将此事件发送给其他的View
        return true;
    }

}
