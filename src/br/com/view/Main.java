/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.view;

import br.com.controller.BuscaSolucao;
import br.com.controller.RainhaFitness;
import br.com.controller.RainhasAG;
import br.com.model.Tabuleiro;
import br.com.model.Problema;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.InvalidConfigurationException;

/**
 *
 * @author Kauê
 */
public class Main {

    public static void main(String[] args) {

        List<int[][]> solucoes = new ArrayList<>();

        int[][] inicial = {{0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0},
        {1, 0, 0, 0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0, 1, 0, 1},
        {0, 0, 1, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}};

        BuscaSolucao buscaSolucao = new BuscaSolucao();
        Problema problema = new Problema(inicial);

        Tabuleiro subidaDeEncosta = buscaSolucao.buscaSubidaDeEncosta(problema);
        Tabuleiro temperaSimulada = buscaSolucao.buscaTemperaSimulada(problema, 1000);

        solucoes.add(subidaDeEncosta.getEstado());
        solucoes.add(temperaSimulada.getEstado());

        RainhasAG rainhasAG = new RainhasAG();
        try {
            solucoes.add(rainhasAG.buscaAG());
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] algoritmo = {"Subida de Encosta" + "\n" + "Custo: " + subidaDeEncosta.getCusto(),
            "Têmpera Simulada" + "\n" + "Custo: " + temperaSimulada.getCusto(),
            "Algoritmos Genéticos"};

        int k = 0;
        for (int[][] solucao : solucoes) {
            System.out.println(algoritmo[k] + "\n");
            k++;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(solucao[i][j] + "\t");
                }
                System.out.println("");
            }

            System.out.println("");
            System.out.println("");
        }
    }
}
