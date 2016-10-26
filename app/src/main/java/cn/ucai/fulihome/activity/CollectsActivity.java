package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.adapter.CollectsAdapter;
import cn.ucai.fulihome.bean.CollectBean;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.view.DisplayUtils;
import cn.ucai.fulihome.view.SpaceItemDecoration;

public class CollectsActivity extends BaseActivity {
    private static final String TAG = CollectsActivity.class.getSimpleName();

    CollectsActivity mContext;
    CollectsAdapter collectAdaptr;
    ArrayList<CollectBean> newgoodslist;
    GridLayoutManager gridLayoutManager;
    int pageId=1;

    User user =null;
    @BindView(R.id.tvNewGoods)
    TextView tvNewGoods;
    @BindView(R.id.fragmentRecyclerViewNewGoods)
    RecyclerView fragmentRecyclerViewNewGoods;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.e(TAG+"CollectsActivity.start");
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        mContext = this;
        newgoodslist = new ArrayList<>();
        collectAdaptr = new CollectsAdapter(mContext,newgoodslist);
        L.e(TAG+"CollectsActivity.starting");
//        initView();   initData();   setListener();
        super.onCreate(savedInstanceState);
        L.e(TAG+"CollectsActivity.end");
    }

    @Override
    protected void initView() {
        L.e(TAG+"CollectsActivity.initView().start");
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.collect_title));
        SwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        fragmentRecyclerViewNewGoods.setLayoutManager(gridLayoutManager);
        fragmentRecyclerViewNewGoods.setHasFixedSize(true);
        fragmentRecyclerViewNewGoods.setAdapter(collectAdaptr);
        fragmentRecyclerViewNewGoods.addItemDecoration(new SpaceItemDecoration(16));
        L.e(TAG+"CollectsActivity.initView().end");

    }

    @Override
    protected void setListener() {
        L.e(TAG+"CollectsActivity.setListener().start");
        setPullDownListener();
        setPullUpListener();
        L.e(TAG+"CollectsActivity.initView().end");
    }

    /**
     * 上拉加载的方法
     */
    private void setPullUpListener() {
        fragmentRecyclerViewNewGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == collectAdaptr.getItemCount() - 1
                        && collectAdaptr.isMore()) {
                    pageId++;
                    downloadCollects(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = gridLayoutManager.findFirstVisibleItemPosition();
                SwipeRefreshLayout.setEnabled(firstPosition == 0);
            }
        });
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
                pageId = 1;
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });
    }

    @Override
    protected void initData() {
        user = FuLiHomeApplication.getUser();
        L.e(TAG+"FuLiHomeApplication.getUser()="+user);
        if (user == null) {
            finish();
        }
        downloadCollects(I.ACTION_DOWNLOAD);
        L.e(TAG+"downloadCollects.action");
    }

    private void downloadCollects(final int action) {
        NetDao.downloadCollects(mContext,user.getMuserName(), pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                //  设置上拉加载效果
                collectAdaptr.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    L.e(TAG+"downloadCollects.result"+result);
                    L.e(TAG+"downloadCollects.list"+list);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        collectAdaptr.initData(list);
                    } else {
                        collectAdaptr.addDate(list);
                    }
                    // 判断本次下载的数据有几个，，注意别混乱
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        collectAdaptr.setMore(false);
                    }
                } else {
                    collectAdaptr.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG+"下载用户收藏商品信息错误");
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                //  设置显示数据异常
                CommonUtils.showLongToast(error);
                collectAdaptr.setMore(false);
                L.e("main" + error);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
