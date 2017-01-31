package com.elh2ny.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
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
import com.elh2ny.model.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

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
        Toast.makeText(this, "SearchResult", Toast.LENGTH_SHORT).show();
    }

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
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }

                if (position > 0) {
                    // doSome things
                  //  schoolID = selectedItem.getId();
                } else {
                   // schoolID = null;
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
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
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
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
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
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
