package es.cic.grupo.Backend.model;

public class Obra {

        private Long id;
        private String nombre;
        private String autor;
        private int fecha;
        private String localizacion;
        private String descripcion;
        private Tipo tipo;

        public Obra(Long id, String nombre, String autor, int fecha, String localizacion, String descripcion, Tipo tipo ) {
            this.id = id;
            this.nombre = nombre;
            this.autor = autor;
            this.fecha = fecha;
            this.localizacion = localizacion;
            this.descripcion = descripcion;
            this.tipo = tipo;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public int getFecha() {
            return fecha;
        }

        public void setFecha(int fecha) {
            this.fecha = fecha;
        }

        public String getLocalizacion() {
            return localizacion;
        }

        public void setLocalizacion(String localizacion) {
            this.localizacion = localizacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

}

