package bloodbank.iii.com.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SearchActivity extends ActionBarActivity {

    private Button b;

    LocationManager location_manager;
    LocationListener listner;
    String getLatitude;
    String getLongitude;

    double x;
    double y;

    Geocoder geocoder;
    List<Address> addresses;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f3c614")));

        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listner = new MyLocationListner();

        b=(Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, listner);
                location_manager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
                startActivity(new Intent(SearchActivity.this,MainActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyLocationListner implements LocationListener
    {
        // @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @SuppressWarnings("static-access")
        @Override
        public void onLocationChanged(Location arg0)
        {
            // TODO Auto-generated method stub
            getLatitude = "0" ;//+ arg0.getLatitude();
            getLongitude = "0" ;//+ arg0.getLongitude();

            x = arg0.getLatitude();
            y = arg0.getLongitude();

            try
            {
                geocoder = new Geocoder(SearchActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                StringBuilder str = new StringBuilder();
                if (geocoder.isPresent())
                {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getSubLocality();
                    String city = returnAddress.getLocality();
                    //String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + " ");
                    str.append(city + "");
                    Log.d("Current place: ",str.toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "geocoder not present", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Log.d("exception", "summma");
            }
        }

        @Override
        public void onProviderDisabled(String arg0) {}

        @Override
        public void onProviderEnabled(String arg0) {}

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
    }

}
