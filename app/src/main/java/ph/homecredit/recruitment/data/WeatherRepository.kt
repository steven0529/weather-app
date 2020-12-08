package ph.homecredit.recruitment.data

import androidx.lifecycle.LiveData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import ph.homecredit.recruitment.BuildConfig
import ph.homecredit.recruitment.data.local.AppDatabase
import ph.homecredit.recruitment.data.remote.WeatherApi
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather
import ph.homecredit.recruitment.domain.model.GroupCityWeather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class WeatherRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
        .addConverterFactory(Json {
            this.ignoreUnknownKeys = true
        }
            .asConverterFactory(MediaType.get("application/json")))
        .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        )
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    fun getWeather(id: String): Single<Response<CityWeather>> {
        return weatherApi.getWeather(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWeatherByGroup(cityIds: String): Single<Response<GroupCityWeather>> {
        return weatherApi.getWeatherGroup(cityIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveFavorite(cityId: Int) {
        GlobalScope.launch {
            AppDatabase.INSTANCE?.weatherDao()?.saveFavorite(FavoriteCityWeather(cityId))
        }
    }

    fun removeFavorite(cityId: Int) {
        GlobalScope.launch {
            AppDatabase.INSTANCE?.weatherDao()?.removeFavorite(FavoriteCityWeather(cityId))
        }
    }

    fun getFavoriteCities(): LiveData<List<FavoriteCityWeather>>? {
        return AppDatabase.INSTANCE?.weatherDao()?.getFavoriteCities()
    }

    fun isCityFavorite(cityId: Int): LiveData<FavoriteCityWeather>? {
        return AppDatabase.INSTANCE?.weatherDao()?.isCityFavorite(cityId)
    }

}
