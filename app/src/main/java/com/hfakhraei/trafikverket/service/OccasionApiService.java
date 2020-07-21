package com.hfakhraei.trafikverket.service;

import com.hfakhraei.trafikverket.BuildConfig;
import com.hfakhraei.trafikverket.R;
import com.hfakhraei.trafikverket.TrafikVerketApiService;
import com.hfakhraei.trafikverket.dto.request.BookingSession;
import com.hfakhraei.trafikverket.dto.request.OccasionBundleQuery;
import com.hfakhraei.trafikverket.dto.request.OccasionRequest;
import com.hfakhraei.trafikverket.dto.response.OccasionResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OccasionApiService {
    private static OccasionApiService occasionApiService = new OccasionApiService();

    private OccasionApiService() {
    }

    public static OccasionApiService getInstance() {
        return occasionApiService;
    }

    public OccasionResponse callApi(Integer locationId) throws IOException {
        return callApi(getRequest(locationId));
    }

    private synchronized OccasionResponse callApi(OccasionRequest request) throws IOException {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrafikVerketApiService service = retrofit.create(TrafikVerketApiService.class);

        Call<OccasionResponse> call = service.listFirstAvailableTime(request);
        return call.execute().body();
    }

    private OccasionRequest getRequest(Integer locationId) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        return OccasionRequest.builder()
                .bookingSession(BookingSession.builder()
                        .socialSecurityNumber("19790627-3771")
                        .licenceId(5)
                        .bookingModeId(0)
                        .ignoreDebt(false)
                        .ignoreBookingHindrance(false)
                        .rescheduleTypeId(0)
                        .paymentIsActive(false)
                        .excludeExaminationCategories(new ArrayList<String>())
                        .build())
                .occasionBundleQuery(OccasionBundleQuery.builder()
                        .startDate(date)
                        .locationId(locationId)
                        .languageId(2)
                        .tachographTypeId(1)
                        .occasionChoiceId(1)
                        .examinationTypeId(3)
                        .build())
                .build();
    }

}
