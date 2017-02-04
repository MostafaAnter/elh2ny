package com.elh2ny.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.elh2ny.R;
import com.elh2ny.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallUsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnFocusChangeListener,
        View.OnClickListener{

    @Nullable
    @BindView(R2.id.card_view1)
    CardView cardView1;

    @Nullable
    @BindView(R2.id.linear1)
    LinearLayout linear1;
    @Nullable
    @BindView(R2.id.linear2)
    LinearLayout linear2;
    @Nullable
    @BindView(R2.id.linear3)
    LinearLayout linear3;
    @Nullable
    @BindView(R2.id.linear4)
    LinearLayout linear4;

    @Nullable
    @BindView(R2.id.linear7)
    LinearLayout linear7;

    @Nullable
    @BindView(R2.id.editText1)
    EditText editText1;
    @Nullable
    @BindView(R2.id.editText2)
    EditText editText2;
    @Nullable
    @BindView(R2.id.editText3)
    EditText editText3;
    @Nullable
    @BindView(R2.id.editText4)
    EditText editText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);

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

        // set focuse
        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);
        editText3.setOnFocusChangeListener(this);
        editText4.setOnFocusChangeListener(this);

        // on click
        cardView1.setOnClickListener(this);
        linear7.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view instanceof EditText) {
            if (b) {
                switch (view.getId()) {
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText3:
                        linear3.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText4:
                        linear4.setBackgroundResource(R.drawable.border_shape_blue);
                        break;

                }
            } else {
                switch (view.getId()) {
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText3:
                        linear3.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText4:
                        linear4.setBackgroundResource(R.drawable.border_shape_gray);
                        break;

                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear7:
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "010895000999"));
                startActivity(callIntent);
                break;
        }
    }
}
