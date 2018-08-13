package maddie.dolo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetThereActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "lyft:Example";
    private static final String LYFT_PACKAGE = "me.lyft.android";
    private GoogleMap mMap;
    private Marker doloresMarker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_there);

//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Button uberButton = findViewById(R.id.uber_button);
        uberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO deep link into uber
                Toast.makeText(GetThereActivity.this, "deep link into uber", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deepLinkIntoLyft() {
        if (isPackageInstalled(this, LYFT_PACKAGE)) {
            double dropOffLat = doloresMarker.getPosition().latitude;
            double dropOffLong = doloresMarker.getPosition().longitude;
            //TODO use URI builder to make this prettier
            openPlayStoreLink(this, "lyft://ridetype?id=lyft" +
                    "&pickup[latitude]=37.764728" +
                    "&pickup[longitude]=-122.422999" +
                    "&destination[latitude]=" + dropOffLat +
                    "&destination[longitude]=" + dropOffLong);
        } else {
            openPlayStoreLink(this, "https://www.lyft.com/signup/SDKSIGNUP?clientId=YOUR_CLIENT_ID&sdkName=android_direct");
        }
    }

    static void openPlayStoreLink(Activity activity, String link) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setIndoorEnabled(false);
        LatLng dolores = new LatLng(37.7596, -122.4269);
        //TODO set icon for map marker
        doloresMarker = mMap.addMarker(new MarkerOptions()
                .position(dolores)
                .title("Hold & drag me to move dropoff")
                .draggable(true));
        doloresMarker.showInfoWindow();
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                //TODO update icon for on marker drag start to something cute
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                doloresMarker.setPosition(marker.getPosition());
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dolores));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
    }
}
