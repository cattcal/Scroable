 ## 引入
 Add it in your root build.gradle at the end of repositories:

 	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Add the dependency
[![](https://jitpack.io/v/ailiwean/Scroable.svg)](https://jitpack.io/#ailiwean/Scroable)

	dependencies {
		 implementation 'com.github.ailiwean:Scroable:Tag'
     }

 ## 使用
简单方便，两步集成

 **step1**
       
         <com.scroable.ScroTabView
            android:layout_width="match_parent"
            android:layout_height="70dp"
	    />

        <com.scroable.ScroableView
            android:id="@+id/scroable"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">


                <!--标题可以存在多个嵌套-->
                <TextView
                    android:id="@+id/title1"
                    android:layout_width="match_parent"
                    android:layout_height="50dp" />


                <!--具体内容-->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="70dp" />
            
                
                ......

            </LinearLayout>

        </com.scroable.ScroableView>

ScroTabView是默认提供的一个TabLayout.源于[FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout)修改与Scroable组合使用更方便。

ScroableView是一个ScrollView,遵循ScrollView写法即可。 这里无非就是提供头View,头View必须属于TextView或其子类。 多层嵌套也无妨。

**step2**
  

```
 scroableView.bindTitleView(R.id.title1, R.id.title2, R.id.title3, R.id.title4, R.id.title5, R.id.title6)
                .bindScroTabView(scroTabView)
                .go();
```
这里绑定头部TextView，ScroTabView即可。


**自由组合**
   可以搭配其他TabLayout使用

```
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

```
 自由搭配的处理逻辑，registerDataCallback获取数据只会调用一次用于添加TabLayout，registerScrollListener滑到下一个Tab会回调一次用于切换TabLayout。

**ScroTabView样式**

