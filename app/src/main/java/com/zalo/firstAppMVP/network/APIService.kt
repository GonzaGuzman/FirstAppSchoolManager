package com.zalo.firstAppMVP.network


import com.zalo.firstAppMVP.network.models.Schools
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {

    /*@GET("/schools")
    fun getSchools(@Query ("city") city: String): Single<Schools>
    */

    @GET("/schools")
    fun getSchools(): Single<Schools>

}

object APIServiceImpl {

    fun getSchools(): Single<Schools> {
        return service.getSchools()
    }

    private val service: APIService by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://626d430b5267c14d5679547b.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(APIService::class.java)
    }

}