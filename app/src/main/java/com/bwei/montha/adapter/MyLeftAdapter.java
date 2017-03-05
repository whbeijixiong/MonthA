package com.bwei.montha.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.montha.App;
import com.bwei.montha.R;
import com.bwei.montha.bean.Bean;

import java.util.List;

/**
 * 作    者 ： 文欢
 * 时    间 ： 2017/3/5.
 * 描    述 ：
 * 修改时间 ：
 */

public class MyLeftAdapter extends RecyclerView.Adapter<MyLeftAdapter.MyViewHolder> {
    private Context context;
    List<Bean.RsBean> dataList;
    onItemClickListenter onItemClickListenter;

    public MyLeftAdapter(Context context) {
        this.context = context;
    }
    //封装接口
    public void setOnItemClickListenter(onItemClickListenter onItemClickListenter){
        this.onItemClickListenter = onItemClickListenter;
    }
    //设置数据的方法
    public void setData(List<Bean.RsBean> dataList){
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.left_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.left_nameTv.setText(dataList.get(position).getDirName());
        if(App.LEFT_POSITION==position){
            //设置背景颜色和字体颜色
            holder.left_nameTv.setTextColor(context.getResources().getColor(R.color.text_check_color));
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.check_bac));
        }else{
            holder.left_nameTv.setTextColor(context.getResources().getColor(R.color.text_normal_color));
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        //设置监听事件
        if(onItemClickListenter!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取当前点击到的条目的索引
                    int position1 = holder.getLayoutPosition();
                    onItemClickListenter.setOnItemClickListenter(holder.itemView,position1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView left_nameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            left_nameTv = (TextView) itemView.findViewById(R.id.left_nameTv);
        }
    }
    //接口
    public interface onItemClickListenter{
        void setOnItemClickListenter(View view,int position);
    }
}
