package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a robot equipped with whisker-like sensors to avoid obstacles
 * <p>
 * The code uses its whiskers to detect nearby obstacles and changes
 * direction to avoid collisions. The animation includes a visual representation
 * of the whiskers.
 * </p>
 */
public class WhiskerRobot extends Robot {
    /** The length of the whiskers */
    private final double whiskerLength;

    /**
     * Constructs a Whisker Robot with specified position, radius, speed, and direction.
     * The length of the whiskers is double the radius.
     *
     * @param x         The x-coordinate of the robot
     * @param y         The y-coordinate of the robot
     * @param radius    The radius of the robot.
     * @param speed     The speed of the robot.
     * @param direction The initial direction of the robot in degrees.
     */
    public WhiskerRobot(double x, double y, double radius, double speed, double direction) {
        super(x, y, radius, speed, direction);
        this.whiskerLength = radius * 2; // Whisker length is double the robot's radius
    }

    /**
     * Activates the whisker sensor.
     * <p>
     * This technique is used periodically throughout the simulation to identify and react
     *  to obstacles.
     * </p>
     */
    @Override
    public void sense() {
        System.out.println("Whisker sensor activated");
    }

    /**
     * Updates the robot's state by avoiding obstacles and calling the base update method.
     * <p>
     * The robot checks for nearby obstacles using its whisker sensors and turns 90 degrees
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
     * If the robot detects an obstacle within its whisker range, it turns 90 degrees
     * to avoid the obstacle.
     * </p>
     *
     * @param arena The arena containing all objects, including obstacles.
     */
    protected void avoidObstacles(Arena arena) {
        for (AbstractObject item : arena.getObjects()) {
            if (item instanceof Obstacle) {
                double dx = item.x - this.x;
                double dy = item.y - this.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < this.radius + item.radius + 10) {
                    this.direction += 90; // Turn 90 degrees to avoid the obstacle
                    this.direction = this.direction % 360; // Normalize the direction
                }
            }
        }
    }

    /**
     * Renders the robot, including its body, wheels, and whiskers, on the canvas.
     *
     * @param gc The {@code GraphicsContext} used to draw the robot.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the robot body
        gc.setFill(Color.GREEN);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw wheels
        double wheelRadius = radius / 4;
        double wheelOffset = radius * 1.1; // Adjust to push wheels outward

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

        // Draw whiskers
        gc.setStroke(Color.RED);
        double whiskerAngle = Math.PI / 8; // Angle offset for the whiskers
        gc.setLineWidth(2);

        // Left whisker
        gc.strokeLine(
            x, 
            y, 
            x + whiskerLength * Math.cos(Math.toRadians(direction) - whiskerAngle), 
            y + whiskerLength * Math.sin(Math.toRadians(direction) - whiskerAngle)
        );

        // Right whisker
        gc.strokeLine(
            x, 
            y, 
            x + whiskerLength * Math.cos(Math.toRadians(direction) + whiskerAngle), 
            y + whiskerLength * Math.sin(Math.toRadians(direction) + whiskerAngle)
        );
    }
}


