package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.CategoryChildBean;
import cn.ucai.fulihome.bean.CategoryGroupBean;
import cn.ucai.fulihome.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/20 0020.
 * 继承可折叠的Adapter类。
 */
public class CategoryAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<CategoryGroupBean> groupList;//  大类
    ArrayList<ArrayList<CategoryChildBean>> childList;//  小类，每个大类里都有小类。注意嵌套关系。

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> grouplist,
                           ArrayList<ArrayList<CategoryChildBean>> childlist) {
        this.mContext = mContext;
        groupList = new ArrayList<>();
        groupList.addAll(grouplist);
        childList = new ArrayList<>();
        childList.addAll(childlist);
    }

    @Override
    public int getGroupCount() {//  得到大类的数量
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && childList.get(groupPosition) != null
                ? childList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {//得到大类的对象
        return groupList != null ? groupList.get(groupPosition) : null;
    }

    @Override
    public Object getChild(int childPosition, int groupPosition) {
        return childList != null && childList.get(groupPosition) != null
                ? childList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            groupViewHolder = new GroupViewHolder(view);
            view.setTag(groupViewHolder);
        } else {
            view.getTag();
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        CategoryGroupBean groupBean = (CategoryGroupBean) getGroup(groupPosition);
        if (groupBean != null) {
            ImageLoader.downloadImg(mContext, groupViewHolder.groupImage, groupBean.getImageUrl());
            groupViewHolder.groupName.setText(groupBean.getName());
            groupViewHolder.groupExpand.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
        }

        return view;
    }

    @Override
    public View getChildView(int childPosition, int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view != null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            view.getTag();
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        CategoryChildBean childBean = (CategoryChildBean) getChild(childPosition, groupPosition);
        if (childBean != null) {
            ImageLoader.downloadImg(mContext,childViewHolder.groupImage,childBean.getImageUrl());
            childViewHolder.groupName.setText(childBean.getName());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.groupImage)
        ImageView groupImage;
        @BindView(R.id.groupName)
        TextView groupName;
        @BindView(R.id.groupExpand)
        ImageView groupExpand;
        @BindView(R.id.groupRelativeLayout)
        RelativeLayout groupRelativeLayout;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.groupImage)
        ImageView groupImage;
        @BindView(R.id.groupName)
        TextView groupName;
        @BindView(R.id.ChildRelativeLayout)
        RelativeLayout ChildRelativeLayout;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
