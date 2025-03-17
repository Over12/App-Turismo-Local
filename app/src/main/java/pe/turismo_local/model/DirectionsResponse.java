package pe.turismo_local.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DirectionsResponse {
    @SerializedName("routes")
    public List<Route> routes;

    public static class Route {
        @SerializedName("distanceMeters")
        public int distanceMeters;

        @SerializedName("duration")
        public String duration;

        @SerializedName("polyline")
        public Polyline polyline;
    }

    public static class Polyline {
        @SerializedName("encodedPolyline")
        public String encodedPolyline;
    }
}

