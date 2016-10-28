package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.adapter.CartAdapter;
import cn.ucai.fulihome.bean.CartBean;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();


    LinearLayoutManager linearLayoutManager;
    MainActivity mContext;
    CartAdapter CartAdapter;
    ArrayList<CartBean> mList;
    @BindView(R.id.SumPrice)
    TextView SumPrice;
    @BindView(R.id.RankPrice)
    TextView RankPrice;
    @BindView(R.id.Charge)
    TextView Charge;
    @BindView(R.id.tvNewGoods)
    TextView tvNewGoods;
    @BindView(R.id.fragmentRecyclerViewNewGoods)
    RecyclerView fragmentRecyclerViewNewGoods;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        L.e(TAG + "CartFragment.onCreateView.start");
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        CartAdapter = new CartAdapter(mContext, mList);
//        initView();   initData();   setListener();
        //  添加父类
        super.onCreateView(inflater, container, savedInstanceState);
        L.e(TAG + "CartFragment.onCreateView.start");
        return layout;
    }

    // 改为重写的方法。
    @Override
    protected void setListener() {
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
                downloadCartGoods();
            }
        });
    }

    @Override
    protected void initData() {
        downloadCartGoods();
    }

    private void downloadCartGoods() {
        User user = FuLiHomeApplication.getUser();
        if (user != null) {
            NetDao.downloadCort(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    //  设置刷新中··· 为不再刷新，不可见状态
                    tvNewGoods.setVisibility(View.GONE);
                    SwipeRefreshLayout.setEnabled(false);
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        L.e(TAG, "list[0]=" + list.get(0));
                        CartAdapter.initData(list);
                    }
                }

                @Override
                public void onError(String error) {
                    L.e(TAG, "CartFragment:onError");
                    //  设置刷新中··· 为不再刷新，不可见状态
                    tvNewGoods.setVisibility(View.GONE);
                    SwipeRefreshLayout.setEnabled(false);
                    //  设置显示数据异常
                    CommonUtils.showLongToast(error);
                }
            });
        }
    }

    @Override
    protected void initView() {
        SwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        linearLayoutManager = new LinearLayoutManager(mContext);
        fragmentRecyclerViewNewGoods.setLayoutManager(linearLayoutManager);
        fragmentRecyclerViewNewGoods.setHasFixedSize(true);
        fragmentRecyclerViewNewGoods.setAdapter(CartAdapter);
    }

    private void sumPrice() {
        int sumPrice = 0;
        int rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                sumPrice = getPrice(c.getGoods().getCurrencyPrice());
                rankPrice = getPrice(c.getGoods().getRankPrice());
            }
            SumPrice.setText("合计：￥=" + Double.valueOf(sumPrice));
            RankPrice.setText("节省：￥=" + Double.valueOf(sumPrice - rankPrice));
        } else {
            SumPrice.setText("合计：￥=0");
            RankPrice.setText("节省：￥=0");
        }
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @OnClick(R.id.Charge)
    public void onClick() {
        MFGT.gotoOrderActivity(mContext);
    }

}
