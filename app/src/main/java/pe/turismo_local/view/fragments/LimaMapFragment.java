package pe.turismo_local.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import pe.turismo_local.R;
import pe.turismo_local.databinding.FragmentLimaBinding;

public class LimaMapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentLimaBinding binding;
    private GoogleMap mMap;
    private RelativeLayout loadingScreen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLimaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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

        mMap.setOnMarkerClickListener(marker -> {
            mostrarMenu(marker.getTitle());
            return true;
        });
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Centrar el mapa en Lima
        LatLng lima = new LatLng(-12.0463731, -77.042754);
        mMap.addMarker(new MarkerOptions().position(lima).title("Lima, Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 12));

        addMarkers();
    }

    private void addMarkers() {
        List<Pair<LatLng, String>> lugares = Arrays.asList(
                new Pair<>(new LatLng(-12.0453, -77.0311), "Plaza Mayor de Lima"),
                new Pair<>(new LatLng(-12.0432, -77.0282), "Catedral de Lima"),
                new Pair<>(new LatLng(-12.0464, -77.0300), "Palacio de Gobierno"),
                new Pair<>(new LatLng(-12.1210, -77.0301), "Parque Kennedy"),
                new Pair<>(new LatLng(-12.0855, -77.0944), "Museo Larco")
        );

        for (Pair<LatLng, String> lugar : lugares) {
            mMap.addMarker(new MarkerOptions()
                    .position(lugar.first)
                    .title(lugar.second)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }

    private void mostrarMenu(String lugar) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.menu_flotante, null);
        bottomSheetDialog.setContentView(view);

        TextView txtLugar = view.findViewById(R.id.txtLugar);
        Button btnVerDetalles = view.findViewById(R.id.btnVerDetalles);
        Button btnCerrar = view.findViewById(R.id.btnCerrar);

        txtLugar.setText(lugar);

        btnVerDetalles.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Ver detalles de " + lugar, Toast.LENGTH_SHORT).show();
        });

        btnCerrar.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }
}