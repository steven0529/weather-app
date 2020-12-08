package ph.homecredit.recruitment.data.remote

import io.reactivex.Single
import ph.homecredit.recruitment.BuildConfig
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.domain.model.GroupCityWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("id") cityId: String,
        @Query("appId") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): Single<Response<CityWeather>>

    @GET("/data/2.5/group")
    fun getWeatherGroup(
        @Query(value = "id", encoded = true) cityIds: String,
        @Query("appId") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): Single<Response<GroupCityWeather>>

}