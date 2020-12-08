package ph.homecredit.recruitment.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ph.homecredit.recruitment.data.WeatherRepository
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather
import ph.homecredit.recruitment.util.convertIdsListToOpenWeatherGroupParam

class WeatherListViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()
    private val compositeDisposable = CompositeDisposable()
    private val weathersLiveData = MutableLiveData<List<CityWeather>>()
    private val errorLiveData = MutableLiveData<String>()

    val cityWeathers
        get() = weathersLiveData
    val error
        get() = errorLiveData

    fun getCitiesWeather(idsList: MutableList<String>) {
        compositeDisposable
            .add(
                weatherRepository.getWeatherByGroup(convertIdsListToOpenWeatherGroupParam(idsList))
                    .subscribe({
                        if (it.isSuccessful) {
                            weathersLiveData.value = it.body()?.list

                        }
                    }, {
                        error.value = it.message
                    })
            )
    }

    fun getFavoriteCities(): LiveData<List<FavoriteCityWeather>>? {
        return weatherRepository.getFavoriteCities()
    }
}