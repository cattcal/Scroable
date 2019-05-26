package com.custom.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scroable.ScroableView;
import com.scroable.ScroTabView;

public class MainActivity extends AppCompatActivity {


    ScroableView scroableView;

    ScroTabView scroTabView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroableView = findViewById(R.id.scroable);

        scroTabView = findViewById(R.id.slidingTablayout);

        scroableView.bindTitleView(R.id.title1, R.id.title2, R.id.title3, R.id.title4, R.id.title5, R.id.title6)
                .registerScrollListener(new ScroableView.ScrollToItemListener() {
                    @Override
                    public void scrollToItem(int position, String title) {
                        scroTabView.setCurrentTab(position);
                    }
                })
                .registerDataCallback(new ScroableView.TitleDataCallback() {
                    @Override
                    public void titlesBack(String[] titles) {
                        scroTabView.setTabsStr(titles);
                    }
                })
                .go();

    }
}
