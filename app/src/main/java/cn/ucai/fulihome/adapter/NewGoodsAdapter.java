package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.utils.ImageLoader;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class NewGoodsAdapter extends Adapter {

    Context mContext;
    ArrayList<NewGoodsBean> mlist;

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mlist = new ArrayList<>();
        mlist.addAll(list);
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

        } else {
            NewGoodsHolder newGoodsHolder = (NewGoodsHolder) holder;
            NewGoodsBean newGoodsBean = mlist.get(position);
            //  set image
            ImageLoader.downloadImg(mContext,newGoodsHolder.ivNewGoods,newGoodsBean.getGoodsThumb());
            newGoodsHolder.tvNewGoodsName.setText(newGoodsBean.getGoodsName());
            newGoodsHolder.tvNewGoodsPrive.setText(newGoodsBean.getCurrencyPrice());

        }

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

    //  用ButterKnife实现两个item布局的ViewHolder，
    static class FooterHolder extends ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class NewGoodsHolder extends ViewHolder{
        @BindView(R.id.ivNewGoods)
        ImageView ivNewGoods;
        @BindView(R.id.tvNewGoodsName)
        TextView tvNewGoodsName;
        @BindView(R.id.tvNewGoodsPrive)
        TextView tvNewGoodsPrive;

        NewGoodsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
