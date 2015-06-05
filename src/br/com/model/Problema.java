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
public class Problema {
    private int[][] estadoInicial;
        
    public Problema(int[][] estadoInical){
        this.estadoInicial = estadoInical;                
    }

    public int[][] getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(int[][] estadoInicial) {
        this.estadoInicial = estadoInicial;
    }    
}
