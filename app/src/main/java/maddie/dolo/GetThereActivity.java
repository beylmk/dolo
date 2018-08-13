package maddie.dolo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetThereActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "lyft:Example";
    private static final String LYFT_PACKAGE = "me.lyft.android";
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_there);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        setUpRequestRideButtons();
    }

    private void setUpRequestRideButtons() {
        Button lyftButton = findViewById(R.id.lyft_button);
        lyftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deepLinkIntoLyft();
            }
        });
    }

    private void deepLinkIntoLyft() {
        if (isPackageInstalled(this, LYFT_PACKAGE)) {
            //This intent will help you to launch if the package is already installed
            openLink( this, "lyft://");

            Log.d(TAG, "Lyft is already installed on your phone.");
        } else {
            openLink(this, "https://www.lyft.com/signup/SDKSIGNUP?clientId=YOUR_CLIENT_ID&sdkName=android_direct");

            Log.d(TAG, "Lyft is not currently installed on your phone..");
        }
    }

    static void openLink(Activity activity, String link) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
        playStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        playStoreIntent.setData(Uri.parse(link));
        activity.startActivity(playStoreIntent);
    }

    static boolean isPackageInstalled(Context context, String packageId) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageId, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // ignored.
        }
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng dolores = new LatLng(37.7596, -122.4269);
        mMap.addMarker(new MarkerOptions().position(dolores).title("Marker in Dolores"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dolores));
    }
}
