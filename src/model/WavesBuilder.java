package model;

import java.util.ArrayList;
import java.util.Random;
import model.gameEntities.enemies.*;

// This class is used to create the waves of ennemies for each level
public class WavesBuilder {

    public static Waves getWaves(int level, int difficulty, Board board, int currentWaveNumber) {
        switch (level) {
            case 1:
                return getLevel1(board, difficulty, currentWaveNumber);
            case 2:
                return getLevel2(board, difficulty, currentWaveNumber);
            case 3:
                return getLevel3(board, difficulty, currentWaveNumber);
            default:
                return null;
        }
    }

    private static Waves generateRandomWaves(Board board, int numWaves, int minEnemies, int maxEnemies,
            int[] spawnCells, int targetCellIndex, int boost, int ennemiesNumGrow, int currentWaveNumber) {
        Waves waves = new Waves();
        Random rand = new Random();

        int enemyLvl = 1;
        int currentWave = 1;
        int spawnCellIndex = 0;

        for (int i = 0; i < currentWaveNumber; i++) {
            if (currentWave % boost == 0) {
                enemyLvl++;
                minEnemies += ennemiesNumGrow;
                maxEnemies += ennemiesNumGrow;
            }
            currentWave++;
        }

        for (int i = 0; i < numWaves; i++) {
            if (currentWave % boost == 0) {
                enemyLvl++;
                minEnemies += ennemiesNumGrow;
                maxEnemies += ennemiesNumGrow;
            }
            ArrayList<Enemy> enemies = new ArrayList<Enemy>();
            int numEnemies = rand.nextInt(maxEnemies - minEnemies + 1) + minEnemies;
            spawnCellIndex = spawnCells[rand.nextInt(spawnCells.length)];

            for (int j = 0; j < numEnemies; j++) {
                Enemy enemy;
                int enemyType = rand.nextInt(100);
                if (enemyType < 30) {
                    enemy = new Goblin(enemyLvl, board.getCellSize());
                } else if (enemyType < 60) {
                    enemy = new Wolf(enemyLvl, board.getCellSize());

                } else if (enemyType < 80) {
                    enemy = new Bee(enemyLvl, board.getCellSize());
                } else {
                    enemy = new Slime(enemyLvl, board.getCellSize());
                }
                enemies.add(enemy);
            }

            int spawnInterval = rand.nextInt(2000 - 500 + 1) + 500; // Random spawn interval between 500 and 2000
            Wave wave = new Wave(enemies, spawnInterval, board, spawnCellIndex, targetCellIndex);
            waves.addWave(wave);
            currentWave++;
        }

        waves.addEventListeners(board);
        return waves;
    }

    private static Waves getLevel1(Board board, int difficulty, int currentWaveNumber) {
        int[] spawnCells = new int[] { 320 };
        switch (difficulty) {
            case 1:
                return generateRandomWaves(board, 20, 1, 3, spawnCells, 139, 5, 2, currentWaveNumber);
            case 2:
                return generateRandomWaves(board, 30, 2, 4, spawnCells, 139, 4, 3, currentWaveNumber);
            case 3:
                return generateRandomWaves(board, 40, 3, 5, spawnCells, 139, 3, 4, currentWaveNumber);
            default:
                return generateRandomWaves(board, 20, 2, 4, spawnCells, 139, 4, 2, currentWaveNumber);
        }
    }

    private static Waves getLevel2(Board board, int difficulty, int currentWaveNumber) {
        int[] spawnCells = new int[] { 320, 11 };
        switch (difficulty) {
            case 1:
                return generateRandomWaves(board, 30, 1, 3, spawnCells, 139, 5, 2, currentWaveNumber);
            case 2:
                return generateRandomWaves(board, 30, 2, 4, spawnCells, 139, 4, 3, currentWaveNumber);
            case 3:
                return generateRandomWaves(board, 40, 3, 5, spawnCells, 139, 3, 4, currentWaveNumber);
            default:
                return generateRandomWaves(board, 20, 2, 4, spawnCells, 139, 4, 2, currentWaveNumber);
        }
    }

    private static Waves getLevel3(Board board, int difficulty, int currentWaveNumber) {
        int[] spawnCells = new int[] { 11, 320 };
        switch (difficulty) {
            case 1:
                return generateRandomWaves(board, 30, 1, 3, spawnCells, 259, 5, 2, currentWaveNumber);
            case 2:
                return generateRandomWaves(board, 30, 2, 4, spawnCells, 259, 4, 3, currentWaveNumber);
            case 3:
                return generateRandomWaves(board, 40, 3, 5, spawnCells, 259, 3, 4, currentWaveNumber);
            default:
                return generateRandomWaves(board, 20, 2, 4, spawnCells, 259, 4, 2, currentWaveNumber);
        }
    }

}