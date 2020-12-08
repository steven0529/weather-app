package ph.homecredit.recruitment.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weather.view.*
import ph.homecredit.recruitment.R
import ph.homecredit.recruitment.domain.model.CityWeather
import ph.homecredit.recruitment.domain.model.FavoriteCityWeather
import ph.homecredit.recruitment.domain.model.TemperatureCategory
import ph.homecredit.recruitment.ui.list.WeatherOnClickListener
import ph.homecredit.recruitment.util.toOneDecimalPlace

class WeatherAdapter(val weatherOnClickListener: WeatherOnClickListener) :
    ListAdapter<CityWeather, WeatherAdapter.ViewHolder>(WeatherDiffUtil()) {

    var favoriteCitiesList = listOf<FavoriteCityWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setFavoriteCities(faveCities: List<FavoriteCityWeather>) {
        favoriteCitiesList = faveCities
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityWeather) = with(itemView) {
            itemView.tv_city_name.text = item.name
            itemView.tv_temp.text =
                context.getString(R.string.temp).format(item.main.temp.toOneDecimalPlace())
            itemView.tv_weather_desc.text = item.weather?.first()?.description
            itemView.setOnClickListener {
                weatherOnClickListener.onClickWeather(item.id.toString())
            }

            var inFaveCity = false
            for (faveCity in favoriteCitiesList) {
                if (faveCity.id == item.id)
                    inFaveCity = true
            }
            if (inFaveCity)
                itemView.iv_favorite.setImageResource(R.drawable.ic_heart_full)
            else
                itemView.iv_favorite.setImageResource(R.drawable.ic_heart)

            val tempBgColor = when (item.getTemperatureCategory()) {
                TemperatureCategory.Freezing -> {
                    R.color.freezing
                }
                TemperatureCategory.Cold -> {
                    R.color.cold
                }
                TemperatureCategory.Warm -> {
                    R.color.warm
                }
                else -> {
                    R.color.hot
                }
            }
            itemView.rl_container.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    tempBgColor
                )
            )
        }
    }
}

class WeatherDiffUtil : DiffUtil.ItemCallback<CityWeather>() {

    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.id == newItem.id && oldItem.main.temp == newItem.main.temp
                && oldItem.main.temp_min == newItem.main.temp_min
                && oldItem.main.temp_max == newItem.main.temp_max
                && oldItem.main.humidity == newItem.main.humidity
                && oldItem.main.pressure == newItem.main.pressure
                && oldItem.name == newItem.name
                && oldItem.clouds?.all == newItem.clouds?.all
                && oldItem.isFavorite == newItem.isFavorite
    }

}