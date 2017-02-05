package com.elh2ny.rest;

import com.elh2ny.model.ArticlesResponseModel.ArticlesResponse;

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
}
