package maddie.dolo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, fullView, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        fullView.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = fullView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getHeaderView(0).findViewById(R.id.user_profile_picture)
        TextView userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.user_profile_name);
        userNameTextView.setText("usre name here");

    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_weather && !(this instanceof WeatherActivity)) {
            Intent weatherIntent = new Intent(this, WeatherActivity.class);
            startActivity(weatherIntent);
        } else if (id == R.id.nav_get_there && !(this instanceof GetThereActivity)) {
            Intent getThereIntent = new Intent(this, GetThereActivity.class);
            startActivity(getThereIntent);
        } else if (id == R.id.nav_sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(BaseActivity.this, R.string.logout_success, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
