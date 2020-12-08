package ph.homecredit.recruitment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_weather_details.*
import ph.homecredit.recruitment.R
import ph.homecredit.recruitment.util.toOneDecimalPlace
import kotlin.math.roundToInt

class WeatherDetailFragment : Fragment() {

    companion object {
        const val KEY_CITY_ID = "cityId"
    }

    private val viewModel by viewModels<WeatherDetailViewModel>()
    private var cityId: String? = null
    private var isCityFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityId = arguments?.getString(KEY_CITY_ID, "").toString()
        bindViewModels()
        viewModel.getCityWeather(cityId ?: "")
        srl_weather.isRefreshing = true
        iv_favorite.setOnClickListener {
            isCityFavorite = if (isCityFavorite) {
                viewModel.toggleFavorite(cityId?.toInt() ?: 0, false)
                false
            } else {
                viewModel.toggleFavorite(cityId?.toInt() ?: 0, true)
                true
            }
            renderFavorite()
        }
        srl_weather.setOnRefreshListener {
            viewModel.getCityWeather(cityId ?: "")
        }
    }

    private fun bindViewModels() {
        viewModel.cityWeather.observe(viewLifecycleOwner, Observer {
            srl_weather.isRefreshing = false
            tv_city_name.text = it.name
            tv_min_max_temp.text =
                getString(R.string.temp_high_low).format(
                    it.main.temp_max?.roundToInt(),
                    it.main.temp_min?.roundToInt()
                )
            tv_weather_desc.text = it.weather?.first()?.description
            tv_temp.text = requireContext().getString(R.string.temp).format(
                it.main.temp.toOneDecimalPlace()
            )
            iv_favorite.visibility = View.VISIBLE
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            srl_weather.isRefreshing = false
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isCityFavorite(cityId?.toInt() ?: 0)?.observe(viewLifecycleOwner, Observer {
            isCityFavorite = it != null
            renderFavorite()
        })
    }

    private fun renderFavorite() {
        if (isCityFavorite) {
            iv_favorite.setImageResource(R.drawable.ic_heart_full)
        } else {
            iv_favorite.setImageResource(R.drawable.ic_heart)
        }
    }
}