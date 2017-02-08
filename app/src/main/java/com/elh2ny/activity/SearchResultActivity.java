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
import com.elh2ny.adapter.RoomAdapter;
import com.elh2ny.model.NetworkEvent;
import com.elh2ny.model.roomsResponseModel.RoomModel;
import com.elh2ny.model.roomsResponseModel.RoomResponse;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
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

public class SearchResultActivity extends BaseActivity
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


    // for recycler view
    private static final String TAG = "ProviderChatsFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    protected LayoutManagerType mCurrentLayoutManagerType;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected RoomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<RoomModel> mDataset;

    private static Subscription subscription1;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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

        mAdapter = new RoomAdapter(this, mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


        // get data
        addItems();
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

    private void addItems(){
        SweetDialogHelper sdh = new SweetDialogHelper(this);
        // check if is online or not
        if (Util.isOnline(this) && apiService != null){
            progressBar.setVisibility(View.VISIBLE);
            Observable<RoomResponse> roomObservable =
                    apiService.getIncs(Constants.TOKEN, getIntent().getStringExtra(Constants.CITY),
                            getIntent().getStringExtra(Constants.AREA),
                            getIntent().getStringExtra(Constants.PRICE),
                            getIntent().getStringExtra(Constants.TYPE));
            subscription1 = roomObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(roomResponse -> {
                        try {
                            mDataset.addAll(roomResponse.getIncs());
                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            noDataView.setVisibility(View.GONE);
                            if (mDataset.size() == 0)
                                noDataView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
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
        addItems();
    }

}
