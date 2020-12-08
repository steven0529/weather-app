package ph.homecredit.recruitment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM favoriteCityWeather")
    fun getFavoriteCities(): LiveData<List<FavoriteCityWeather>>

    @Query("SELECT * FROM favoriteCityWeather WHERE id=:id ")
    fun isCityFavorite(id: Int): LiveData<FavoriteCityWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavorite(favCityWeather: FavoriteCityWeather):Long

    @Delete
    fun removeFavorite(favCityWeather: FavoriteCityWeather)

}