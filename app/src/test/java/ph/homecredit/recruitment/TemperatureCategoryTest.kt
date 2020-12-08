package ph.homecredit.recruitment

import org.junit.Assert
import org.junit.Test
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.domain.model.MainWeather
import ph.homecredit.recruitment.domain.model.TemperatureCategory

class TemperatureCategoryTest {

    @Test
    fun isTempFreezing() {
        Assert.assertEquals(
            CityWeather(main = MainWeather(temp = 0.0)).getTemperatureCategory(),
            TemperatureCategory.Freezing
        )
    }

    @Test
    fun isTempCold() {
        Assert.assertEquals(
            CityWeather(main = MainWeather(temp = 15.0)).getTemperatureCategory(),
            TemperatureCategory.Cold
        )
    }

    @Test
    fun isTempWarm() {
        Assert.assertEquals(
            CityWeather(main = MainWeather(temp = 16.0)).getTemperatureCategory(),
            TemperatureCategory.Warm
        )
    }

    @Test
    fun isTempHot() {
        Assert.assertEquals(
            CityWeather(main = MainWeather(temp = 31.0)).getTemperatureCategory(),
            TemperatureCategory.Hot
        )
    }

}