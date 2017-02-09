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
import com.elh2ny.model.NetworkEvent;
import com.elh2ny.model.articlesResponseModel.ArticlesResponse;
import com.elh2ny.model.articlesResponseModel.Datum;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
import com.elh2ny.utility.EndlessRecyclerViewScrollListener;
import com.elh2ny.utility.SweetDialogHelper;
import com.elh2ny.utility.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private static Subscription subscription1;
    private ApiInterface apiService;
    private EndlessRecyclerViewScrollListener scrollListener;

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

        // get apiService
        apiService = ApiClient.getClient().create(ApiInterface.class);

        onRefresh();

    }

    private void setRecyclerViewAndSwipe() {
        // manipulateRecyclerView & Swipe to refresh
        mDataSet = new ArrayList<>();
        sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(sglm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);
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
        if (mDataSet.size() > 0){
            mDataSet.clear();
            mAdapter.notifyDataSetChanged();
        }
        loadNextDataFromApi(1);
    }

    private void loadNextDataFromApi(int offset){
        SweetDialogHelper sdh = new SweetDialogHelper(this);
        // check if is online or not
        if (Util.isOnline(this) && apiService != null){
            Observable<ArticlesResponse> articlesObservable =
                    apiService.getArticles(Constants.TOKEN, String.valueOf(offset));
            subscription1 = articlesObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(articlesResponse -> {
                        try {
                            mDataSet.addAll(mDataSet.size(), articlesResponse.getArticles().getData());
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefresh.setRefreshing(false);
                            noDataView.setVisibility(View.GONE);
                            if (mDataSet.size() == 0)
                                noDataView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }
                    });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        if (subscription1 != null) subscription1.unsubscribe();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkEvent event) {
        loadNextDataFromApi(1);
    }
}
