package com.dm.common.ui.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 自由TabLayout联动的ScrollView: 适用于多个页面而页面内容少的功能按钮界面。
 *
 * @Author: SWY
 * @Date: 2019/5/11 10:06
 */
public class LinkScrollView extends ScrollView {

    private List<View> titleView = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public LinkScrollView(Context context) {
        super(context);
    }

    public LinkScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int measureHeight;

    ScrollToItemListener scrollToItemListener;

    TitleDataCallback titleDataCallback;

    public LinkScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinkScrollView bindTitleView(@IdRes int... titleIds) {
        for (int id : titleIds) {
            View v = findViewById(id);

            if (!(v instanceof TextView)) {
                throw new RuntimeException("bindView must be TextView ");
            }

            titles.add(((TextView) v).getText() + "");

            while (!(v.getParent().getParent() instanceof ScrollView)) {
                v = (View) v.getParent();
            }

            titleView.add(v);

        }

        View lastTitle = findViewById(titleIds[titleIds.length - 1]);
        adjuestEndHeight(lastTitle);

        return this;
    }

    private void adjuestEndHeight(final View lastTitleView) {

        post(new Runnable() {
            @Override
            public void run() {

                //最后一个View
                ViewGroup viewGroup = (ViewGroup) getChildAt(0);
                View lastView = viewGroup.getChildAt(viewGroup.getChildCount() - 1);

                //总高减去最后的标题栏高度为最后的显示高度，再用scrollView高度减去其值为应该向上偏移的值
                int spaceHeight = (int) (getMeasuredHeight() - (measureHeight - lastTitleView.getY()));

                //增加高度并增加padding,不影响原效果
                ViewGroup.LayoutParams params = lastView.getLayoutParams();
                params.height = params.height + spaceHeight;
                lastView.setLayoutParams(params);

                lastView.setPadding(0, 0, 0, spaceHeight + lastView.getPaddingBottom());
            }
        });

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        post(new Runnable() {
            @Override
            public void run() {

                measureHeight = getChildAt(0).getMeasuredHeight();

            }
        });

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        for (int i = titleView.size() - 1; i >= 0; i--) {

            if (t >= titleView.get(i).getY()) {

                if (scrollToItemListener != null)
                    scrollToItemListener.scrollToItem(i, titles.get(i));

                return;
            }

        }

    }

    public LinkScrollView registerScrollListener(ScrollToItemListener scrollToItemListener) {
        this.scrollToItemListener = scrollToItemListener;
        return this;
    }

    public LinkScrollView registerDataCallback(TitleDataCallback titleDataCallback) {
        this.titleDataCallback = titleDataCallback;
        return this;
    }

    public void go() {

        if (titleDataCallback != null)
            titleDataCallback.titlesBack(titles.toArray(new String[]{}));

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
