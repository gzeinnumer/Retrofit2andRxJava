package com.gzeinnumer.retrofit2andrxjava.network;



import com.gzeinnumer.retrofit2andrxjava.model.ResponseNews;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    //?country=us&apiKey=e5430ac2a413408aaafdf60bfa27a874
    @GET("top-headlines")
    Observable<ResponseNews> getBerita(@Query("country") String country,
                                       @Query("apiKey") String apiKey);
}
