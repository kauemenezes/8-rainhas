/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.model;

/**
 *
 * @author Kauê
 */
public class Tabuleiro implements Comparable<Tabuleiro>{
    private int[][] estado;
    private Integer custo;

    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public Integer getCusto() {
        return custo;
    }

    public void setCusto(Integer custo) {
        this.custo = custo;
    }    
    
    @Override
    public int compareTo(Tabuleiro o) {
        return this.custo.compareTo(o.getCusto());
    }
}
