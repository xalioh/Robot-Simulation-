package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a robot equipped with bump sensors for detecting collisions.
 * <p>
 * The {@code BumpSensorRobot} uses bump sensors to detect obstacles and react accordingly.
 * It is visually represented with a blue body and four wheels.
 * </p>
 */
public class BumpSensorRobot extends Robot {

    /**
     * Constructs a {@code BumpSensorRobot} with specified position, radius, speed, and direction.
     *
     * @param x         The x-coordinate of the robot's initial position.
     * @param y         The y-coordinate of the robot's initial position.
     * @param radius    The radius of the robot.
     * @param speed     The speed of the robot.
     * @param direction The initial direction of the robot in degrees.
     */
    public BumpSensorRobot(double x, double y, double radius, double speed, double direction) {
        super(x, y, radius, speed, direction);
    }

    /**
     * Activates the bump sensor.
     * <p>
     * This method simulates the activation of bump sensors for detecting collisions.
     * </p>
     */
    @Override
    public void sense() {
        System.out.println("Bump sensor activated");
    }

    /**
     * Renders the robot, including its body and wheels, on the simulation canvas.
     * <p>
     * The robot's body is represented as a blue circle, and its wheels are drawn
     * as black circles positioned around the body.
     * </p>
     *
     * @param gc The {@code GraphicsContext} used to draw the robot.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the robot body
        gc.setFill(Color.BLUE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw wheels
        double wheelRadius = radius / 4;
        double wheelOffset = radius * 1.1;

        gc.setFill(Color.BLACK);

        // Top-left wheel
        gc.fillOval(
            x - wheelOffset * Math.cos(Math.toRadians(direction) + Math.PI / 4) - wheelRadius,
            y - wheelOffset * Math.sin(Math.toRadians(direction) + Math.PI / 4) - wheelRadius,
            wheelRadius * 2, wheelRadius * 2);

        // Top-right wheel
        gc.fillOval(
            x - wheelOffset * Math.cos(Math.toRadians(direction) - Math.PI / 4) - wheelRadius,
            y - wheelOffset * Math.sin(Math.toRadians(direction) - Math.PI / 4) - wheelRadius,
            wheelRadius * 2, wheelRadius * 2);

        // Bottom-left wheel
        gc.fillOval(
            x + wheelOffset * Math.cos(Math.toRadians(direction) + Math.PI / 4) - wheelRadius,
            y + wheelOffset * Math.sin(Math.toRadians(direction) + Math.PI / 4) - wheelRadius,
            wheelRadius * 2, wheelRadius * 2);

        // Bottom-right wheel
        gc.fillOval(
            x + wheelOffset * Math.cos(Math.toRadians(direction) - Math.PI / 4) - wheelRadius,
            y + wheelOffset * Math.sin(Math.toRadians(direction) - Math.PI / 4) - wheelRadius,
            wheelRadius * 2, wheelRadius * 2);
    }
}


