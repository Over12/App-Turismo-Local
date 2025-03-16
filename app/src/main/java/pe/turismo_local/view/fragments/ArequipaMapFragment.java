package pe.turismo_local.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

import pe.turismo_local.R;
import pe.turismo_local.controller.LugarController;
import pe.turismo_local.databinding.FragmentArequipaBinding;
import pe.turismo_local.model.LugarTuristico;

public class ArequipaMapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentArequipaBinding binding;
    private GoogleMap mMap;
    private RelativeLayout loadingScreen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentArequipaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingScreen = view.findViewById(R.id.loadingScreen);

        loadingScreen.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(() -> {
            if (loadingScreen != null) {
                loadingScreen.setVisibility(View.GONE);
            }
        });

        setUpMap();
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Centrar el mapa en Arequipa
        LatLng arequipa = new LatLng(-16.4090, -71.5375);
        mMap.addMarker(new MarkerOptions().position(arequipa).title("Arequipa, Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa, 12));

        addMarkers();
    }

    private void addMarkers() {
        LugarController lugarController = new LugarController(requireContext());

        List<LugarTuristico> lugares = lugarController.obtenerLugaresPorCiudad("Arequipa");

        for (LugarTuristico lugar : lugares) {
            LatLng latLng = new LatLng(lugar.getLatitud(), lugar.getLongitud());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(lugar.getNombre())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }
}