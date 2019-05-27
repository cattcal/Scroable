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


### 其他

**ScroTabView样式**

![在这里插入图片描述](https://github.com/H07000223/FlycoTabLayout/raw/master/preview_3.gif)
###### XML属性
## Attributes

|name|format|description|
|:---:|:---:|:---:|
| tl_indicator_color | color |设置显示器颜色
| tl_indicator_height | dimension |设置显示器高度
| tl_indicator_width | dimension |设置显示器固定宽度
| tl_indicator_margin_left | dimension |设置显示器margin,当indicator_width大于0,无效
| tl_indicator_margin_top | dimension |设置显示器margin,当indicator_width大于0,无效
| tl_indicator_margin_right | dimension |设置显示器margin,当indicator_width大于0,无效
| tl_indicator_margin_bottom | dimension |设置显示器margin,当indicator_width大于0,无效 
| tl_indicator_corner_radius | dimension |设置显示器圆角弧度
| tl_indicator_gravity | enum |设置显示器上方(TOP)还是下方(BOTTOM),只对常规显示器有用
| tl_indicator_style | enum |设置显示器为常规(NORMAL)或三角形(TRIANGLE)或背景色块(BLOCK)
| tl_underline_color | color |设置下划线颜色
| tl_underline_height | dimension |设置下划线高度
| tl_underline_gravity | enum |设置下划线上方(TOP)还是下方(BOTTOM)
| tl_divider_color | color |设置分割线颜色
| tl_divider_width | dimension |设置分割线宽度
| tl_divider_padding |dimension| 设置分割线的paddingTop和paddingBottom
| tl_tab_padding |dimension| 设置tab的paddingLeft和paddingRight
| tl_tab_space_equal |boolean| 设置tab大小等分
| tl_tab_width |dimension| 设置tab固定大小
| tl_textsize |dimension| 设置字体大小
| tl_textSelectColor |color| 设置字体选中颜色
| tl_textUnselectColor |color| 设置字体未选中颜色
| tl_textBold |boolean| 设置字体加粗
| tl_iconWidth |dimension| 设置icon宽度(仅支持CommonTabLayout)
| tl_iconHeight |dimension|设置icon高度(仅支持CommonTabLayout)
| tl_iconVisible |boolean| 设置icon是否可见(仅支持CommonTabLayout)
| tl_iconGravity |enum| 设置icon显示位置,对应Gravity中常量值,左上右下(仅支持CommonTabLayout)
| tl_iconMargin |dimension| 设置icon与文字间距(仅支持CommonTabLayout)
| tl_indicator_anim_enable |boolean| 设置显示器支持动画(only for CommonTabLayout)
| tl_indicator_anim_duration |integer| 设置显示器动画时间(only for CommonTabLayout)
| tl_indicator_bounce_enable |boolean| 设置显示器支持动画回弹效果(only for CommonTabLayout)
| tl_indicator_width_equal_title |boolean| 设置显示器与标题一样长(only for SlidingTabLayout)


### 支持
咖啡什么的根本不重要， 如果能帮到您， 笔者希望能得到一个大大的star鼓励一下，谢谢,谢谢,谢谢!
