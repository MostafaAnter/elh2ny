package com.elh2ny.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.model.SpinnerModel;
import com.elh2ny.model.contactUsResponseModel.ContactResponse;
import com.elh2ny.model.sendRoomOrderResponse.OrderResponse;
import com.elh2ny.model.townResponse.TownsResponseModel;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
import com.elh2ny.utility.SweetDialogHelper;
import com.elh2ny.utility.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @Nullable
    @BindView(R2.id.text15)
    TextView textView15;

    private SweetDialogHelper sdh;
    private String title, subject, email, msg;
    private static Subscription subscription1, subscription2;
    private ApiInterface apiService;

    private String phoneNumber;

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

        // get apiService
        apiService = ApiClient.getClient().create(ApiInterface.class);

        // set focuse
        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);
        editText3.setOnFocusChangeListener(this);
        editText4.setOnFocusChangeListener(this);

        // on click
        cardView1.setOnClickListener(this);
        linear7.setOnClickListener(this);

        sdh = new SweetDialogHelper(this);

        getPhoneNumber();
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
                if (phoneNumber != null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    startActivity(callIntent);
                }else {
                    sdh.showErrorMessage("أنتظر..", "حتى يتم تحميل رقم الهاتف");
                }
                break;
            case R.id.card_view1:

                if (Util.isOnline(this)){
                    if (checkData()) {
                        sdh.showMaterialProgress("تحميل..");
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Observable<ContactResponse> contactObservable =
                                apiService.contactUs(Constants.TOKEN, title, subject, email, msg);
                        subscription1 = contactObservable
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ContactResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        sdh.showErrorMessage("عفواً", "قم بغلق الصفحة وأعادة فتحها");
                                    }

                                    @Override
                                    public void onNext(ContactResponse contactResponse) {
                                        try {
                                            sdh.dismissDialog();
                                            if (contactResponse.getError().equalsIgnoreCase("false")){
                                                sdh.showSuccessfulMessage("تم", "تم الارسال بنجاح", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                        finish();
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            sdh.dismissDialog();
                                            e.printStackTrace();
                                        }
                                        subscription1.unsubscribe();
                                    }
                                });
                    }
                }else {
                    sdh.showErrorMessage("عفواً", "لا يوجد اتصال بالانترنت");
                }
                break;
        }
    }

    private boolean checkData(){
        title = editText1.getText().toString().trim();
        subject = editText3.getText().toString().trim();
        email = editText2.getText().toString().trim();
        msg = editText4.getText().toString().trim();


        if (title == null || title.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "أدخل أسمك");
            return false;
        }
        if (subject == null || subject.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "أدخل عنوان الرسالة");
            return false;
        }
        if (email == null || email.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "أدخل البريد الإلكترونى الصحيح");
            return false;
        }
        if (msg == null || msg.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "أدخل الرسالة");
            return false;
        }
        return true;
    }

    private void getPhoneNumber(){
        Observable<Response<ResponseBody>> phoneObservable =
                apiService.getPhoneNumber();

       subscription2 = phoneObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        try {
                            textView15.append(responseBodyResponse.body().string());
                            phoneNumber = responseBodyResponse.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        subscription2.unsubscribe();
                    }
                });
    }
}
