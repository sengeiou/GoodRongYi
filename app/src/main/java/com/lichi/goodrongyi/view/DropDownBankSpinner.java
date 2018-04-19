package com.lichi.goodrongyi.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.utill.RecycleViewDivider;

import java.util.List;

/**
 * Auther: Scott
 * Date: 2017/1/11 0011
 * E-mail:hekescott@qq.com
 */

public class DropDownBankSpinner extends PopupWindow {

    private List<SelectbankBean> mDataList;
    private Context mContext;
    private View catContentView;
    private Resources mRes;
    private RecyclerView catPopListView;
    private CommonAdapter<SelectbankBean> mCatgroyAdapter;
    private int selectPos;

    public DropDownBankSpinner(Context context, List<SelectbankBean> dataList) {
        super(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mContext = context;
        mDataList = dataList;
        mRes = mContext.getResources();
        IsSelc = false;
        initView();
        initAdapter();
    }

    public void setDeafultSelectPos(int pos) {
        selectPos = pos;
        SelectbankBean selectItem = mDataList.get(selectPos);
        selectItem.isSelected = true;
        mCatgroyAdapter.notifyDataSetChanged();
    }

    public int getSelectPos() {
        return selectPos;
    }

    public SelectbankBean getSelectDataItem() {
        return mSelectDataItem;
    }

    public boolean IsSelc = false;

    public boolean getIsSelec() {
        return IsSelc;
    }

    private SelectbankBean mSelectDataItem;

    private void initAdapter() {
        mCatgroyAdapter = new CommonAdapter<SelectbankBean>(mContext, R.layout.item_banklistview_popupwindow, mDataList) {
            @Override
            public void convert(ViewHolder helper, SelectbankBean item, final int position) {
                LinearLayout background = helper.getView(R.id.background);
                TextView tv = helper.getView(R.id.listview_popwind_tv);
                tv.setText(item.name);
                if (item.isSelected) {
                    background.setBackgroundResource(R.drawable.botton_bank_yes);
                } else {
                    background.setBackgroundResource(R.drawable.botton_bank_no);
                }

                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IsSelc = true;
                        SelectbankBean selectItem = mDataList.get(position);
                        selectPos = position;
                        mSelectDataItem = selectItem;
                        singleChecked(mDataList, selectItem);
                        for (SelectbankBean dataItem : mDataList) {
                            dataItem.isSelected = false;
                        }
                        selectItem.isSelected = true;
                        mCatgroyAdapter.notifyDataSetChanged();
                        DropDownBankSpinner.this.dismiss();
                    }
                });
            }
        };
        catPopListView.setAdapter(mCatgroyAdapter);
    }

    private void initView() {
        catContentView = View.inflate(mContext, R.layout.popwin_bank_list, null);
        this.setContentView(catContentView);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popwin_anim_style);
        catPopListView = (RecyclerView) catContentView.findViewById(R.id.popwin_supplier_list_lv);
        catPopListView.setLayoutManager(new GridLayoutManager(mContext, 4));
        catPopListView.setNestedScrollingEnabled(false);
        catPopListView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, Color.WHITE));
        catPopListView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, Color.WHITE));

        catContentView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        DropDownBankSpinner.this.dismiss();
                    }
                });
    }

    private void singleChecked(List<SelectbankBean> items, SelectbankBean item) {
        for (SelectbankBean d : items) {
            if (d.id != item.id) {
                d.isSelected = false;
            }
        }
    }

    public void showAsDropDownBelwBtnView(View btnView) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            btnView.getGlobalVisibleRect(rect);
            int h = btnView.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(btnView, 0, 0);
    }
}
