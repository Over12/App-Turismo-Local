package pe.turismo_local.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LugarTuristico implements Parcelable {
    private int id;
    private String nombre;
    private String descripcion;
    private String cuidad;
    private double latitud;
    private double longitud;
    private String imagen;
    private boolean favorito;

    public LugarTuristico() {
    }

    public LugarTuristico(int id, String nombre, String descripcion, String cuidad, double latitud, double longitud, String imagen, boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuidad = cuidad;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagen = imagen;
        this.favorito = favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCuidad() {
        return cuidad;
    }

    public void setCuidad(String cuidad) {
        this.cuidad = cuidad;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    protected LugarTuristico(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        cuidad = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        imagen = in.readString();
        favorito = in.readByte() != 0;
    }

    public static final Creator<LugarTuristico> CREATOR = new Creator<LugarTuristico>() {
        @Override
        public LugarTuristico createFromParcel(Parcel in) {
            return new LugarTuristico(in);
        }

        @Override
        public LugarTuristico[] newArray(int size) {
            return new LugarTuristico[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(cuidad);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(imagen);
        dest.writeByte((byte) (favorito ? 1 : 0));
    }
}
