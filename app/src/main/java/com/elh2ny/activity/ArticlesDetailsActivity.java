package com.elh2ny.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elh2ny.R;
import com.elh2ny.model.articlesResponseModel.Datum;
import com.elh2ny.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.article_title)TextView title;
    @BindView(R.id.article_byline)TextView byline;
    @BindView(R.id.article_body)TextView body;
    //@BindView(R.id.adView)AdView mAdView;

    private Datum article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_details);
        ButterKnife.bind(this);
        article = (Datum) getIntent().getExtras().get("item_data");
        if (article == null){
            // replace with load item

        }else {
            bindData();
        }


        Util.manipulateToolbar(this, toolbar, 0, null, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareLink();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // manipulate ads
//        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }

    private void bindData(){

        ImageView imageView = (ImageView) collapsingToolbarLayout.findViewById(R.id.mainImage);
        // load thumbnail image :)
        Glide.with(this)
                .load("http://elh2ny.com/" + article.getImg())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(imageView);

        // set title text
        String titleText = article.getTitle().equalsIgnoreCase("null")? "":
                article.getTitle();
        title.setText(titleText);



        byline.setText(Util.manipulateDateFormat(article.getCreatedAt()));

        body.setText(Html.fromHtml(article.getContent()));


    }

    private void shareLink(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "الحقني");
            String sAux = "\n"+ article.getTitle() + "\n\n";
            sAux = sAux + article.getDescription() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}
