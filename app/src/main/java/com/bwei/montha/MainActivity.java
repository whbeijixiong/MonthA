package com.bwei.montha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bwei.montha.adapter.MyLeftAdapter;
import com.bwei.montha.adapter.MyRightAdapter;
import com.bwei.montha.bean.Bean;
import com.bwei.montha.okhttp.OkHttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mleft;
    private RecyclerView mRight;
    private String path = "http://mock.eoapi.cn/success/4q69ckcRaBdxhdHySqp2Mnxdju5Z8Yr4";
    private List<Bean.RsBean> dataList = new ArrayList<>();

    private MyLeftAdapter myLeftAdapter;
    private MyRightAdapter myRightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //网络请求
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.getJson(path,new HttpData());
        //运用接口回调 取出右边的数据
        myLeftAdapter.setOnItemClickListenter(new MyLeftAdapter.onItemClickListenter() {
            @Override
            public void setOnItemClickListenter(View view, int position) {
                //让右边的数据与左边点击到的数据进行同步
                App.LEFT_POSITION = position;
                myLeftAdapter.notifyDataSetChanged();
                List<Bean.RsBean.ChildrenBeanX> children = dataList.get(App.LEFT_POSITION).getChildren();
                myRightAdapter.setData(children);
            }
        });
    }

    class HttpData extends OkHttpUtil.HttpCallBack{
        @Override
        public void onSusscess(String data) {
            Gson gson = new Gson();
            Bean bean = gson.fromJson(data, Bean.class);
            List<Bean.RsBean> rs = bean.getRs();
            App.LEFT_POSITION = 0;
            dataList.clear();
            dataList.addAll(rs);
            myLeftAdapter.setData(dataList);
            List<Bean.RsBean.ChildrenBeanX> children = dataList.get(App.LEFT_POSITION).getChildren();
            myRightAdapter.setData(children);
        }
    }

//    private void getData(final List<Bean.RsBean> rs) {
//        // 创建一个线性布局管理器
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        // 设置布局管理器
//
//        recycleViewLeft.setLayoutManager(layoutManager);
//        myLeftAdapter = new MyLeftAdapter(MainActivity.this);
//        recycleViewLeft.setAdapter(myLeftAdapter);
//        //运用接口回调 取出右边的数据
//        myLeftAdapter.setOnItemClickListenter(new MyLeftAdapter.onItemClickListenter() {
//            @Override
//            public void setOnItemClickListenter(View view, int position) {
//                //让右边的数据与左边点击到的数据进行同步
//                App.LEFT_POSITION = position;
//                myLeftAdapter.notifyDataSetChanged();
//                List<Bean.RsBean.ChildrenBeanX> children = rs.get(App.LEFT_POSITION).getChildren();
//                myRightAdapter.setData(children);
//            }
//        });
//
//    }

    private void initView() {
        LinearLayout activity_main =  (LinearLayout) findViewById(R.id.activity_main);
        mleft = (RecyclerView) findViewById(R.id.recycler_left);
        mRight = (RecyclerView) findViewById(R.id.recycler_right);
        //设置管理者
        mleft.setLayoutManager(new LinearLayoutManager(this));
        mRight.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        myLeftAdapter = new MyLeftAdapter(this);
        myRightAdapter = new MyRightAdapter(this);
        //设置适配器
        mleft.setAdapter(myLeftAdapter);
        mRight.setAdapter(myRightAdapter);
    }

}
