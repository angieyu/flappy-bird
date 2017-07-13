package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game {
    // JFrame => Canvas
    public final static int WIDTH = 800, HEIGHT = 600;

    private String gameName = "Flappy Bird";

    private Canvas game = new Canvas();

    private Input input;

    private ArrayList<Updatable> updateables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    public void addUpdatable(Updatable u) {
        updateables.add(u);
    }

    public void removeUpdatable(Updatable u) {
        updateables.remove(u);
    }

    public void addRenderable(Renderable r) {
        renderables.add(r);
    }

    public void removeRenderable(Renderable r) {
        renderables.remove(r);
    }

    public void start() {
        // init window
        Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT);
        JFrame gameWindow = new JFrame(gameName);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);
        gameWindow.setSize(gameSize);
        gameWindow.setMaximumSize(gameSize);
        gameWindow.setMinimumSize(gameSize);
        gameWindow.setPreferredSize(gameSize);
        gameWindow.add(game);
        gameWindow.setLocationRelativeTo(null);

        // init input
        input = new Input();
        game.addKeyListener(input);
        // game loop
        final int TICKS_PER_SECOND = 60;
        final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIPS = 5;

        long nextGameTick = System.currentTimeMillis();
        int loops;
        float interpolation;

        long timeAtLastFPSCheck = 0;
        int ticks = 0;

        boolean running = true;
        while (running) {
            // update
            loops = 0;
            while (System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIPS) {
                update();
                ticks++;
                nextGameTick += TIME_PER_TICK;
                loops++;
            }

            // render
            interpolation = (float) (System.currentTimeMillis() + TIME_PER_TICK - nextGameTick) / (float) TIME_PER_TICK;
            render(interpolation);

            // FPS check
            if (System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
                System.out.println("FPS: " + ticks);
                gameWindow.setTitle(gameName + " - FPS: " + ticks);
                ticks = 0;
                timeAtLastFPSCheck = System.currentTimeMillis();
            }
        }
        // game end
    }

    private void update() {
        for (Updatable u : updateables) {
            u.update(input);
        }
    }

    public void render(float interpolation) {
        BufferStrategy b = game.getBufferStrategy();
        if (b == null) {
            game.createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) b.getDrawGraphics();
        g.clearRect(0, 0, game.getWidth(), game.getHeight());
        for (Renderable r : renderables) {
            r.render(g, interpolation);
        }
        g.dispose();
        b.show();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.renderables.add(new Renderable() {
            @Override
            public void render(Graphics2D g, float interpolation) {
                g.setColor(Color.RED);
                g.drawRect(300, 250, 50, 100);
            }
        });
        g.start();
    }
}