package com.lichi.goodrongyi.view.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.DataItem;

import java.util.List;

/**
 * Auther: Scott
 * Date: 2017/1/11 0011
 * E-mail:hekescott@qq.com
 */

public class DropUpDatePopupWindow extends BottomPushPopupWindow<List<DataItem>> {

    private List<DataItem> mDataList;
    private View catContentView;
    private Resources mRes;
    private ListView catPopListView;
    private WrapAdapter<DataItem> mCatgroyAdapter;
    private int selectPos;

    public DropUpDatePopupWindow(Context context, List<DataItem> dataList) {
        super(context, dataList);
        //super(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected View generateCustomView(List<DataItem> dataItems) {
        mDataList = dataItems;
        mRes = context.getResources();
        initView();
        return catContentView;
    }

    public void setDeafultSelectPos(int pos) {
        selectPos = pos;
        DataItem selectItem = mDataList.get(selectPos);
        selectItem.isSelected = true;
        mCatgroyAdapter.notifyDataSetChanged();
    }

    public int getSelectPos() {
        return selectPos;
    }

    public DataItem getSelectDataItem() {
        return mSelectDataItem;
    }

    public boolean IsSelc = false;

    public boolean getIsSelec() {
        return IsSelc;
    }

    private DataItem mSelectDataItem;

    private void initAdapter() {
        mCatgroyAdapter = new WrapAdapter<DataItem>(context, mDataList, R.layout.date_listview_item_popwin) {

            @Override
            public void convert(ViewHolder helper, DataItem item, int position) {
                RelativeLayout relativelayout = helper.getView(R.id.listitem_popwind_ll);
                TextView tv = helper.getView(R.id.listview_popwind_tv);
                if (position == 0) {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    tv.setTextColor(mContext.getResources().getColor(R.color.color_login_devide));
                    relativelayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_login_devide));
                } else {
                    tv.setTextColor(mContext.getResources().getColor(R.color.app_title_color1));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    relativelayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv.setText(item.name);
                }
/*                ImageView imageView = helper.getView(R.id.select_status_img);
                if (item.isSelected) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }*/
            }
        };
        catPopListView.setAdapter(mCatgroyAdapter);
        catPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    IsSelc = true;
                    DataItem selectItem = mDataList.get(position);
                    selectItem.isSelected = true;
                    selectPos = position;
                    mSelectDataItem = selectItem;
                    singleChecked(mDataList, selectItem);
                    mCatgroyAdapter.notifyDataSetChanged();
                    DropUpDatePopupWindow.this.dismiss();
                }
            }
        });
    }

    private void initView() {
        IsSelc = false;
        catContentView = View.inflate(context, R.layout.date_popupwind, null);
        this.setContentView(catContentView);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popwin_anim_style);
        catPopListView = (ListView) catContentView.findViewById(R.id.popwin_supplier_list_lv);
        catContentView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        DropUpDatePopupWindow.this.dismiss();
                    }
                });
        catContentView.findViewById(R.id.cancel)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        DropUpDatePopupWindow.this.dismiss();
                    }
                });
        initAdapter();
    }

    private void singleChecked(List<DataItem> items, DataItem item) {
        for (DataItem d : items) {
            if (d.id != item.id) {
                d.isSelected = false;
            }
        }
    }


    /**
     * 显示在界面的底部
     */
    public void show(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public void showAsDropDownBelwBtnView(View btnView) {
        if (Build.VERSION.SDK_INT >= 24) {

            Rect rect = new Rect();
            btnView.getGlobalVisibleRect(rect);
            int h = btnView.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(btnView, 0, 2);
    }
}
