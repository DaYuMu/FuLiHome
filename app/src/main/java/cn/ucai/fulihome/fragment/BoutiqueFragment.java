package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.adapter.BoutiqueAdapter;
import cn.ucai.fulihome.bean.BoutiqueBean;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.view.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class BoutiqueFragment extends Fragment {
    @BindView(R.id.tvNewGoods)
    TextView tvNewGoods;
    @BindView(R.id.fragmentRecyclerViewNewGoods)
    RecyclerView fragmentRecyclerViewNewGoods;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    LinearLayoutManager linearLayoutManager;
    MainActivity mContext;
    BoutiqueAdapter boutiqueAdapter;
    ArrayList<BoutiqueBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container,false);

        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        boutiqueAdapter = new BoutiqueAdapter(mContext,mList);
        initView();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownListener();
    }

    /**
     * 下拉刷新的方法
     */
    private void setPullDownListener() {
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.setRefreshing(true);
                tvNewGoods.setVisibility(View.VISIBLE);
                downloadBoutique();
            }
        });
    }

    private void initData() {
        downloadBoutique();
    }

    private void downloadBoutique() {
        NetDao.downloadBoutique(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                if (result != null && result.length > 0) {
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                        boutiqueAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                //  设置显示数据异常
                CommonUtils.showLongToast(error);
            }
        });
    }
    private void initView() {
        SwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        linearLayoutManager = new LinearLayoutManager(mContext);
        fragmentRecyclerViewNewGoods.setLayoutManager(linearLayoutManager);
        fragmentRecyclerViewNewGoods.setHasFixedSize(true);
        fragmentRecyclerViewNewGoods.setAdapter(boutiqueAdapter);
    }
}
