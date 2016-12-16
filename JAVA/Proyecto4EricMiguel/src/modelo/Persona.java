/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author dawj23
 */
public class Persona {
    
    private int pers_id;
    private String pers_nom;
    private String pers_pass;

    public Persona() {
    }
// aañadimos constructores 
    public Persona(int pers_id, String pers_nom, String pers_pass) {
        this.pers_id = pers_id;
        this.pers_nom = pers_nom;
        this.pers_pass = pers_pass;
    }

    public Persona(String pers_nom, String pers_pass) {
        this.pers_nom = pers_nom;
        this.pers_pass = pers_pass;
    }
// añadimos getters
    public int getPers_id() {
        return pers_id;
    }

    public String getPers_nom() {
        return pers_nom;
    }

    public String getPers_pass() {
        return pers_pass;
    }
// añadimos setters
    public void setPers_id(int pers_id) {
        this.pers_id = pers_id;
    }

    public void setPers_nom(String pers_nom) {
        this.pers_nom = pers_nom;
    }

    public void setPers_pass(String pers_pass) {
        this.pers_pass = pers_pass;
    }
    
    
    
}
