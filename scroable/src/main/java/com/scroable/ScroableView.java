package com.scroable;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scroable.widget.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 自由与TabLayout联动的ScrollView: 适用于多个页面而页面内容少的功能按钮界面。
 *
 * @Author: SWY
 * @Date: 2019/5/11 10:06
 */
public class ScroableView extends NestedScrollView {

    private List<View> titleView = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private FrameLayout rootView;
    private ScroTabView scroTabView;

    public ScroableView(Context context) {
        super(context);
    }

    public ScroableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int measureHeight;

    List<ScrollToItemListener> scrollToItemListener = new ArrayList<>();

    List<TitleDataCallback> titleDataCallback = new ArrayList<>();

    public ScroableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScroableView bindTitleView(@IdRes int... titleIds) {
        for (int id : titleIds) {
            View v = findViewById(id);

            if (v == null)
                return this;

            if (!(v instanceof TextView)) {
                throw new RuntimeException("bindView must be TextView ");
            }

            titles.add(((TextView) v).getText() + "");

            while (!(v.getParent().getParent() instanceof NestedScrollView)) {
                v = (View) v.getParent();
            }

            titleView.add(v);
        }
        return this;
    }

    private void adjuestEndHeight(final View lastTitleView) {

        //总高减去最后的标题栏高度为最后的显示高度，再用scrollView高度减其值为应该向上偏移的值
        int spaceHeight = (int) (getMeasuredHeight() - (measureHeight - lastTitleView.getY()));
        rootView.setPadding(0, 0, 0, spaceHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        post(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });

    }

    private void initView() {
        if (getChildCount() == 0)
            return;

        View oriView = getChildAt(0);
        removeView(oriView);

        rootView = new FrameLayout(getContext());
        rootView.setLayoutParams(new LayoutParams(-1, -2));
        rootView.addView(oriView);

        addView(rootView);

        measureHeight = oriView.getMeasuredHeight();

        adjuestEndHeight(titleView.get(titleView.size() - 1));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        for (int i = titleView.size() - 1; i >= 0; i--) {

            if (t >= titleView.get(i).getY()) {

                if (scrollToItemListener.size() != 0) {
                    for (ScrollToItemListener scroll : scrollToItemListener)
                        scroll.scrollToItem(i, titles.get(i));
                }
                return;
            }
        }
    }

    public ScroableView registerScrollListener(ScrollToItemListener scrollToItemListener) {
        this.scrollToItemListener.add(scrollToItemListener);
        return this;
    }

    public ScroableView registerDataCallback(TitleDataCallback titleDataCallback) {
        this.titleDataCallback.add(titleDataCallback);
        return this;
    }

    public void go() {

        if (titleDataCallback.size() != 0) {
            for (TitleDataCallback callback : titleDataCallback)
                callback.titlesBack(titles.toArray(new String[]{}));
        }
        if (scroTabView != null) {
            scroTabView.setTabsStr(titles.toArray(new String[]{}));
            scroTabView.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    scrollToPosition(position);
                }

                @Override
                public void onTabReselect(int position) {

                }
            });
            registerScrollListener(new ScrollToItemListener() {
                @Override
                public void scrollToItem(int position, String title) {
                    scroTabView.setCurrentTab(position);
                }
            });

        }

    }

    public ScroableView bindScroTabView(ScroTabView scroTabView) {
        this.scroTabView = scroTabView;
        return this;
    }

    public ScroTabView getTabView() {
        return scroTabView;
    }

    /***
     *  外部直接调用 传入TabLayout点击位置即可
     * @param position
     */
    public void scrollToPosition(int position) {

        View v = titleView.get(position);

        if (v == null)
            return;

        scrollTo(0, (int) v.getY());
    }

    public interface ScrollToItemListener {
        void scrollToItem(int position, String title);
    }

    public interface TitleDataCallback {
        void titlesBack(String[] titles);
    }
}
