/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 *
 * @author Kauê
 */
public class RainhaFitness extends FitnessFunction {
    int fitness;
    public double evaluate(IChromosome a_subject) {
        int[][] tabuleiro = new int[8][8];
        int pos;
        for (int i = 0; i < a_subject.getGenes().length; i++) {
            pos = (Integer) a_subject.getGene(i).getAllele();
            tabuleiro[pos][i] = 1;            
        }
        fitness = calcularCusto(tabuleiro);
        return fitness;
    }

    /**
     * Método responsável por calcular o custo do estado representado
     * pelo tabuleiro.  
     * @param tabuleiro.
     * @return um inteiro com o respectivo custo.
     */
    public int calcularCusto(int[][] tabuleiro) {
        //int[][] estado = no.getEstado();
        int tam = tabuleiro.length;
        int fitness = 0;
        int soma;
        //Percorre as colunas da matriz.
        for (int i = 0; i < tam; i++) {
            //Percorre as linhas da matriz.
            for (int j = 0; j < tam; j++) {
                soma = 0;
                //verifica se a posição atual está ocupada por uma rainha.
                if (tabuleiro[j][i] == 1) {
                    int aux = j, aux2 = i;
                    //while responsável por contar os ataques a rainha atual na diagonal superior.
                    while (aux > 0 && aux2 < 7) {

                        if (tabuleiro[--aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    aux = j;
                    aux2 = i;
                    //while responsável por contar os ataques a rainha atual na diagonal inferior.
                    while (aux < 7 && aux2 < 7) {
                        if (tabuleiro[++aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    aux = j;
                    aux2 = i;
                    //while responsável por contar os ataques a rainha atual na sua direita.
                    while (aux2 < 7) {
                        if (tabuleiro[aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    fitness += (7 - soma - i);
                    break;
                }
            }
        }
        return fitness;
    }
}
