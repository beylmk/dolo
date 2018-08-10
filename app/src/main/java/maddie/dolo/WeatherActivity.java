package maddie.dolo;

import android.os.Bundle;

public class WeatherActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        super.setUpNavigationDrawer();
    }
}
