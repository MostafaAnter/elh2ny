package com.elh2ny.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.adapter.ArticlesAdapter;
import com.elh2ny.model.articlesResponseModel.Datum;
import com.elh2ny.utility.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener{

    @Nullable
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R2.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Nullable
    @BindView(R2.id.noData)
    LinearLayout noDataView;


    protected ArticlesAdapter mAdapter;
    protected StaggeredGridLayoutManager sglm;
    protected List<Datum> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        ButterKnife.bind(this);
        setToolbar();
        setRecyclerViewAndSwipe();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeFontOfNavigation();


        onRefresh();

    }

    private void setRecyclerViewAndSwipe() {
        // manipulateRecyclerView & Swipe to refresh
        mDataSet = new ArrayList<>();
        sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        mAdapter = new ArticlesAdapter(this, mDataSet);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        //noinspection ResourceAsColor
        mSwipeRefresh.setColorScheme(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        if (!mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(true);
        }
        if (!Util.isOnline(this) && mAdapter.getItemCount() == 0 && mDataSet.size() == 0) {
            mSwipeRefresh.setRefreshing(false);
            noDataView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "لا يوجد أتصال بالأنترنت", Toast.LENGTH_LONG).show();
        } else if (!Util.isOnline(this)) {
            mSwipeRefresh.setRefreshing(false);
            Toast.makeText(this, "لا يوجد أتصال بالأنترنت", Toast.LENGTH_LONG).show();
        }
    }
}
