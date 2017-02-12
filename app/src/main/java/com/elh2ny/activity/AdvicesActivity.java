package com.elh2ny.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.adapter.AdviceAdapter;
import com.elh2ny.model.NetworkEvent;
import com.elh2ny.model.advicesResponceModel.AdviceModel;
import com.elh2ny.model.advicesResponceModel.AdviceResponce;
import com.elh2ny.model.roomsResponseModel.RoomResponse;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
import com.elh2ny.utility.EndlessRecyclerViewScrollListener;
import com.elh2ny.utility.SweetDialogHelper;
import com.elh2ny.utility.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AdvicesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable
    @BindView(R2.id.noData)
    LinearLayout noDataView;
    @Nullable
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @Nullable
    @BindView(R2.id.progressBar1)
    ProgressBar progressBar;


    private EndlessRecyclerViewScrollListener scrollListener;

    // for recycler view
    private static final String TAG = "ProviderChatsFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    protected LayoutManagerType mCurrentLayoutManagerType;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected AdviceAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<AdviceModel> mDataset;

    private static Subscription subscription1;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advices);

        ButterKnife.bind(this);
        setToolbar();

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
        // set recyclerView
        setRecyclerView(savedInstanceState);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void setRecyclerView(Bundle savedInstanceState) {
        // initiate mDataSet
        mDataset = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager)mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);

        mAdapter = new AdviceAdapter(this, mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


        // get data
        loadNextDataFromApi(1);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void loadNextDataFromApi(int offset){
        final SweetDialogHelper sdh = new SweetDialogHelper(this);
        // check if is online or not
        if (Util.isOnline(this) && apiService != null){
            progressBar.setVisibility(View.VISIBLE);
            Observable<AdviceResponce> adviceObservable =
                    apiService.getAdvices(Constants.TOKEN, String.valueOf(offset));
            subscription1 = adviceObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AdviceResponce>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }

                        @Override
                        public void onNext(AdviceResponce adviceResponce) {
                            try {
                                mDataset.addAll(mDataset.size(), adviceResponce.getAdvices().getData());
                                mAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                noDataView.setVisibility(View.GONE);
                                if (mDataset.size() == 0)
                                    noDataView.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                            }
                        }
                    });
        }else {
            sdh.showErrorMessage("عفواً", "لا يوجد اتصال بالانترنت");
            progressBar.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
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
