package ph.homecredit.recruitment.ui

import android.app.Application
import androidx.room.Room
import ph.homecredit.recruitment.data.local.AppDatabase
import ph.homecredit.recruitment.data.local.AppDatabase.Companion.DATABASE_NAME

class WeatherBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.INSTANCE = Room.databaseBuilder(
            this,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }
}