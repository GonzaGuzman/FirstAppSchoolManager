package com.zalo.firstAppMVP.network


import com.zalo.firstAppMVP.network.headersInterceptor.HeadersInterceptor
import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface APIService {

    /*@GET("/schools")
    fun getSchools(@Query ("city") city: String): Single<Schools>
    */

    //  @Headers("Accept: application/json")

    @GET("/schools")
    fun getSchools(): Single<Schools>

    //  @Headers("Accept: application/json;charset=utf-8")
    @POST("/addSchool")
    fun postNewSchool(@Body school: School): Single<ResponseNetwork>

}

object APIServiceImpl {

    fun getSchools(): Single<Schools> {
        return service.getSchools()
    }

    fun newSchool(school: School): Single<ResponseNetwork> {
        return service.postNewSchool(school)
    }

    private val service: APIService by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://626d430b5267c14d5679547b.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getClient())
            .build()
            .create(APIService::class.java)
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HeadersInterceptor())
            .build()


}