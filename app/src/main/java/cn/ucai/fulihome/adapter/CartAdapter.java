package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.CartBean;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.utils.ImageLoader;


public class CartAdapter extends Adapter {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context mContext, ArrayList<CartBean> List) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mList.addAll(List);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartViewHolder cartViewHolder = (CartViewHolder) holder;
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, cartViewHolder.ivCartGood, goods.getGoodsThumb());
            cartViewHolder.CartGoodTitle.setText(goods.getGoodsName());
            cartViewHolder.CartGoodsPrice.setText(goods.getCurrencyPrice());
        }
        cartViewHolder.CartGoodsCount.setTag(cartBean.getCount());
//        cartViewHolder.Check   设置为不选择状态
//        cartViewHolder
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CartViewHolder extends ViewHolder {
        @BindView(R.id.CheckBox)
        ImageView CheckBox;
        @BindView(R.id.ivCartGood)
        ImageView ivCartGood;
        @BindView(R.id.CartGoodTitle)
        TextView CartGoodTitle;
        @BindView(R.id.AddCart)
        ImageView AddCart;
        @BindView(R.id.CartGoodsCount)
        TextView CartGoodsCount;
        @BindView(R.id.DeleteCart)
        ImageView DeleteCart;
        @BindView(R.id.LayoutCartGoods)
        LinearLayout LayoutCartGoods;
        @BindView(R.id.CartGoodsPrice)
        TextView CartGoodsPrice;
        @BindView(R.id.BoutiqueRelativeLayout)
        LinearLayout BoutiqueRelativeLayout;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @OnClick({R.id.CheckBox, R.id.AddCart, R.id.DeleteCart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CheckBox:
                // 点击圆圈改变购物车里商品的选中状态，改变合计价钱
                break;
            case R.id.AddCart:
                //  添加购物车里的商品，改变合计价钱与节省价钱
                break;
            case R.id.DeleteCart:
                //  删除购物车里的商品，改变合计价钱与节省价钱，物品最少只能为1。
                break;
        }
    }
}
