package ph.homecredit.recruitment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favoriteCityWeather")
data class FavoriteCityWeather(
    @PrimaryKey
    val id: Int? = null
)

@Serializable
data class GroupCityWeather(
    val cnt: Int,
    val list: List<CityWeather>
)

@Serializable
data class CityWeather(
    val coord: Coordinates? = null,
    val weather: List<Weather>? = null,
    val main: MainWeather,
    val visibility: Int? = null,
    val wind: Wind? = null,
    val clouds: Cloud? = null,
    val dt: Long? = null,
    val sys: SysWeatherInfo? = null,
    val id: Int? = null,
    val name: String? = null,
    var isFavorite: Boolean? = false
) {

    fun getTemperatureCategory(): TemperatureCategory {
        return if (main.temp < 1) {
            TemperatureCategory.Freezing
        } else if (main.temp > 0 && main.temp <= 15) {
            TemperatureCategory.Cold
        } else if (main.temp > 15 && main.temp <= 30) {
            TemperatureCategory.Warm
        } else {
            TemperatureCategory.Hot
        }
    }
}

@Serializable
data class Coordinates(
    val lon: Double,
    val lat: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class MainWeather(
    val temp: Double,
    val feels_like: Double? = null,
    val temp_min: Double? = null,
    val temp_max: Double? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int
)

@Serializable
data class Cloud(
    val all: Int
)

@Serializable
data class SysWeatherInfo(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

enum class TemperatureCategory {
    Freezing,
    Cold,
    Warm,
    Hot
}
