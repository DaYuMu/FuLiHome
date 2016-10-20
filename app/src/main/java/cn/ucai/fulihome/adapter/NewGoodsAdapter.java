package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.FooterHolder;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class NewGoodsAdapter extends Adapter {

    Context mContext;
    ArrayList<NewGoodsBean> mlist;
    boolean isMore;//  用于判断是否还有更多数据。
    int sorBy = I.SORT_BY_ADDTIME_DESC;

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mlist = new ArrayList<>();
        mlist.addAll(list);
    }

    public int getSorBy() {
        return sorBy;
    }

    public void setSorBy(int sorBy) {
        this.sorBy = sorBy;
        sortBy();
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

    public void addDate(ArrayList<NewGoodsBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        //  由getItemViewType得到的类型不同，设置不同的holder
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new NewGoodsHolder(View.inflate(mContext, R.layout.item_newgoods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.tvFooter.setText(getFooterText());
        } else {
            NewGoodsHolder newGoodsHolder = (NewGoodsHolder) holder;
            NewGoodsBean newGoodsBean = mlist.get(position);
            //  set image
            ImageLoader.downloadImg(mContext,newGoodsHolder.ivNewGoods,newGoodsBean.getGoodsThumb());
            newGoodsHolder.tvNewGoodsName.setText(newGoodsBean.getGoodsName());
            newGoodsHolder.tvNewGoodsPrive.setText(newGoodsBean.getCurrencyPrice());
            //                                     注意传过来的goodsid
            newGoodsHolder.layout.setTag(newGoodsBean.getGoodsId());//传position
        }

    }

    private int getFooterText() {
        return isMore?R.string.load_more:R.string.no_more;
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

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mlist != null) {
            mlist.clear();
            mlist.addAll(list);
            notifyDataSetChanged();
        }
    }


     class NewGoodsHolder extends ViewHolder{
        @BindView(R.id.ivNewGoods)
        ImageView ivNewGoods;
        @BindView(R.id.tvNewGoodsName)
        TextView tvNewGoodsName;
        @BindView(R.id.tvNewGoodsPrive)
        TextView tvNewGoodsPrive;
         @BindView(R.id.layout)
         LinearLayout layout;

        NewGoodsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout)
         public  void  GTGoodsDatailsActivity(){
            int position= (int) layout.getTag();
            MFGT.gotoNewGoodsDetailsActivity(mContext,position);
        }

    }

    private void sortBy() {
        Collections.sort(mlist, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right) {
                int result = 0;
                switch (sorBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (Long.valueOf(left.getAddTime()) - Long.valueOf(right.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (Long.valueOf(right.getAddTime()) - Long.valueOf(left.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(left.getCurrencyPrice()) - getPrice(right.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(right.getCurrencyPrice()) - getPrice(left.getCurrencyPrice());
                        break;
                }
                return result;
            }

            private int getPrice(String price) {
                price = price.substring(price.indexOf("￥") + 1);
                return Integer.valueOf(price);
            }
        });
    }
}
