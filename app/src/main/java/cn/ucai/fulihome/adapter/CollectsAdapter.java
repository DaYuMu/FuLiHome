package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.CollectBean;
import cn.ucai.fulihome.bean.MessageBean;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.FooterHolder;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class CollectsAdapter extends Adapter {

    Context mContext;
    ArrayList<CollectBean> mlist;
    boolean isMore;//  用于判断是否还有更多数据。
    int sorBy = I.SORT_BY_ADDTIME_DESC;

    public CollectsAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = context;
        mlist = new ArrayList<>();
        mlist.addAll(list);
    }

    public int getSorBy() {
        return sorBy;
    }

    public void setSorBy(int sorBy) {
        this.sorBy = sorBy;
        notifyDataSetChanged();
    }

    //   设置isMore的get、set方法。
    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        //  刷新数据
        notifyDataSetChanged();
    }

    public void addDate(ArrayList<CollectBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        //  由getItemViewType得到的类型不同，设置不同的holder
        if (viewType == I.TYPE_FOOTER) {
            //  空指针异常时，要看方法，，传入的参数，，等等。是否都实例化了。
            holder = new FooterHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CollectHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.tvFooter.setText(getFooterText());
        } else {
            CollectHolder newGoodsHolder = (CollectHolder) holder;
            CollectBean newGoodsBean = mlist.get(position);
            //  set image
            ImageLoader.downloadImg(mContext, newGoodsHolder.ivNewGoods, newGoodsBean.getGoodsThumb());
            newGoodsHolder.tvNewGoodsName.setText(newGoodsBean.getGoodsName());
            //                                     注意传过来的goodsid
            newGoodsHolder.layoutCollect.setTag(newGoodsBean);//传position
        }

    }

    private int getFooterText() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() + 1 : 1;
    }

    //  覆写getItemViewType，得到返回的类型
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mlist != null) {
            mlist.clear();
            mlist.addAll(list);
            notifyDataSetChanged();
        }
    }

    @OnClick(R.id.ivdelete)
    public void onClick() {
    }


    class CollectHolder extends ViewHolder {
        @BindView(R.id.ivNewGoods)
        ImageView ivNewGoods;
        @BindView(R.id.tvNewGoodsName)
        TextView tvNewGoodsName;
        @BindView(R.id.ivdelete)
        ImageView ivdelete;
        @BindView(R.id.layoutCollect)
        RelativeLayout layoutCollect;

        CollectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layoutCollect)
        public void GTGoodsDatailsActivity() {
            {
                CollectBean goods = (CollectBean) layoutCollect.getTag();
                MFGT.gotoNewGoodsDetailsActivity(mContext, goods.getGoodsId());
            }

        }
        @OnClick(R.id.ivdelete)
        public void deleteCollects() {
            final CollectBean goods = (CollectBean) layoutCollect.getTag();
            User user = FuLiHomeApplication.getUser();
            if (user != null) {
                NetDao.deleteCollects(mContext, user.getMuserName(), goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mlist.remove(goods);
                            notifyDataSetChanged();
                        } else {
                            CommonUtils.showShortToast(result!=null?result.getMsg()
                                    :mContext.getResources().getString(R.string.delete_collect_fail));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                });
            }

        }
    }
}
