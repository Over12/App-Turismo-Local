package pe.turismo_local.model;

public class LugarTuristico {
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
}
