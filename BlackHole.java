package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a BlackHole in the simulation.
 * <p>
 * The BlackHole is a stationary obstacle that absorbs any robot
 * entering its radius, deleting it from the simulation
 * </p>
 */
public class BlackHole extends AbstractObject {

    /**
     * Constructs a {@code BlackHole} with the specified position and radius.
     *
     * @param x      The x-coordinate of the center of the BlackHole.
     * @param y      The y-coordinate of the center of the BlackHole.
     * @param radius The radius of the BlackHole.
     */
    public BlackHole(double x, double y, double radius) {
        super(x, y, radius);
    }

    /**
     * Updates the state of the BlackHole.
     * <p>
     * The BlackHole is static and does not change state during the simulation.
     * </p>
     */
    @Override
    public void update() {
        // BlackHole does not move or change
    }

    /**
     * Checks if the specified {@code Robot} is absorbed by the BlackHole.
     * <p>
     * A robot is absorbed if its position is within the radius of the BlackHole.
     * </p>
     *
     * @param robot The {@code Robot} to check for absorption.
     * @return {@code true} if the robot is within the BlackHole's radius, {@code false} otherwise.
     */
    public boolean absorbs(Robot robot) {
        double dx = robot.getX() - this.x;
        double dy = robot.getY() - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If the robot enters the BlackHole's radius, it is absorbed
        return distance < this.radius + robot.getRadius();
    }

    /**
     * Renders the BlackHole on the simulation canvas.
     * <p>
     * The BlackHole is visually represented as a solid black circle.
     * </p>
     *
     * @param gc The {@code GraphicsContext} used to draw the BlackHole.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the BlackHole
        gc.setFill(Color.BLACK);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
