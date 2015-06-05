/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controller;

import br.com.model.Tabuleiro;
import br.com.model.Problema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por solucionar o problema das 8 rainhas, através
 * dos algoritmos subida de encosta e têmpera simulada.
 * @author Kauê
 */
public class BuscaSolucao {
    
    private Tabuleiro corrente, vizinho;
    private double temperatura;
    private final int epocas = 500;
    private List<Tabuleiro> custos;
    
    /**
     * Método responsável por encontrar a melhor solução, seja essa local 
     * ou global, para determinado problema passado como parâmetro, com base no
     * algoritmo subida de encosta.
     * @param problema Que representa o problema a ser resolvido.
     * @return um objeto Tabuleiro, que representando a solução encontrada.
     */
    public Tabuleiro buscaSubidaDeEncosta(Problema problema) {
        this.corrente = new Tabuleiro();
        this.corrente.setEstado(problema.getEstadoInicial());
        this.corrente.setCusto(calcularCusto(this.corrente.getEstado()));
                
        // while responsável por gerar os vizinhos do tabuleiro corrente,
        // enquanto os custos destes forem melhor.
        while (true) {
            this.vizinho = buscaMelhorVizinho(this.corrente);
            
            if (this.vizinho.getCusto() < this.corrente.getCusto()) {
                this.corrente = this.vizinho;
            } else {
                return this.corrente;
            }
        }
    }
    
    /**
     * Método responsável por encontrar a melhor solução para determinado 
     * problema passado como parâmetro, com base no algoritmo têmpera simulada.
     * @param problema Que representa o problema a ser resolvido.
     * @param temp que representa a temperatura inicial do algoritmo.
     * @return um objeto Tabuleiro, que representando a solução encontrada. 
     */
    public Tabuleiro buscaTemperaSimulada(Problema problema, double temp) {
        this.temperatura = temp;
        this.corrente = new Tabuleiro();
        this.corrente.setEstado(problema.getEstadoInicial());
        this.corrente.setCusto(calcularCusto(this.corrente.getEstado()));
        // contador responsável por verificar se a execução do código
        // está dentro do número máximo de épocas pré-definido.
        int contador = 1;
        
        while (this.corrente.getCusto() != 0 && contador <= epocas) {
            double temperatura;
            double delta;
            double probabilidade;
            double rand;


            for (temperatura = this.temperatura; (temperatura > 0) && (this.corrente.getCusto() != 0); temperatura--) {
                this.vizinho = buscaMelhorVizinho(this.corrente);
                delta = this.corrente.getCusto() - this.vizinho.getCusto();
                probabilidade = Math.exp(delta / temperatura);
                rand = Math.random();

                if (delta > 0) {
                    this.corrente = this.vizinho;
                } else if (rand <= probabilidade) {
                    this.corrente = this.vizinho;
                }
            }
            
            contador++;
        }
        return this.corrente;
    }
    
     /**
     * Método responsável por calcular o custo do estado representado pelo tabuleiro.
     * @param estado
     * @return um inteiro com o respectivo custo.
     */
    public int calcularCusto(int[][] estado) {
        int tam = estado.length;
        int soma = 0;
        //Percorre as colunas da matriz.
        for (int i = 0; i < tam; i++) {
            //Percorre as linhas da matriz.
            for (int j = 0; j < tam; j++) {
                //verifica se a posição atual está ocupada por uma rainha.
                if (estado[j][i] == 1) {
                    int aux = j, aux2 = i;
                    //while responsável por contar os ataques a rainha atual na diagonal superior.
                    while (aux > 0 && aux2 < 7) {

                        if (estado[--aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    aux = j;
                    aux2 = i;
                    //while responsável por contar os ataques a rainha atual na diagonal inferior.
                    while (aux < 7 && aux2 < 7) {
                        if (estado[++aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    aux = j;
                    aux2 = i;
                    //while responsável por contar os ataques a rainha atual na sua direita.
                    while (aux2 < 7) {
                        if (estado[aux][++aux2] == 1) {
                            soma++;
                        }
                    }
                    break;
                }
            }
        }
        return soma;
    }
    
    /**
     * Método responsável por encontrar o melhor vizinho do nó atual passado
     * como parâmetro.
     * @param atual
     * @return um tabuleiro com o melhor custo dentre os sucessores do tabuleiro atual.
     */
    public Tabuleiro buscaMelhorVizinho(Tabuleiro atual) {
        int[][] estado = new int[8][8];
        this.setMatriz(estado, atual.getEstado());
        int tam = estado.length;
        custos = new ArrayList<>();
        // Percorre as colunas da matriz.
        for (int i = 0; i < tam; i++) {
            // Percorre as linhas da matriz.
            for (int j = 0; j < tam; j++) {
                // verifica se a posição atual está ocupada por uma rainha.
                if (estado[j][i] == 1) {
                    int aux = j;
                    // while responsável por armazenar os sucessores acima da 
                    // rainha, e seus respectivos custos.
                    while (aux > 0) {
                        Tabuleiro tabuleiro = new Tabuleiro();
                        int[][] estadoAtual = new int[8][8];
                        this.setMatriz(estadoAtual, estado);
                        estadoAtual[j][i] = 0;
                        estadoAtual[--aux][i] = 1;
                        tabuleiro.setEstado(estadoAtual);
                        tabuleiro.setCusto(this.calcularCusto(tabuleiro.getEstado()));
                        this.custos.add(tabuleiro);                        
                    }
                    
                    aux = j;
                    // while responsável por armazenar os sucessores abaixo da 
                    // rainha, e seus respectivos custos.                    
                    while (aux < 7) {
                        Tabuleiro tabuleiro = new Tabuleiro();
                        int[][] estadoAtual = new int[8][8];
                        this.setMatriz(estadoAtual, estado);
                        estadoAtual[j][i] = 0;
                        estadoAtual[++aux][i] = 1;
                        tabuleiro.setEstado(estadoAtual);
                        tabuleiro.setCusto(this.calcularCusto(tabuleiro.getEstado()));
                        this.custos.add(tabuleiro);                        
                    }
                    break;
                }
            }
        }
        // Ordena os sucessores com base no custo.
        Collections.sort(this.custos);
        int menorCusto = this.custos.get(0).getCusto();
        List<Tabuleiro> menoresCustos = new ArrayList<>();
        // For responsável por armazenar todos os nós que possuem como valor
        // de custo o valor contido em menorCusto.
        for(Tabuleiro no : this.custos){
            if(no.getCusto() == menorCusto){
                menoresCustos.add(no);
            }
        }
        // Gera aleatoriedade na escolha dos nós.
        Collections.shuffle(menoresCustos);
        return menoresCustos.get(0);        
        
    }  
    
    /**
     * Método responsável por fazer uma cópia de matriz2 para matriz1.
     * @param matriz1
     * @param matriz2 
     */
    public void setMatriz(int[][] matriz1, int[][] matriz2) {
        int tam = matriz1.length;
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                matriz1[i][j] = matriz2[i][j];
            }
        }
    }    
}
