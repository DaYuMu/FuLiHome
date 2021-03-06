package cn.ucai.fulihome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.CartBean;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.bean.MessageBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.MFGT;


public class CartAdapter extends Adapter {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context mContext, ArrayList<CartBean> List) {
        this.mContext = mContext;
        List = mList;
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
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, cartViewHolder.ivCartGood, goods.getGoodsThumb());
            cartViewHolder.CartGoodTitle.setText(goods.getGoodsName());
            cartViewHolder.CartGoodsPrice.setText(goods.getCurrencyPrice());
        }
        cartViewHolder.CartGoodsCount.setText(String.valueOf(cartBean.getCount()));
//        cartViewHolder.Check   设置为不选择状态
        cartViewHolder.CheckBox.setChecked(cartBean.isChecked());
        cartViewHolder.CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
            }
        });
        cartViewHolder.AddCart.setTag(position);
    }

    @OnClick({R.id.ivCartGood, R.id.CartGoodTitle, R.id.CartGoodsPrice})
    //  实现点击商品图片，商品名称，商品价格进入商品详情页面的点击事件。
    public void gotoDetail(View view) {
//        final int posotion = AddCart.getTag();
//        CartBean cartBean = mList.get(posotion);
//        MFGT.gotoNewGoodsDetailsActivity(mContext,cartBean.getGoodsId());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }



    class CartViewHolder extends ViewHolder {
        @BindView(R.id.CheckBox)
        android.widget.CheckBox CheckBox;
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
        @OnClick({R.id.AddCart, R.id.DeleteCart})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.AddCart:
                    //  添加购物车里的商品，改变合计价钱与节省价钱
                    final int position = (int) AddCart.getTag();
                    CartBean cart = mList.get(position);
                    NetDao.updataCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null&&result.isSuccess()) {
                                mList.get(position).setCount(mList.get(position).getCount()+1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                CartGoodsCount.setText(String.valueOf(mList.get(position).getCount()));
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                    break;
                case R.id.DeleteCart:
                    final int delposition = (int) AddCart.getTag();
                    CartBean delcart = mList.get(delposition);
                    //  删除购物车里的商品，改变合计价钱与节省价钱，物品最少只能为1。
                    if (delcart.getCount() > 1) {
                        NetDao.updataCart(mContext, delcart.getId(), delcart.getCount()-1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                    mList.get(delposition).setCount(mList.get(delposition).getCount() - 1);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                    CartGoodsCount.setText(String.valueOf(mList.get(delposition).getCount()));
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    } else {
                        NetDao.deleteCart(mContext, delcart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                    mList.remove(delposition);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                    break;
            }
        }

        @OnClick(R.id.layout_cart_detail)
        public void carttodetail() {
            final int delposition = (int) AddCart.getTag();
            CartBean delcart = mList.get(delposition);
            MFGT.gotoNewGoodsDetailsActivity(mContext,delcart.getGoodsId());
        }
    }



}
