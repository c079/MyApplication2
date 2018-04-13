package org.android.myapplication2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    protected void onButtonClick1(View v) {
        Toast.makeText(getApplicationContext(), "위치를 검색합니다.", Toast.LENGTH_LONG).show();
    }

    protected void onButtonClick2(View v) {
        MapFragment mapFragment;
        LocationManager locationManager;

        double mLatitude;
        double mLongitude;
        setContentView(R.layout.activity_maps);
        Toast.makeText(getApplicationContext(), "Gps로 위치를 검색합니다.", Toast.LENGTH_LONG).show();


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //GPS가 켜져있는지 체크
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }



        requestMyLocation();
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한

        if (requestCode == 1) {
            //권한받음
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestMyLocation();
            }
            //권한못받음
            else {
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //나의 위치 요청
    public void requestMyLocation() {

        LocationManager locationManager = null;

        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
    }

    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {
        MapFragment mapFragment;
        LocationManager locationManager;
        RelativeLayout boxMap;

        double mLatitude;
        double mLongitude;

        @Override
        public void onLocationChanged(Location location) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //나의 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도

            //맵생성
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            //콜백클래스 설정
            mapFragment.getMapAsync(MapsActivity.this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("gps", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    //구글맵 생성 콜백


    protected void onButtonClick3(View v) {

        setContentView(R.layout.login);
    }

    protected void onButtonClick4(View v) {
        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_maps);


    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng seoul = new LatLng(37.56, 126.98);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

        MarkerOptions marker = new MarkerOptions();
        marker .position(new LatLng(37.56, 126.98)).title("서울역").snippet("Seoul Station");
        googleMap.addMarker(marker).showInfoWindow();
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


}




