/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoartex.artex.dominio;

/**
 *
 * @author mapa
 */
public  class Bien {
        private String controlMedio;
        private String descripcion;

        public Bien(String controlMedio, String descripcion) {
            this.controlMedio = controlMedio;
            this.descripcion = descripcion;
        }
        
        public String getControlMedio() {
            return controlMedio;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setControlMedio(String controlMedio) {
            this.controlMedio = controlMedio;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
