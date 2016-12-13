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
public class Lloc {

    private int lloc_id;
    private String num_bloc;
    private String num_passadis;
    private String num_lleixa;

    public Lloc() {
    }

    public Lloc(int lloc_id, String num_bloc, String num_passadis, String num_lleixa) {
        this.lloc_id = lloc_id;
        this.num_bloc = num_bloc;
        this.num_passadis = num_passadis;
        this.num_lleixa = num_lleixa;
    }

    public int getLloc_id() {
        return lloc_id;
    }

    public String getNum_bloc() {
        return num_bloc;
    }

    public String getNum_passadis() {
        return num_passadis;
    }

    public String getNum_lleixa() {
        return num_lleixa;
    }

    public void setLloc_id(int lloc_id) {
        this.lloc_id = lloc_id;
    }

    public void setNum_bloc(String num_bloc) {
        this.num_bloc = num_bloc;
    }

    public void setNum_passadis(String num_passadis) {
        this.num_passadis = num_passadis;
    }

    public void setNum_lleixa(String num_lleixa) {
        this.num_lleixa = num_lleixa;
    }

}
