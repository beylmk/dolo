package maddie.dolo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import maddie.dolo.DoloUtil;
import maddie.dolo.R;

public class GetThereActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String LYFT_PACKAGE = "me.lyft.android";
    private static final String UBER_PACKAGE = "uber";
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
                deepLinkIntoRideShare(LYFT_PACKAGE);
            }
        });

        Button uberButton = findViewById(R.id.uber_button);
        uberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deepLinkIntoRideShare(UBER_PACKAGE);
            }
        });
    }

    private void deepLinkIntoRideShare(String packageName) {
        double dropOffLat = doloresMarker.getPosition().latitude;
        double dropOffLong = doloresMarker.getPosition().longitude;
        switch (packageName) {
            case LYFT_PACKAGE: deepLinkIntoLyft(dropOffLat, dropOffLong);
            default: deepLinkIntoUber(dropOffLat, dropOffLong);
        }

    }

    private void deepLinkIntoUber(double dropOffLat, double dropOffLong) {
        openPlayStoreLink(this, "uber://?action=setPickup" +
                "&pickup=my_loction" +
                "&dropoff[latitude]=" + dropOffLat +
                "&dropoff[longitude]=" + dropOffLong);
    }

    private void deepLinkIntoLyft(double dropOffLat, double dropOffLong) {
        if (isPackageInstalled(this, LYFT_PACKAGE)) {
            //TODO use URI builder to make this prettier

            openPlayStoreLink(this, "lyft://ridetype?id=lyft" +
                    "&destination[latitude]=" + dropOffLat +
                    "&destination[longitude]=" + dropOffLong);
        } else {
            openPlayStoreLink(this, DoloUtil.LYFT_PLAY_STORE_LINK);
        }
    }

    static void openPlayStoreLink(Activity activity, String link) {
        Intent playStoreIntent = getPlayStoreIntent(link);
        activity.startActivity(playStoreIntent);
    }

    static Intent getPlayStoreIntent(String link) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
        playStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        playStoreIntent.setData(Uri.parse(link));
        return playStoreIntent;
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
        googleMap.setIndoorEnabled(false);
        LatLng dolores = new LatLng(37.7596, -122.4269);
        doloresMarker = googleMap.addMarker(new MarkerOptions()
                .position(dolores)
                .title(getString(R.string.marker_title))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dolo_app_icon))
                .draggable(true));
        doloresMarker.showInfoWindow();
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                doloresMarker.setPosition(marker.getPosition());
            }
        });
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dolores));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
    }
}
