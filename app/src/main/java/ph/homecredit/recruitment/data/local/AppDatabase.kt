package ph.homecredit.recruitment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather

@Database(entities = [FavoriteCityWeather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "WeatherApp.db"
        var INSTANCE: AppDatabase? = null
    }

    abstract fun weatherDao(): WeatherDao

}