package com.bwei.montha.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.montha.MyGridView;
import com.bwei.montha.R;
import com.bwei.montha.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 作    者 ： 文欢
 * 时    间 ： 2017/3/5.
 * 描    述 ：
 * 修改时间 ：
 */

public class MyRightAdapter extends RecyclerView.Adapter<MyRightAdapter.MyViewHolder_Right>{
    private Context context;

    List<Bean.RsBean.ChildrenBeanX> childData;

    public MyRightAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Bean.RsBean.ChildrenBeanX> childData) {
        this.childData = childData;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder_Right onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.right_item_layout, parent, false);
        MyViewHolder_Right myViewHolder = new MyViewHolder_Right(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder_Right holder, int position) {
        holder.brandTv.setText(childData.get(position).getDirName());
        //取出小集合 设置到GridView上面
        List<Bean.RsBean.ChildrenBeanX.ChildrenBean> childList = childData.get(position).getChildren();
        MyAdapter myAdapter = new MyAdapter(context, childList);
        holder.gridView.setAdapter(myAdapter);
    }


    @Override
    public int getItemCount() {
        return childData == null ? 0 : childData.size();
    }

    class MyViewHolder_Right extends RecyclerView.ViewHolder {

        private final MyGridView gridView;
        private final TextView brandTv;

        public MyViewHolder_Right(View itemView) {
            super(itemView);
            brandTv = (TextView) itemView.findViewById(R.id.brandTv);
            gridView = (MyGridView) itemView.findViewById(R.id.gridView);
        }
    }

    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Bean.RsBean.ChildrenBeanX.ChildrenBean> childList;
        private LinearLayout item;

        public MyAdapter(Context context, List<Bean.RsBean.ChildrenBeanX.ChildrenBean> childList) {
            this.context = context;
            this.childList = childList;
        }

        @Override
        public int getCount() {
            return childList == null ? 0 : childList.size();
        }

        @Override
        public Object getItem(int i) {
            return childList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
                holder = new ViewHolder();
                holder.image = (ImageView) view.findViewById(R.id.item_image);
                holder.nameTv = (TextView) view.findViewById(R.id.nameTv);
                holder.item = (LinearLayout) view.findViewById(R.id.item);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
            imageLoader.displayImage(childList.get(i).getImgApp(),holder.image);
            holder.nameTv.setText(childList.get(i).getDirName());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,childList.get(i).getDirName(),Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        class ViewHolder {
            ImageView image;
            TextView nameTv;
            LinearLayout item;
        }
    }


}
