package ph.homecredit.recruitment.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_weather_list.*
import ph.homecredit.recruitment.R
import ph.homecredit.recruitment.ui.details.WeatherDetailFragment.Companion.KEY_CITY_ID
import ph.homecredit.recruitment.ui.list.adapter.WeatherAdapter

class WeatherListFragment : Fragment(), WeatherOnClickListener {

    private val viewModel by viewModels<WeatherListViewModel>()

    private lateinit var adapter: WeatherAdapter
    private val cityIds = mutableListOf("1701668", "3067696", "1835848")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srl_weather.isRefreshing = true
        viewModel.getCitiesWeather(cityIds)
        rv_weatherlist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = WeatherAdapter(this)
        rv_weatherlist.adapter = adapter
        srl_weather.setOnRefreshListener {
            viewModel.getCitiesWeather(cityIds)
        }
        bindViewModels()
    }

    private fun bindViewModels() {
        viewModel.cityWeathers.observe(viewLifecycleOwner, Observer {
            srl_weather.isRefreshing = false
            adapter.submitList(it)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            srl_weather.isRefreshing = false
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.getFavoriteCities()?.observe(viewLifecycleOwner, Observer {
            adapter.setFavoriteCities(it)
        })
    }

    override fun onClickWeather(id: String) {
        findNavController().navigate(
            R.id.action_weatherList_to_weatherDetails, bundleOf(
                KEY_CITY_ID to id
            )
        )
    }
}