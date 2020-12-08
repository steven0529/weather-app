package ph.homecredit.recruitment.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.data.WeatherRepository
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather

class WeatherDetailViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()
    private val compositeDisposable = CompositeDisposable()
    private val weatherLiveData = MutableLiveData<CityWeather>()
    private val errorLiveData = MutableLiveData<String>()

    val cityWeather
        get() = weatherLiveData
    val error
        get() = errorLiveData


    fun getCityWeather(id: String) {
        compositeDisposable
            .add(
                weatherRepository.getWeather(id)
                    .subscribe({
                        if (it.isSuccessful) {
                            weatherLiveData.value = it.body()
                        }
                    }, {
                        error.value = it.message
                    })
            )
    }

    fun isCityFavorite(cityId: Int): LiveData<FavoriteCityWeather>? {
        return weatherRepository.isCityFavorite(cityId)
    }

    fun toggleFavorite(cityId: Int, isFave: Boolean) {
        if (isFave) {
            weatherRepository.saveFavorite(cityId)
        } else {
            weatherRepository.removeFavorite(cityId)
        }
    }
}