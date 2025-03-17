package pe.turismo_local.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import pe.turismo_local.R;
import pe.turismo_local.controller.LugarController;
import pe.turismo_local.databinding.FragmentTacnaBinding;
import pe.turismo_local.lib.RoutesApiService;
import pe.turismo_local.model.DirectionsResponse;
import pe.turismo_local.model.LugarTuristico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TacnaMapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentTacnaBinding binding;
    GoogleMap mMap;
    RelativeLayout loadingScreen;
    private TextView txtDistancia;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTacnaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingScreen = view.findViewById(R.id.loadingScreen);
        txtDistancia = view.findViewById(R.id.distanceText);

        loadingScreen.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(() -> {
            if (loadingScreen != null) {
                loadingScreen.setVisibility(View.GONE);
                txtDistancia.setBackgroundColor(getResources().getColor(R.color.notification));
                txtDistancia.setVisibility(View.VISIBLE);
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

        drawRoute(lugares);
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

    private void drawRoute(List<LugarTuristico> lugares) {
        if (lugares.size() < 2) return;

        // Crear JSON para la solicitud
        JsonObject requestBody = new JsonObject();

        // Origen
        JsonObject origin = new JsonObject();
        JsonObject originLocation = new JsonObject();
        JsonObject originLatLng = new JsonObject();
        originLatLng.addProperty("latitude", lugares.get(0).getLatitud());
        originLatLng.addProperty("longitude", lugares.get(0).getLongitud());
        originLocation.add("latLng", originLatLng);
        origin.add("location", originLocation);
        requestBody.add("origin", origin);

        // Destino
        JsonObject destination = new JsonObject();
        JsonObject destinationLocation = new JsonObject();
        JsonObject destinationLatLng = new JsonObject();
        destinationLatLng.addProperty("latitude", lugares.get(lugares.size() - 1).getLatitud());
        destinationLatLng.addProperty("longitude", lugares.get(lugares.size() - 1).getLongitud());
        destinationLocation.add("latLng", destinationLatLng);
        destination.add("location", destinationLocation);
        requestBody.add("destination", destination);

        // Waypoints (lugares intermedios)
        if (lugares.size() > 2) {
            JsonArray intermediates = new JsonArray();
            for (int i = 1; i < lugares.size() - 1; i++) {
                JsonObject waypoint = new JsonObject();
                JsonObject waypointLocation = new JsonObject();
                JsonObject waypointLatLng = new JsonObject();
                waypointLatLng.addProperty("latitude", lugares.get(i).getLatitud());
                waypointLatLng.addProperty("longitude", lugares.get(i).getLongitud());
                waypointLocation.add("latLng", waypointLatLng);
                waypoint.add("location", waypointLocation);
                intermediates.add(waypoint);
            }
            requestBody.add("intermediates", intermediates);
        }

        // Opciones de enrutamiento
        requestBody.addProperty("travelMode", "DRIVE");
        requestBody.addProperty("routingPreference", "TRAFFIC_AWARE");
        requestBody.addProperty("computeAlternativeRoutes", false);
        requestBody.addProperty("languageCode", "es-419");
        requestBody.addProperty("units", "IMPERIAL");

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://routes.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoutesApiService service = retrofit.create(RoutesApiService.class);

        // Llamar a la API
        Call<DirectionsResponse> call = service.computeRoutes(
                "AIzaSyDgpL1QSrLHPTOIE6AkQ81dL1vGpxy3eec",
                "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline",
                requestBody
        );

        call.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {

                if (response.body() != null && !response.body().routes.isEmpty()) {
                    String encodedPath = response.body().routes.get(0).polyline.encodedPolyline;
                    int distance = response.body().routes.get(0).distanceMeters;
                    List<LatLng> decodedPath = PolyUtil.decode(encodedPath);

                    // Dibujar la ruta en el mapa
                    if (mMap != null) {
                        mMap.addPolyline(new PolylineOptions()
                                .addAll(decodedPath)
                                .color(Color.RED)
                                .width(8));
                    }

                    txtDistancia.setText("Distancia en automovil: " + distance + " metros");
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Log.e("API_REQUEST", "Error al obtener la ruta: " + t.getMessage());
                Toast.makeText(requireContext(), "Error al obtener la ruta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}