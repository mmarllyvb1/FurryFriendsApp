package com.mmarllyvb1.furryfriendsapp.Modelo;

public class Mascota {
        private String id;
        private String nombre;
        private String cualidad;
        private int edad;
        private String imagen;

        public Mascota() {

        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCualidad() {
            return cualidad;
        }

        public void setCualidad(String cualidad) {
            this.cualidad = cualidad;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

}
