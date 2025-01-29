package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a user-controlled robot in the simulation.
 * <p>
 * The {@code ControlBot} allows players to manually control its movement
 * using keyboard inputs (W, A, S, D). It does not move automatically and
 * does not perform any sensing by default.
 * </p>
 */
public class ControlBot extends Robot {

    /**
     * Constructs a {@code ControlBot} with specified position, radius, speed, and direction.
     *
     * @param x         The x-coordinate of the robot's initial position.
     * @param y         The y-coordinate of the robot's initial position.
     * @param radius    The radius of the robot.
     * @param speed     The speed of the robot.
     * @param direction The initial direction of the robot in degrees.
     */
    public ControlBot(double x, double y, double radius, double speed, double direction) {
        super(x, y, radius, speed, direction);
    }

    /**
     * Prevents automatic movement of the {@code ControlBot}.
     * <p>
     * The {@code ControlBot} only moves when the player provides input
     * using the movement methods (e.g., {@code moveUp}, {@code moveDown}).
     * </p>
     */
    @Override
    public void update() {
        // The ControlBot does not move automatically.
        // Movement is only handled via player input (W, A, S, D).
    }

    /**
     * Moves the robot upward by decreasing its y-coordinate.
     */
    public void moveUp() {
        y -= speed;
    }

    /**
     * Moves the robot downward by increasing its y-coordinate.
     */
    public void moveDown() {
        y += speed;
    }

    /**
     * Moves the robot to the left by decreasing its x-coordinate.
     */
    public void moveLeft() {
        x -= speed;
    }

    /**
     * Moves the robot to the right by increasing its x-coordinate.
     */
    public void moveRight() {
        x += speed;
    }

    /**
     * Provides a placeholder for the sensing functionality.
     * <p>
     * The {@code ControlBot} does not have any sensing capabilities
     * by default.
     * </p>
     */
    @Override
    public void sense() {
        // ControlBot does not sense anything by default.
    }

    /**
     * Renders the {@code ControlBot} on the simulation canvas.
     * <p>
     * The robot's body is represented as a purple circle, and its wheels
     * are drawn as black circles positioned around the body.
     * </p>
     *
     * @param gc The {@code GraphicsContext} used to draw the robot.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the robot body
        gc.setFill(Color.PURPLE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw wheels
        double wheelRadius = radius / 2;
        double wheelOffset = radius * 1.0;

        gc.setFill(Color.BLACK);
        // Top-left wheel
        gc.fillOval(x - wheelOffset, y - wheelOffset, wheelRadius, wheelRadius);
        // Top-right wheel
        gc.fillOval(x + wheelOffset - wheelRadius, y - wheelOffset, wheelRadius, wheelRadius);
        // Bottom-left wheel
        gc.fillOval(x - wheelOffset, y + wheelOffset - wheelRadius, wheelRadius, wheelRadius);
        // Bottom-right wheel
        gc.fillOval(x + wheelOffset - wheelRadius, y + wheelOffset - wheelRadius, wheelRadius, wheelRadius);
    }
}



