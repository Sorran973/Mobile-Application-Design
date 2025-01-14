package com.example.practice3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private final static String WEATHER_API_KEY = "";
    OkHttpClient okHttpClient;
    Button buttonSearchWeather;
    TextView textView;
    EditText editText;

    Button buttonStartService;
    Button buttonStopService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        okHttpClient = new OkHttpClient();
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextText);
        buttonSearchWeather = findViewById(R.id.buttonSearchWeather);
        buttonSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather();
            }
        });

        buttonStartService = findViewById(R.id.buttonStartService);
        buttonStartService.setOnClickListener(view -> startService(new Intent(MainActivity.this, MyServiceMediaPlayer.class)));

        buttonStopService = findViewById(R.id.buttonStopService);
        buttonStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, MyServiceMediaPlayer.class));
            }
        });
    }

    private void getWeather() {
        String city = editText.getText().toString();
        Request request = new Request.Builder().url(makeUrl(city)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String jsonStr = response.body().string();
                            textView.setText(getInfoFromJSON(jsonStr));
                            editText.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private String getInfoFromJSON(String response) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);

            stringBuilder.append("Город: ");
            stringBuilder.append((String) jsonObject.get("name"));

            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weather = (JSONObject) weatherArray.get(0);
            stringBuilder.append("\n\nОписание: ");
            stringBuilder.append(weather.getString("description"));

            JSONObject main = (JSONObject) jsonObject.get("main");
            stringBuilder.append("\n\nТемпература: ");
            stringBuilder.append(main.getString("temp"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private HttpUrl makeUrl(String city) {
        return HttpUrl.parse(WEATHER_URL)
                .newBuilder()
                .addQueryParameter("q", city)
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "ru")
                .addQueryParameter("appid", WEATHER_API_KEY)
                .build();
    }
}