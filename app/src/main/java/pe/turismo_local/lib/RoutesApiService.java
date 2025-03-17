package pe.turismo_local.lib;

import com.google.gson.JsonObject;

import pe.turismo_local.model.DirectionsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RoutesApiService {
    @POST("directions/v2:computeRoutes")
    Call<DirectionsResponse> computeRoutes(
            @Header("X-Goog-Api-Key") String apiKey,
            @Header("X-Goog-FieldMask") String fieldMask,
            @Body JsonObject requestBody
    );
}

