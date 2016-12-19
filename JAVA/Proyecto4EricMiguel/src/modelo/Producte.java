/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author 9199.joan23
 */
public class Producte {
    
      private int prod_id;
    private String prod_nom;
    private double prod_preu;
     private String prod_foto;
     private int categoria_id;

    public Producte() {
    }

    public Producte(int prod_id) {
        this.prod_id = prod_id;
    }

    public Producte(int prod_id, String prod_nombre, double prod_preu, String prod_foto, int categoria_id) {
        this.prod_id = prod_id;
        this.prod_nom = prod_nom;
        this.prod_preu = prod_preu;
        this.prod_foto = prod_foto;
        this.categoria_id = categoria_id;
    }

    public Producte(String prod_nom, double prod_preu, int categoria_id) {
        this.prod_nom = prod_nom;
        this.prod_preu = prod_preu;
        this.categoria_id = categoria_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public String getProd_nombre() {
        return prod_nom;
    }

    public double getProd_preu() {
        return prod_preu;
    }

    public String getProd_foto() {
        return prod_foto;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nom = prod_nombre;
    }

    public void setProd_preu(double prod_preu) {
        this.prod_preu = prod_preu;
    }

    public void setProd_foto(String prod_foto) {
        this.prod_foto = prod_foto;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    
    
    
}
