/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

/**
 * Classe responsável por obter a solução para o problema das 8 rainhas utilizando
 * algoritmos genéticos.
 * @author Kauê
 */
public class RainhasAG {
    
    public int[][] buscaAG() throws InvalidConfigurationException {
        // matriz que representará o tabuleiro depois de solucionado o problema.
        int[][] tabuleiro = new int[8][8];
        int linha, coluna = 0;
        
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        FitnessFunction fitness = new RainhaFitness();
        conf.setFitnessFunction(fitness);

        Gene[] sampleGene = new Gene[8];
        for (int i = 0; i < sampleGene.length; i++) {
            sampleGene[i] = new IntegerGene(conf, 0, 7);
        }
        IChromosome sampleChromosome = new Chromosome(conf, sampleGene);
        conf.setSampleChromosome(sampleChromosome);

        conf.setPopulationSize(10000);

        Genotype population = Genotype.randomInitialGenotype(conf);

        boolean rodaAlgotimo = true;
        int evolucoes = 0;
        while (rodaAlgotimo) {
            population.evolve();
            IChromosome bestSolutionSoFar = population.getFittestChromosome();
            System.out.println(bestSolutionSoFar);
            evolucoes++;
            if (bestSolutionSoFar.getFitnessValue() == 28) {
                System.out.println("Número de evolucões: " + evolucoes + "\n");
                break;
            }
        }
        
        for (Gene gene : population.getFittestChromosome().getGenes()) {
            linha = (Integer) gene.getAllele();
            tabuleiro[linha][coluna] = 1;
            coluna++;
        }
        return tabuleiro;
        
    }
}
