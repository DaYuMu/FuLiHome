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
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.adapter.NewGoodsAdapter;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ConvertUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.SpaceItemDecoration;

public class CategoryChildActivity extends BaseActivity {

    CategoryChildActivity context;
    NewGoodsAdapter newGoodsAdapter;
    ArrayList<NewGoodsBean> newgoodslist;
    int pageId = 1;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.NewGoodsTitle)
    TextView NewGoodsTitle;
    @BindView(R.id.tvNewGoods)
    TextView tvNewGoods;
    @BindView(R.id.fragmentRecyclerViewNewGoods)
    RecyclerView fragmentRecyclerViewNewGoods;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    int catId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = this;
        newgoodslist = new ArrayList<>();
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
        newGoodsAdapter = new NewGoodsAdapter(context, newgoodslist);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        SwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gridLayoutManager = new GridLayoutManager(context, I.COLUM_NUM);
        fragmentRecyclerViewNewGoods.setLayoutManager(gridLayoutManager);
        fragmentRecyclerViewNewGoods.setHasFixedSize(true);
        fragmentRecyclerViewNewGoods.setAdapter(newGoodsAdapter);
        fragmentRecyclerViewNewGoods.addItemDecoration(new SpaceItemDecoration(16));
    }


    @Override
    protected void setListener() {

        setPullDownListener();
        setPullUpListener();
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
                        && lastPosition == newGoodsAdapter.getItemCount() - 1
                        && newGoodsAdapter.isMore()) {
                    pageId++;
                    downloadCategoryGoods(I.ACTION_PULL_UP);
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
                downloadCategoryGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    @Override
    protected void initData() {
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
    }

    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(context, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                //  设置上拉加载效果
                newGoodsAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        newGoodsAdapter.initData(list);
                    } else {
                        newGoodsAdapter.addDate(list);
                    }
                    // 判断本次下载的数据有几个，，注意别混乱
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        newGoodsAdapter.setMore(false);
                    }
                } else {
                    newGoodsAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                //  设置刷新中··· 为不再刷新，不可见状态
                tvNewGoods.setVisibility(View.GONE);
                SwipeRefreshLayout.setEnabled(false);
                //  设置显示数据异常
                CommonUtils.showLongToast(error);
                newGoodsAdapter.setMore(false);
                L.e("main" + error);
            }

        });
    }


    @OnClick(R.id.ivTitleBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
