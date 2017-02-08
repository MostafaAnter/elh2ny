package com.elh2ny.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.adapter.SpinnerCustomAdapter;
import com.elh2ny.model.NetworkEvent;
import com.elh2ny.model.ResponseOfPrices.PriceResponse;
import com.elh2ny.model.SpinnerModel;
import com.elh2ny.model.cityResponce.CityResponseModel;
import com.elh2ny.model.townResponse.TownsResponseModel;
import com.elh2ny.model.typesResponseModel.TypesResponseModel;
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
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.nav_view)
    NavigationView navigationView;
    @Nullable
    @BindView(R2.id.spinner1)
    Spinner spinner1;
    @Nullable
    @BindView(R2.id.spinner2)
    Spinner spinner2;
    @Nullable
    @BindView(R2.id.spinner3)
    Spinner spinner3;
    @Nullable
    @BindView(R2.id.spinner4)
    Spinner spinner4;
    @Optional
    @OnClick(R2.id.card_view1)
    public void goToSearchResultPage(CardView view) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(Constants.CITY, city);
        intent.putExtra(Constants.AREA, area);
        intent.putExtra(Constants.PRICE, price);
        intent.putExtra(Constants.TYPE, type);
        startActivity(intent);
    }

    private static Subscription subscription1, subscription2,
    subscription3, subscription4;
    private ApiInterface apiService;

    private String city = "", area = "", price = "", type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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

        // set spinner
        List<SpinnerModel> models = new ArrayList<>();
        models.add(new SpinnerModel("أختر محافظة", ""));
        populateSpinner1(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("أختر منطقة", ""));
        populateSpinner2(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("السعر", ""));
        populateSpinner3(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("النوع", ""));
        populateSpinner4(models);

        getCitiesPriceTypies();

    }

    private void populateSpinner1(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
                    city = selectedItem.getId();
                    area = "";
                    getArea();

                    Log.d("debug_mostafa", selectedItem.getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner2(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(spinnerArrayAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
                    area = selectedItem.getId();
                    Log.d("debug_mostafa", selectedItem.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner3(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner3.setAdapter(spinnerArrayAdapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
                    price = selectedItem.getPrice();
                    Log.d("debug_mostafa", selectedItem.getPrice());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner4(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner4.setAdapter(spinnerArrayAdapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
                    type = selectedItem.getId();
                    Log.d("debug_mostafa", selectedItem.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        if (subscription2 != null) subscription2.unsubscribe();
        if (subscription3 != null) subscription3.unsubscribe();
        if (subscription4 != null) subscription4.unsubscribe();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkEvent event) {
        getCitiesPriceTypies();
    }

    private void getCitiesPriceTypies(){
        SweetDialogHelper sdh = new SweetDialogHelper(this);
        // check if is online or not
        if (Util.isOnline(this) && apiService != null){
            Observable<CityResponseModel> cityObservable =
                    apiService.getCites(Constants.TOKEN);
            Observable<PriceResponse> priceObservable =
                    apiService.getPrices(Constants.TOKEN);
            Observable<TypesResponseModel> typesObservable =
                    apiService.getTypes(Constants.TOKEN);

            subscription1 = cityObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cityResponseModel -> {
                        try {
                            List<SpinnerModel> models = new ArrayList<>();
                            models.add(new SpinnerModel("أختر محافظة", ""));
                            models.addAll(1, cityResponseModel.getCities());
                            populateSpinner1(models);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }
                        subscription1.unsubscribe();
                    });


            subscription2 = priceObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(priceResponse -> {
                        try {
                            List<SpinnerModel> models = new ArrayList<>();
                            models = new ArrayList<>();
                            models.add(new SpinnerModel("السعر", ""));
                            models.addAll(1, priceResponse.getPrices());
                            populateSpinner3(models);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }
                        subscription2.unsubscribe();
                    });


            subscription3 = typesObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( typesResponseModel -> {
                        try {
                            List<SpinnerModel> models = new ArrayList<>();
                            models = new ArrayList<>();
                            models.add(new SpinnerModel("النوع", ""));
                            models.addAll(1, typesResponseModel.getTypes());
                            populateSpinner4(models);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }
                        subscription3.unsubscribe();
                    });


        }else {
            sdh.showErrorMessage("عفواً", "لا يوجد اتصال بالانترنت");
        }
    }

    private void getArea(){
        SweetDialogHelper sdh = new SweetDialogHelper(this);

        Observable<TownsResponseModel> townsObservable =
                apiService.getTowns(Constants.TOKEN, city);
        Log.d("debug_mostafa", city);

        subscription4 = townsObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(townsResponseModel -> {
                    try {
                        List<SpinnerModel> models = new ArrayList<>();
                        models = new ArrayList<>();
                        models.add(new SpinnerModel("أختر منطقة", ""));
                        models.addAll(1, townsResponseModel.getTowns());
                        populateSpinner2(models);
                    } catch (Exception e) {
                        e.printStackTrace();
                        sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                    }
                    subscription4.unsubscribe();
                });
    }
}
