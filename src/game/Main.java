package game;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        Pipes p = new Pipes();
        // Initialize game objects

        // Add updatables and renderables
        g.addRenderable(p);
        g.addUpdatable(p);

        // Start
        g.start();
    }
}
