package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a static obstacle in the simulation.
 * <p>
 * The {@code Obstacle} class extends {@code AbstractObject} and is used to represent
 * stationary obstacles that robots interact with during the simulation. Obstacles
 * are visually represented as gray circles.
 * </p>
 */
public class Obstacle extends AbstractObject {

    /**
     * Constructs an {@code Obstacle} with the specified position and radius.
     *
     * @param x      The x-coordinate of the obstacle's center.
     * @param y      The y-coordinate of the obstacle's center.
     * @param radius The radius of the obstacle.
     */
    public Obstacle(double x, double y, double radius) {
        super(x, y, radius);
    }

    /**
     * Updates the state of the obstacle.
     * <p>
     * Obstacles are static and do not change state or move during the simulation.
     * </p>
     */
    @Override
    public void update() {
        // Obstacles do not move
    }

    /**
     * Renders the obstacle on the simulation canvas.
     * <p>
     * Obstacles are drawn as solid gray circles.
     * </p>
     *
     * @param gc The {@code GraphicsContext} used to draw the obstacle.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}


