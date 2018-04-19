package com.lichi.goodrongyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.CreditCardDetailsBean;
import com.lichi.goodrongyi.mvp.model.CreditCardMonthBean;
import com.lichi.goodrongyi.ui.activity.loans.RefundActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.view.swipelayout.SwipeLayout;

import java.util.List;

/**
 * Created by 默小小 on 2017/12/6.
 */

public class ExpandableListAdatapter extends BaseExpandableListAdapter {
    private List<CreditCardMonthBean.DataList> group;
    private Context context;
    private GroupViewClickListener mGroupViewClickListener;

    public ExpandableListAdatapter(Context context, List<CreditCardMonthBean.DataList> group,GroupViewClickListener groupViewClickListener) {
        mGroupViewClickListener=groupViewClickListener;
        this.context = context;
        this.group = group;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.get(groupPosition).child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (groupViewHolder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.visa_detail_item_group, null);
            groupViewHolder = new GroupViewHolder();

            groupViewHolder.dayOfMonth = (TextView) convertView.findViewById(R.id.tv_visa_detail_day_month);
            groupViewHolder.monthOfYear = (TextView) convertView.findViewById(R.id.tv_visa_detail_month_year);
            groupViewHolder.visaPrice = (TextView) convertView.findViewById(R.id.tv_visa_detail_price);
            groupViewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_visa_ex_icon);
            groupViewHolder.mRepayment = (TextView) convertView.findViewById(R.id.repayment);
            groupViewHolder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipelayout);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        CreditCardMonthBean.DataList dataList = group.get(groupPosition);
        String paytime = DateFormatUtil.stringToString(dataList.statementEndDate, "yyyy-MM-dd", "MM月yy年");
        String statementEndtime = DateFormatUtil.stringToString(dataList.statementEndDate, "yyyy-MM-dd", "MM月dd日");
        String statementStarttime = DateFormatUtil.stringToString(dataList.statementStartDate, "yyyy-MM-dd", "MM月dd日");
        groupViewHolder.dayOfMonth.setText(statementStarttime + "-" + statementEndtime);
        groupViewHolder.monthOfYear.setText(paytime);
        groupViewHolder.visaPrice.setText(dataList.balanceRmb + "");
        if (isExpanded) {
            groupViewHolder.icon.setImageResource(R.mipmap.down);
        } else {
            groupViewHolder.icon.setImageResource(R.mipmap.lef);
        }
        groupViewHolder.mRepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startNewActivity(context, RefundActivity.class);
            }
        });

        groupViewHolder.swipeLayout.setOnSwipeLayoutClickListener(new SwipeLayout.OnSwipeLayoutClickListener() {
            @Override
            public void onClick() {
                mGroupViewClickListener.onGroupClick(groupPosition);
            }
        });

        ((LinearLayout) groupViewHolder.swipeLayout.getDeleteView()).getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewClickListener.onDeleteGroupClick(groupPosition);
               // Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHohder viewHohder = null;
        if (viewHohder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.visa_detail_item_child, null);
            viewHohder = new ChildViewHohder();

            viewHohder.limitType = (TextView) convertView.findViewById(R.id.tv_visa_detail_limit_type);
            viewHohder.limitDate = (TextView) convertView.findViewById(R.id.tv_visa_detail_limit_date);
            viewHohder.limitNum = (TextView) convertView.findViewById(R.id.tv_visa_detail_limit_num);
            viewHohder.limitIcon = (ImageView) convertView.findViewById(R.id.iv_visa_detail_limit_icon);
            convertView.setTag(viewHohder);
        } else {
            viewHohder = (ChildViewHohder) convertView.getTag();
        }
        CreditCardDetailsBean.DataList dataList = group.get(groupPosition).child.get(childPosition);
        viewHohder.limitDate.setText(dataList.transDate);
        viewHohder.limitType.setText(dataList.transDesc);
        viewHohder.limitNum.setText(dataList.postAmt);
        viewHohder.limitIcon.setImageResource(R.drawable.merchant);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView monthOfYear, dayOfMonth, visaPrice, mRepayment;
        ImageView icon;
        SwipeLayout swipeLayout;
    }

    class ChildViewHohder {
        TextView limitType, limitDate, limitNum;
        ImageView limitIcon;
    }

    public interface GroupViewClickListener {
        void onGroupClick(int groupPosition);
        void onDeleteGroupClick(int groupPosition);
    }
}
