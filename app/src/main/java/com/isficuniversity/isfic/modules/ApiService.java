package com.isficuniversity.isfic.modules;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Requête pour récupérer les articles liés à un mot-clé (ex : "Mali")
    @GET("v2/top-headlines") // Adapte l'endpoint selon l'API utilisée
    Call<ApiResponse> getArticles(
            @Query("q") String keywords, // Mots-clés de recherche
            @Query("apiKey") String apiKey // Clé API
    );
}
