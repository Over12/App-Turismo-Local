package pe.turismo_local.view.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import pe.turismo_local.controller.LugarController;
import pe.turismo_local.databinding.FragmentTacnaBinding;
import pe.turismo_local.model.LugarTuristico;

public class TacnaMapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentTacnaBinding binding;
    GoogleMap mMap;
    RelativeLayout loadingScreen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTacnaBinding.inflate(inflater, container, false);
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

        mMap.setOnMarkerClickListener(marker -> {
            if (Objects.equals(marker.getTitle(), "Tacna, Perú")) {
                return false;
            }

            mostrarMenu(marker.getTitle());
            return true;
        });
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Centrar el mapa en Tacna
        LatLng tacna = new LatLng(-18.0056, -70.2483);
        mMap.addMarker(new MarkerOptions().position(tacna).title("Tacna, Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tacna, 12));

        addMarkers();
    }

    private void addMarkers() {
        LugarController lugarController = new LugarController(requireContext());

        List<LugarTuristico> lugares = lugarController.obtenerLugaresPorCiudad("Tacna");

        for (LugarTuristico lugar : lugares) {
            LatLng latLng = new LatLng(lugar.getLatitud(), lugar.getLongitud());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(lugar.getNombre())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }

    private void mostrarMenu(String lugar) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.menu_flotante, null);
        bottomSheetDialog.setContentView(view);

        LugarController lugarController = new LugarController(requireContext());
        LugarTuristico lugarTuristico = lugarController.obtenerLugarPorNombre(lugar);

        TextView txtLugar = view.findViewById(R.id.txtLugar);
        ImageView imgLugar = view.findViewById(R.id.imgLugar);
        TextView txtDescripcion = view.findViewById(R.id.txtDescripcion);
        Button btnCerrar = view.findViewById(R.id.btnCerrar);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        txtLugar.setText(lugar);
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(lugarTuristico.getImagen())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, @NonNull Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imgLugar);

        txtDescripcion.setText(lugarTuristico.getDescripcion());

        LatLng latLng = new LatLng(lugarTuristico.getLatitud(), lugarTuristico.getLongitud());
        moverCamara(latLng);

        btnCerrar.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void moverCamara(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13), 500, null);
    }
}