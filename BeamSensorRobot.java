package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a robot equipped with a beam sensor to avoid obstacles.
 * <p>
 * The code detects obstacles in its path using a beam and changes course
 *  to prevent collisions. The simulation includes visual representations 
 *  of the robot's body, wheels, and beam sensor.
 * </p>
 */
public class BeamSensorRobot extends Robot {

    /**
     * Constructs a Beam Sensor Robot with specified position, radius, speed, and direction.
     *
     * @param x         The x-coordinate of the robot.
     * @param y         The y-coordinate of the robot.
     * @param radius    The radius of the robot.
     * @param speed     The speed of the robot.
     * @param direction The initial direction of the robot in degrees.
     */
    public BeamSensorRobot(double x, double y, double radius, double speed, double direction) {
        super(x, y, radius, speed, direction);
    }

    /**
     * Activates the beam sensor.
     * <p>
     * This technique is used periodically throughout the simulation to identify and react
     * to obstacles.
     * </p>
     */
    @Override
    public void sense() {
        System.out.println("Beam sensor activated");
    }

    /**
     * Avoids obstacles and updates its location to update the robot's state.
     * <p>
     * The robot checks for nearby obstacles using its beam sensor and turns 67 degrees
     * to avoid collisions.
     * </p>
     */
    @Override
    public void update() {
        avoidObstacles(Arena.getInstance());
        super.update();
    }

    /**
     * Detects and avoids nearby obstacles in the arena.
     * <p>
     * If the robot detects an obstacle within its beam range, it turns 67 degrees to
     * avoid the obstacle.
     * </p>
     *
     * @param arena The arena containing all objects.
     */
    private void avoidObstacles(Arena arena) {
        for (AbstractObject item : arena.getObjects()) {
            if (item instanceof Obstacle) {
                double dx = item.getX() - this.x;
                double dy = item.getY() - this.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                // Check if obstacle is in the robot's path (within a certain distance threshold)
                if (distance < this.radius + item.getRadius() + 20) {
                    this.direction += 67; // Turn 67 degrees to avoid the obstacle
                    this.direction = this.direction % 360; // Normalise the direction
                }
            }
        }
    }

    /**
     * Renders the robot, including its body, wheels, and beam sensor, on the simulation canvas.
     *
     * @param gc The {@code GraphicsContext} used to draw the robot.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the robot body
        gc.setFill(Color.ORANGE);
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

        // Draw the beam sensor line
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(
            x, 
            y, 
            x + (radius + 20) * Math.cos(Math.toRadians(direction)),
            y + (radius + 20) * Math.sin(Math.toRadians(direction))
        );
    }
}

