package com.elh2ny.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.text1)
    TextView textView1;

    @BindView(R2.id.text2)
    TextView textView2;

    @BindView(R2.id.text3)
    TextView textView3;

    @BindView(R2.id.text4)
    TextView textView4;

    @BindView(R2.id.linear1)
    LinearLayout linearLayout1;

    @BindView(R2.id.linear2)
    LinearLayout linearLayout2;

    @BindView(R2.id.linear3)
    LinearLayout linearLayout3;

    @BindView(R2.id.linear4)
    LinearLayout linearLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);


        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView4);
    }

    public void setToolbar() {
        Util.manipulateToolbar(this, toolbar, 0, null, true);
        ImageView backButton = (ImageView) toolbar.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linear1:
                startActivity(new Intent(this, SearchActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.linear2:
                startActivity(new Intent(this, ArticlesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.linear3:
                startActivity(new Intent(this, AdvicesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.linear4:
                startActivity(new Intent(this, CallUsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}
