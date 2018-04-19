package com.lichi.goodrongyi.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author:Jenny
 * Date:16/5/15 16:49
 * E-mail:fishloveqin@gmail.com
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public ViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override


    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position > viewList.size() - 1) return;
        container.removeView(viewList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
