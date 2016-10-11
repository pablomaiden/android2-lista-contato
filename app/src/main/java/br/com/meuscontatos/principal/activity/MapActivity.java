package br.com.meuscontatos.principal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.fragment.MapaFragment;

/**
 * Created by patricknasc on 11/10/16.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapaFragment mapaFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mapa_tab);

        mapaFragment = MapaFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mapaFragment)
                .commit();

        // Registra o onMapReadyCallback
        mapaFragment.getMapAsync(this);
    }


    public MapaFragment getMapaFragment() {
        return mapaFragment;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng cali = new LatLng(3.4383, -76.5161);
        googleMap.addMarker(new MarkerOptions()
                .position(cali)
                .title("Cali la Sucursal del cielo"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(10)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

