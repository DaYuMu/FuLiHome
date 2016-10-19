package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Albums;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.FlowIndicator;
import cn.ucai.fulihome.view.SlideAutoLoopView;


public class NewGoodsDetailsActivity extends BaseActivity {
    @BindView(R.id.agoPrice)
    TextView agoPrice;
    @BindView(R.id.currentPrice)
    TextView currentPrice;
    @BindView(R.id.ivTitleBack)
    ImageView ivTitleBack;
    @BindView(R.id.EnglishName)
    TextView EnglishName;
    @BindView(R.id.ChinaName)
    TextView ChinaName;
    @BindView(R.id.SlideImage)
    SlideAutoLoopView SlideImage;
    @BindView(R.id.FlowImage)
    FlowIndicator FlowImage;
    @BindView(R.id.NewGoodsDetails)
    TextView NewGoodsDetails;

    GoodsDetailsBean detailsBean;
   int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_goods_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        position = intent.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetails(this, position, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDateils(result);
                }else {
                    finish();
                }
            }

            private void showGoodsDateils(GoodsDetailsBean result) {
                agoPrice.setText(result.getPromotePrice());
                currentPrice.setText(result.getCurrencyPrice());
                EnglishName.setText(result.getGoodsEnglishName());
                ChinaName.setText(result.getGoodsName());
                NewGoodsDetails.setText(result.getGoodsBrief());
                L.e("main","商品的详情加载完成");
                SlideImage.startPlayLoop(FlowImage,getAlbumImgUrl(result),getAlbumCount(result));

            }

            private int getAlbumCount(GoodsDetailsBean detailsBean) {
                if (detailsBean.getProperties() != null && detailsBean.getProperties().length > 0) {
                    return detailsBean.getProperties()[0].getAlbums().length;
                }
                return 0;
            }

            private String[] getAlbumImgUrl(GoodsDetailsBean detailsBean) {
                String[] url = new String[]{};
                if (detailsBean.getProperties()!= null && detailsBean.getProperties().length > 0) {
                    Albums[] albums = detailsBean.getProperties()[0].getAlbums();
                    url = new String[albums.length];
                    for (int i=0;i<albums.length;i++) {
                        url[i] = albums[i].getImgUrl();
                    }
                }
                return url;
            }

            @Override
            public void onError(String error) {
                    finish();
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.ivTitleBack)
    public void onBackClick() {
        MFGT.finish(this);
    }

}
