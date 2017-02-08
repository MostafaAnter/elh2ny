package com.elh2ny.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.model.sendRoomOrderResponse.OrderResponse;
import com.elh2ny.rest.ApiClient;
import com.elh2ny.rest.ApiInterface;
import com.elh2ny.utility.Constants;
import com.elh2ny.utility.SweetDialogHelper;
import com.elh2ny.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mostafa on 07/07/16.
 */
public class RoomRegisterationDialog extends DialogFragment implements View.OnClickListener,
        View.OnFocusChangeListener{
    int mNum;

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
    @BindView(R2.id.linear5)
    LinearLayout linear5;

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
    @BindView(R2.id.editText5)
    EditText editText5;

    @Nullable
    @BindView(R2.id.text1)
    TextView text1;

    private SweetDialogHelper sdh;
    private String title, dis, point, tel, id, mail;
    private static Subscription subscription1;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static RoomRegisterationDialog newInstance(int num) {
        RoomRegisterationDialog f = new RoomRegisterationDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.room_registeration_dialog, container, false);
        ButterKnife.bind(this, v);
        text1.setText(getArguments().getString("name"));

        // set focuse
        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);
        editText3.setOnFocusChangeListener(this);
        editText4.setOnFocusChangeListener(this);
        editText5.setOnFocusChangeListener(this);

        cardView1.setOnClickListener(this);

        sdh = new SweetDialogHelper(getActivity());

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view1:
                if (Util.isOnline(getActivity())){
                    if (checkData()) {
                        sdh.showMaterialProgress("تحميل..");
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Observable<OrderResponse> orderObservable =
                                apiService.getOrder(Constants.TOKEN, title, dis, point, tel, id, mail);
                        subscription1 = orderObservable
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(orderResponse -> {
                                    try {
                                        sdh.dismissDialog();
                                        if (orderResponse.getError().equalsIgnoreCase("false")){
                                            sdh.showSuccessfulMessage("تم", "الحجز تم بنجاح", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    dismiss();
                                                }
                                            });
                                        }
                                    } catch (Exception e) {
                                        sdh.dismissDialog();
                                        e.printStackTrace();
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
        dis = editText2.getText().toString().trim();
        point = editText3.getText().toString().trim();
        tel = editText5.getText().toString().trim();
        id = getArguments().getString("id");
        mail = editText4.getText().toString().trim();

        if (title == null || title.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أدخل أسم الطفل");
            return false;
        }
        if (dis == null || dis.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أدخل التشخيص");
            return false;
        }
        if (point == null || point.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أدخل جهة التحويل");
            return false;
        }
        if (tel == null || tel.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أدخل رقم الهاتف");
            return false;
        }
        if (id == null || id.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أغلق الصفحة وأعد المحاولة");
            return false;
        }
        if (mail == null || mail.trim().isEmpty()) {
            new SweetDialogHelper(getActivity()).showErrorMessage("خطأ", "أدخل البريد الإلكترونى");
            return false;
        }
        return true;
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
                    case R.id.editText5:
                        linear5.setBackgroundResource(R.drawable.border_shape_blue);
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
                    case R.id.editText5:
                        linear5.setBackgroundResource(R.drawable.border_shape_gray);
                        break;


                }
            }
        }
    }
}