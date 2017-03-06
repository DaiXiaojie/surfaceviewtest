package com.daixiaojie.surfaceviewtest.filter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by daixiaojie
 */

public class ViewAdapter extends PagerAdapter {

    private List<View> viewList;//数据源

    public ViewAdapter(List<View> viewList) {

        this.viewList = viewList;
    }


    //数据源的数目
    public int getCount() {

        return viewList.size();
    }


    public boolean isViewFromObject(View view, Object object) {
        return view == object;

    }


    //销毁一个页卡(即ViewPager的一个item)
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(viewList.get(position));
    }


    //对应页卡添加上数据
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }
}
