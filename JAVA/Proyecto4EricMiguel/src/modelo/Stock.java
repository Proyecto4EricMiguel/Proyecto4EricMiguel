/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ser
 */
public class Stock {
    private int idstock;
    private int stock_actual;
    private int stock_max;
    private int stock_min;

    public Stock() {
    }

    public Stock(int idstock) {
        this.idstock = idstock;
    }

    public Stock(int idstock, int stock_actual, int stock_max, int stock_min) {
        this.idstock = idstock;
        this.stock_actual = stock_actual;
        this.stock_max = stock_max;
        this.stock_min = stock_min;
    }

    public Stock(int stock_actual, int stock_max, int stock_min) {
        this.stock_actual = stock_actual;
        this.stock_max = stock_max;
        this.stock_min = stock_min;
    }

    public int getIdstock() {
        return idstock;
    }

    public void setIdstock(int idstock) {
        this.idstock = idstock;
    }

    public int getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    public int getStock_max() {
        return stock_max;
    }

    public void setStock_max(int stock_max) {
        this.stock_max = stock_max;
    }

    public int getStock_min() {
        return stock_min;
    }

    public void setStock_min(int stock_min) {
        this.stock_min = stock_min;
    }

    public Stock(int stock_max, int stock_min) {
        this.stock_max = stock_max;
        this.stock_min = stock_min;
    }
    
    
}
