package com.need.unknown.component.helper;

import com.need.unknown.component.model.DataUser;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface EndPointService {

    @POST("auth/register-phone")
    @FormUrlEncoded
    Observable<DataUser> checkUserExist(@Field("phone_number") String phoneNumber);
}
