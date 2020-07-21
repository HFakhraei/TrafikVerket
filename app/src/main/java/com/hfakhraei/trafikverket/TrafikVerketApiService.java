package com.hfakhraei.trafikverket;

import com.hfakhraei.trafikverket.dto.request.OccasionRequest;
import com.hfakhraei.trafikverket.dto.response.OccasionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TrafikVerketApiService {
    @POST("Boka/occasion-bundles")
    @Headers("Content-Type: application/json")
    Call<OccasionResponse> listFirstAvailableTime(@Body OccasionRequest request);
}
