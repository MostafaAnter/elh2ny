package com.elh2ny.rest;

import com.elh2ny.model.ResponseOfPrices.PriceResponse;
import com.elh2ny.model.advicesResponceModel.AdviceResponce;
import com.elh2ny.model.articlesResponseModel.ArticlesResponse;
import com.elh2ny.model.cityResponce.CityResponseModel;
import com.elh2ny.model.contactUsResponseModel.ContactResponse;
import com.elh2ny.model.townResponse.TownsResponseModel;
import com.elh2ny.model.typesResponseModel.TypesResponseModel;

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
    @POST("contact")
    Observable<ContactResponse> contactUs(@Field("token") String token, @Field("title") String title,
                                          @Field("subject") String subject, @Field("email") String email,
                                          @Field("msg") String msg);

    @FormUrlEncoded
    @POST("advices")
    Observable<AdviceResponce> getAdvices(@Field("token") String token, @Field("page") String page);

    @FormUrlEncoded
    @POST("cities")
    Observable<CityResponseModel> getCites(@Field("token") String token);

    @FormUrlEncoded
    @POST("towns")
    Observable<TownsResponseModel> getTowns(@Field("token") String token, @Field("city") String city);

    @FormUrlEncoded
    @POST("prices")
    Observable<PriceResponse> getPrices(@Field("token") String token);

    @FormUrlEncoded
    @POST("types")
    Observable<TypesResponseModel> getTypes(@Field("token") String token);
}
