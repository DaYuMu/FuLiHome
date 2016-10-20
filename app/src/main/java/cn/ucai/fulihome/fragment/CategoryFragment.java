package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.adapter.CategoryAdapter;
import cn.ucai.fulihome.bean.CategoryChildBean;
import cn.ucai.fulihome.bean.CategoryGroupBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;

    MainActivity mContext;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> groupList;//  大类
    ArrayList<ArrayList<CategoryChildBean>> childList;

    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, groupList, childList);
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        elvCategory.setGroupIndicator(null);
        elvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupBean = ConvertUtils.array2List(result);
                    groupList.addAll(groupBean);
                    /*for (CategoryGroupBean g : groupBean) {
                        downloadChild(g.getId());
                    }*/
                    for (int i =0;i<groupList.size();i++) {
                        childList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean groupBean1 = groupList.get(i);
                        downloadChild(groupBean1.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error"+error);
            }
        });
    }

    private void downloadChild(int id, final int index) {
        NetDao.downloadChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childBean = ConvertUtils.array2List(result);
                    childList.set(index, childBean);
                }

                if (groupCount == groupList.size()) {
                    mAdapter.initDate(groupList, childList);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error"+error);
            }
        });
    }

    @Override
    protected void setListener() {

    }


}
