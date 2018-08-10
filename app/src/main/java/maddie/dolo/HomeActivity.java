package maddie.dolo;

import android.os.Bundle;

public class HomeActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.setUpNavigationDrawer();
    }
}
