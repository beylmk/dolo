package maddie.dolo.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

public class WeatherService extends IntentService {

    public WeatherService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Bundle bundle = new Bundle();
            bundle.putString("hi", "ok");
        }
    }
}
