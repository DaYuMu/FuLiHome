package cn.ucai.fulihome.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.R;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class FooterHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvFooter)
    public
    TextView tvFooter;

    public FooterHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
