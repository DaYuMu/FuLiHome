package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.BoutiqueBean;
import cn.ucai.fulihome.utils.ImageLoader;


/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
                                               //  为了避免布局的不居中
            holder = new NewGoodsAdapter.FooterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer, null));
        } else {
            holder = new BoutiqueHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == I.TYPE_FOOTER) {
            NewGoodsAdapter.FooterHolder footer = (NewGoodsAdapter.FooterHolder) holder;
            footer.tvFooter.setText(getFooterText());
        } else {
            BoutiqueHolder boutiqueHolder = (BoutiqueHolder) holder;
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext,((BoutiqueHolder) holder).ivBoutique,boutiqueBean.getImageurl());
            boutiqueHolder.BoutiqueTitle.setText(boutiqueBean.getTitle());
            boutiqueHolder.BoutiqueGoodsName.setText(boutiqueBean.getName());
            boutiqueHolder.BoutiqueDetails.setText(boutiqueBean.getDescription());

        }
    }

    private int getFooterText() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }



    //  继承ViewHolder，设置为一般方法。
    class BoutiqueHolder extends ViewHolder{
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.BoutiqueTitle)
        TextView BoutiqueTitle;
        @BindView(R.id.BoutiqueGoodsName)
        TextView BoutiqueGoodsName;
        @BindView(R.id.BoutiqueDetails)
        TextView BoutiqueDetails;
        @BindView(R.id.BoutiqueRelativeLayout)
        RelativeLayout BoutiqueRelativeLayout;

        BoutiqueHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
