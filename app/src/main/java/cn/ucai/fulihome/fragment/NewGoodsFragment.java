package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import cn.ucai.fulihome.adapter.NewGoodsAdapter;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class NewGoodsFragment extends Fragment {


    MainActivity context;
    NewGoodsAdapter newGoodsAdapter;
    ArrayList<NewGoodsBean> newgoodslist;

    int pageId=1;
    @BindView(R.id.tvNewGoods)
    TextView tvNewGoods;
    @BindView(R.id.fragmentRecyclerViewNewGoods)
    RecyclerView fragmentRecyclerViewNewGoods;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        context = (MainActivity) getContext();
        newgoodslist = new ArrayList<>();
        newGoodsAdapter = new NewGoodsAdapter(context, newgoodslist);
        initView();
        initData();
        return layout;

    }

    private void initData() {
        NetDao.downloadNewGoods(context, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                //  设置刷新中··· 为不再刷新，不可见状态
                SwipeRefreshLayout.setVisibility(View.GONE);
                fragmentRecyclerViewNewGoods.setEnabled(false);

                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    newGoodsAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                //  设置刷新中··· 为不再刷新，不可见状态
                SwipeRefreshLayout.setVisibility(View.GONE);
                fragmentRecyclerViewNewGoods.setEnabled(false);
                //  设置显示数据异常
                CommonUtils.showLongToast("数据异常，请稍后再试");
                L.e("main" + error);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, I.COLUM_NUM);
        fragmentRecyclerViewNewGoods.setLayoutManager(gridLayoutManager);
        fragmentRecyclerViewNewGoods.setHasFixedSize(true);
        fragmentRecyclerViewNewGoods.setAdapter(newGoodsAdapter);
    }
}
