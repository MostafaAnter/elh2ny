package com.elh2ny.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elh2ny.R;
import com.elh2ny.model.SpinnerModel;
import com.elh2ny.model.articlesResponseModel.ArticlesResponse;
import com.elh2ny.model.articlesResponseModel.Datum;
import com.elh2ny.model.oneArticleResponse.OneArticleResponse;
import com.elh2ny.model.townResponse.TownsResponseModel;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
import com.elh2ny.utility.SweetDialogHelper;
import com.elh2ny.utility.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private String id;

    private static Subscription subscription4;
    private ApiInterface apiService;


    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_details);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null && !getIntent().getExtras().getString("id", "")
                .isEmpty())
            id = getIntent().getStringExtra("id");

        // get apiService
        apiService = ApiClient.getClient().create(ApiInterface.class);

        article = (Datum) getIntent().getExtras().get("item_data");
        if (article == null && id != null){
            // replace with load item
            getArticle();
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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.bainy_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            finish();
        }
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



        byline.setText(Util.manipulateDateFormat(article.getupdated_at()));

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

    private void getArticle(){
        final SweetDialogHelper sdh = new SweetDialogHelper(this);

        Observable<OneArticleResponse> articleObservable =
                apiService.getArticle(Constants.TOKEN, id);

        subscription4 = articleObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OneArticleResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OneArticleResponse oneArticleResponse) {
                        try {
                            article = oneArticleResponse.getArticle();
                            if (article != null)
                                bindData();
                        } catch (Exception e) {
                            e.printStackTrace();
                            sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                        }
                        subscription4.unsubscribe();
                    }
                });
    }
}
