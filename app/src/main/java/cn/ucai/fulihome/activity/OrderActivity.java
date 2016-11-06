package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.CartBean;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.utils.ResultUtils;
import cn.ucai.fulihome.view.DisplayUtils;

public class OrderActivity extends BaseActivity implements PaymentHandler{

    @BindView(R.id.LoginBack)
    ImageView LoginBack;
    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.Phone)
    EditText UserPhone;
    @BindView(R.id.Street)
    EditText UserStreet;

    OrderActivity mContext;
    String cartid = "";
    User user = null;
    ArrayList<CartBean> mlist = null;
    String[] ids = null;
    int rankPrice = 0;
    @BindView(R.id.buyingPrice)
    TextView buyingPrice;
    private static String URL = "http://218.244.151.190/demo/charge";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        mContext = this;

        ButterKnife.bind(this);
        mlist = new ArrayList<>();
        super.onCreate(savedInstanceState);
        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;

        // 设置要使用的支付方式
        /*PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb"});
        //设置是否支持外卡支付， true：支持， false：不支持， 默认不支持外卡
        PingppOne.SUPPORT_FOREIGN_CARD = true;
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //设置APP_ID和PUBLISHABLE_KEY(应用快捷支付需要)
        PingppOne.APP_ID = "Ping++ App ID";
        PingppOne.PUBLISHABLE_KEY = "Ping++ Publishable Key";
        //是否开启日志
        PingppLog.DEBUG = true;*/
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, "支付页面");
    }

    @Override
    protected void initData() {
        cartid = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiHomeApplication.getUser();
        if (cartid == null || cartid.equals("") || user == null) {
            finish();
        }
        ids = cartid.split(",");

        getOrderList();
    }

    private void getOrderList() {
        NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("public void onSuccess(String s)"+s);
                ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                L.e("public void onSuccess(String s)"+list);
                if (list == null || list.size() == 0) {
                    L.e("if (list == null || list.size() == 0) ");
                    finish();
                } else {
                    mlist.addAll(list);
                    sumPrice();
                    L.e("mlist.addAll(list);||sumPrice(); ");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrice() {
        rankPrice = 0;
        if (mlist != null && mlist.size() > 0) {
            for (CartBean c : mlist) {
                for (String id : ids) {
                    if (id.equals(String.valueOf(c.getId()))) {
                        rankPrice += getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    }
                }
            }
        }
        buyingPrice.setText("合计：￥"+Double.valueOf(rankPrice));
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.ToBuying)
    public void Buying() {
        User user = FuLiHomeApplication.getUser();
        if (user == null) {
            finish();
        } else {
            String username = UserName.getText().toString();
            if (username == null) {
                CommonUtils.showShortToast(R.string.username_no_empty);
                UserName.requestFocus();
                return;
            }
            String Phone = UserPhone.getText().toString();
            if (Phone == null) {
                CommonUtils.showShortToast(R.string.Phone_no_empty);
                UserPhone.requestFocus();
                return;
            }
            if (Phone.matches("[\\d]{11}")) {
                CommonUtils.showShortToast(R.string.Charg_Phone_error);
                UserPhone.requestFocus();
                return;
            }
            String Street = UserStreet.getText().toString();
            if (Street == null) {
                CommonUtils.showShortToast(R.string.strrent_no_empty);
                UserStreet.requestFocus();
                return;
            }
        }
        gotoStatement();


    }

    private void gotoStatement() {
            // 产生个订单号
            String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                    .format(new Date());

            // 计算总金额（以分为单位）
            // 构建账单json对象
            JSONObject bill = new JSONObject();

            // 自定义的额外信息 选填
            JSONObject extras = new JSONObject();
            try {
                extras.put("extra1", "extra1");
                extras.put("extra2", "extra2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                bill.put("order_no", orderNo);
                bill.put("amount", rankPrice);
                bill.put("extras", extras);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //壹收款: 创建支付通道的对话框
            PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);


    }

    @OnClick(R.id.LoginBack)
    public void onClick() {
        MFGT.finish(mContext);
    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {

            // result：支付结果信息
            // code：支付结果码
            //-2:用户自定义错误
            //-1：失败
            // 0：取消
            // 1：成功
            // 2:应用内快捷支付支付结果

            if (data.getExtras().getInt("code") != 2) {
                PingppLog.d(data.getExtras().getString("result") + "  " + data.getExtras().getInt("code"));
            } else {
                String result = data.getStringExtra("result");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    if (resultJson.has("error")) {
                        result = resultJson.optJSONObject("error").toString();
                    } else if (resultJson.has("success")) {
                        result = resultJson.optJSONObject("success").toString();
                    }
                    PingppLog.d("result::" + result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
