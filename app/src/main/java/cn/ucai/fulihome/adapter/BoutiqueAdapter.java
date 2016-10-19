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
import cn.ucai.fulihome.view.FooterHolder;


/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> List) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mList.addAll(List);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new BoutiqueHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoutiqueHolder boutiqueHolder = (BoutiqueHolder) holder;
        BoutiqueBean boutiqueBean = mList.get(position);
        ImageLoader.downloadImg(mContext,((BoutiqueHolder) holder).ivBoutique,boutiqueBean.getImageurl());
        boutiqueHolder.BoutiqueTitle.setText(boutiqueBean.getTitle());
        boutiqueHolder.BoutiqueGoodsName.setText(boutiqueBean.getName());
        boutiqueHolder.BoutiqueDetails.setText(boutiqueBean.getDescription());


    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() ;
    }


    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
        }
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
