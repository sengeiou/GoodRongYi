package com.lichi.goodrongyi.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lichi.goodrongyi.R;

/**
 * Created by Wang on 2017/12/8.
 */

public class ModifyCardPopupWindow extends PopupWindow {
    private TextView mTVModifyCard;
    private View mMenuView;
    private LinearLayout mLLPopup;

    public ModifyCardPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.modify_card_popup_window, null);
        mTVModifyCard = (TextView) mMenuView.findViewById(R.id.tv_modify_card);

        //设置按钮监听
        mTVModifyCard.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupwindow_card_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int width = mMenuView.findViewById(R.id.ll_popup).getLeft();
                int height = mMenuView.findViewById(R.id.ll_popup).getBottom();
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if( x < width || y > height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
