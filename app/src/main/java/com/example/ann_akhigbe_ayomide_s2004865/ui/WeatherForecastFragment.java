package com.example.ann_akhigbe_ayomide_s2004865.ui;

import static com.example.ann_akhigbe_ayomide_s2004865.utilities.Utilities.getTempsImage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.ann_akhigbe_ayomide_s2004865.R;
import com.example.ann_akhigbe_ayomide_s2004865.databinding.FragmentWeatherForecastBinding;
import com.example.ann_akhigbe_ayomide_s2004865.model.data.WeatherData;
import com.example.ann_akhigbe_ayomide_s2004865.model.data.WeatherDataDto;
import com.example.ann_akhigbe_ayomide_s2004865.model.data.WeatherForecast;
import com.example.ann_akhigbe_ayomide_s2004865.model.data.WeatherInfo;
import com.example.ann_akhigbe_ayomide_s2004865.ui.viewmodel.WeatherViewModel;
import com.example.ann_akhigbe_ayomide_s2004865.utilities.Result;
import com.example.ann_akhigbe_ayomide_s2004865.utilities.Utilities;

import java.util.List;


// Name: Anna Akhigbe Ayomide
// StudentId: S2004865
// Programme of Study: Mobile Platform Development
public class WeatherForecastFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    public static final String LOCATION_ID = "locationId";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private WeatherData currentItem;
    private String locationId = "";

    private FragmentWeatherForecastBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        locationId = getArguments().getString(ARG_PARAM, "");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.greetingImg.setImageResource(Utilities.getGreetingsImage());
        binding.navIcon.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), LatestWeatherObservationActivity.class);
            intent.putExtra(LOCATION_ID, locationId);
            intent.putExtra(LATITUDE, currentItem.getThreeDaysForecast().get(0).getLatitude());
            intent.putExtra(LONGITUDE, currentItem.getThreeDaysForecast().get(0).getLongitude());
            startActivity(intent);
        });
        WeatherViewModel viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getWeatherForecast(locationId);
        viewModel.getThreeDaysWeatherForecastResult().observe(getViewLifecycleOwner(), weatherDataResult -> {
            binding.progress.setVisibility(View.GONE);
            if (weatherDataResult instanceof Result.Success) {
                currentItem = ((Result.Success<WeatherData>) weatherDataResult).data;
              //  binding.title.setText(currentItem.getTitle());
                binding.plainHeader.location.setText(getLastTwoStrings(currentItem.getTitle()));
               // binding.description.setText(currentItem.getDescription());
                display3DaysForecast(currentItem.getThreeDaysForecast());
                binding.navIcon.setVisibility(View.VISIBLE);
            } else {
                binding.navIcon.setVisibility(View.GONE);
            }
        });
    }

    private void display3DaysForecast(List<WeatherForecast> forecasts) {
       // binding.titleDay0.setText(forecasts.get(0).getTitle());
        // binding.descriptionDay0.setText(forecasts.get(0).getDescription());

        WeatherInfo weatherInfo = new WeatherInfo(forecasts.get(0).getTitle());
        WeatherDataDto weatherData = new WeatherDataDto(forecasts.get(0).getDescription());

        Log.d("God", forecasts.get(0).getTitle());
        binding.plainView.cloudCondition.setText(weatherInfo.getWeatherCondition());
        binding.plainView.weatherDay.setText(weatherInfo.getDay());
        binding.plainView.cloudHumidity.setText(getString(R.string.humidity_20,weatherData.getHumidity()));
        binding.plainView.minTemp.setText(getString(R.string.min_temp, weatherInfo.getMinTemperature()));
        binding.plainView.cloudImg.setImageResource(getTempsImage(weatherInfo.getWeatherCondition()));
        binding.plainView.rangeTemp.setText(getString(R.string.min_max, weatherInfo.getMinTemperature(), weatherInfo.getMaxTemperature()));

        // 2nd

        WeatherInfo weatherInfo1 = new WeatherInfo(forecasts.get(1).getTitle());
        WeatherDataDto weatherData1 = new WeatherDataDto(forecasts.get(1).getDescription());

        Log.d("God", forecasts.get(0).getTitle());
        assert binding.plainView1 != null;
        binding.plainView1.parentLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        binding.plainView1.cloudCondition.setText(weatherInfo1.getWeatherCondition());
        binding.plainView1.weatherDay.setText(weatherInfo1.getDay());
        binding.plainView1.cloudImg.setImageResource(getTempsImage(weatherInfo1.getWeatherCondition()));
        binding.plainView1.cloudHumidity.setText(getString(R.string.humidity_20,weatherData1.getHumidity()));
        binding.plainView1.minTemp.setText(getString(R.string.min_temp, weatherInfo1.getMinTemperature()));
        binding.plainView1.rangeTemp.setText(getString(R.string.min_max, weatherInfo1.getMinTemperature(), weatherInfo1.getMaxTemperature()));

        // 3rd

        assert binding.plainView2 != null;
        binding.plainView2.parentLayout.setBackgroundColor(getResources().getColor(R.color.blue_dark));
        WeatherInfo weatherInfo2 = new WeatherInfo(forecasts.get(2).getTitle());
        WeatherDataDto weatherData2 = new WeatherDataDto(forecasts.get(2).getDescription());

       //God Log.d("God", weatherInfo2.getMaxTemperature());
        Log.d("God2", forecasts.get(2).getTitle());
        binding.plainView2.cloudCondition.setText(weatherInfo2.getWeatherCondition());
        binding.plainView2.weatherDay.setText(weatherInfo2.getDay());
        binding.plainView2.cloudImg.setImageResource(getTempsImage(weatherInfo2.getWeatherCondition()));
        binding.plainView2.cloudHumidity.setText(getString(R.string.humidity_20,weatherData2.getHumidity()));
        binding.plainView2.minTemp.setText(getString(R.string.min_temp, weatherInfo2.getMinTemperature()));
        binding.plainView2.rangeTemp.setText(getString(R.string.min_max, weatherInfo2.getMinTemperature(), weatherInfo2.getMaxTemperature()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static WeatherForecastFragment newInstance(String param) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, param);
        fragment.setArguments(bundle);
        return fragment;
    }
    public static String getLastTwoStrings(String inputString) {
        String[] parts = inputString.split(" ");
        if (parts.length >= 2) {
            return parts[parts.length - 2] + " " + parts[parts.length - 1];
        } else {
            return "";
        }
    }
}