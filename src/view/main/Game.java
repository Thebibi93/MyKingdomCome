package view.main;

import inputs.KeyBoard;
import inputs.Mouse;
import states.*;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private GameScreen gameScreen = new GameScreen();
    private Thread gameThread;

    public static final Game INSTANCE = new Game();

    private final double FPS = 60.0;
    private final double UPS = 60.0;

    private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // Classes
    private Affichage affichage = new Affichage();
    private Menu menu = Menu.getInstance();
    private Playing playing = Playing.getInstance();
    private Settings settings = Settings.getInstance();
    private static GameStates gameState = GameStates.MENU;

    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);
        setResizable(false);
        
        getContentPane().setBackground(java.awt.Color.BLACK);

        add(gameScreen);
        pack();

        setVisible(true);
    }

    private void initInputs() {
        addMouseListener(Mouse.INSTANCE);
        addMouseMotionListener(Mouse.INSTANCE);
        addKeyListener(KeyBoard.INSTANCE);

        requestFocus();
    }

    private void start() {
        gameThread = new Thread(this);
        gameState.changeState(menu);
        gameThread.start();
    }

    private void updateGameLogic(long deltaT) {
        gameState.updateLogic(deltaT);
    }

    @Override
    public void run() {
        double timePerFrame = NANOSECONDS_PER_SECOND / FPS;
        double timePerUpdate = NANOSECONDS_PER_SECOND / UPS;

        long lastFrameTime = System.nanoTime();
        long lastUpdateTime = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {
            now = System.nanoTime();

            if (now - lastFrameTime >= timePerFrame) {
                repaint();
                lastFrameTime = now;
                frames++;
            }

            if (now - lastUpdateTime >= timePerUpdate) {
                long deltaT = now - lastUpdateTime;
                updateGameLogic(deltaT);
                lastUpdateTime = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= MILLISECONDS_PER_SECOND) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getters and setters
    public Affichage getAffichage() {
        return affichage;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public static GameStates getGameState() {
        return gameState;
    }

    public static void main(String[] args) {
        INSTANCE.initInputs();
        INSTANCE.start();
    }
}