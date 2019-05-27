package com.custom.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scroable.ScroTabView;
import com.scroable.ScroableView;
import com.scroable.widget.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {


    ScroableView scroableView;

    ScroTabView scroTabView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroableView = findViewById(R.id.scroable);

        scroTabView = findViewById(R.id.slidingTablayout);

        scroTabView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                scroableView.scrollToPosition(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        scroableView.bindTitleView(R.id.title1, R.id.title2, R.id.title3, R.id.title4, R.id.title5, R.id.title6)
                .registerDataCallback(new ScroableView.TitleDataCallback() {
                    @Override
                    public void titlesBack(String[] titles) {
                        scroTabView.setTabsStr(titles);
                    }
                })
                .registerScrollListener(new ScroableView.ScrollToItemListener() {
                    @Override
                    public void scrollToItem(int position, String title) {
                        scroTabView.setCurrentTab(position);
                    }
                })
                .go();

    }
}
