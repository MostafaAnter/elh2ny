package com.elh2ny.rest;

import com.elh2ny.model.advicesResponceModel.AdviceResponce;
import com.elh2ny.model.articlesResponseModel.ArticlesResponse;
import com.elh2ny.model.contactUsResponseModel.ContactResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by mostafa_anter on 1/1/17.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("articles")
    Observable<ArticlesResponse> getArticles(@Field("token") String token, @Field("page") String page);

    @FormUrlEncoded
    @POST("articles")
    Observable<ContactResponse> contactUs(@Field("token") String token, @Field("title") String title,
                                          @Field("subject") String subject, @Field("email") String email,
                                          @Field("msg") String msg);

    @FormUrlEncoded
    @POST("advices")
    Observable<AdviceResponce> getAdvices(@Field("token") String token, @Field("page") String page);
}
