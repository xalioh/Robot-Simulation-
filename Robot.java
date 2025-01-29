package Robot_Sim;

/**
 * Abstract base class for all robot types in the simulation.
 * <p>
 * The {@code Robot} class extends {@code AbstractObject} and adds functionality for
 * movement, collision handling, and robot-specific sensing. It serves as a blueprint
 * for all robot implementations, such as {@code WhiskerRobot} and {@code BeamSensorRobot}.
 * </p>
 */
public abstract class Robot extends AbstractObject {
    /** The speed of the robot. */
    protected double speed;

    /** The direction of the robot's movement, expressed as an angle in degrees. */
    protected double direction;

    /**
     * Constructs a {@code Robot} with the specified position, radius, speed, and direction.
     *
     * @param x         The x-coordinate of the robot. 
     * @param y         The y-coordinate of the robot.
     * @param radius    The radius of the robot.
     * @param speed     The speed of the robot.
     * @param direction The initial direction of the robot in degrees.
     */
    public Robot(double x, double y, double radius, double speed, double direction) {
        super(x, y, radius);
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Abstract method for robot-specific sensing functionality.
     * <p>
     * Subclasses must implement this method to define how the robot detects its
     * environment (e.g., using whiskers, beams, or other sensors).
     * </p>
     */
    public abstract void sense();

    /**
     * Updates the robot's position based on its speed and direction.
     * <p>
     * The robot's position is adjusted, and collisions with the arena walls are handled
     * by reversing its direction if it moves out of bounds. The direction is normalized
     * to stay within the range of 0 to 360 degrees.
     * </p>
     */
    @Override
    public void update() {
        // Update position based on speed and direction
        x += speed * Math.cos(Math.toRadians(direction));
        y += speed * Math.sin(Math.toRadians(direction));

        // Handle collision with arena walls
        if (x - radius < 0) {
            x = radius; // Keep within left border
            direction = 180 - direction;
        } else if (x + radius > 500) {
            x = 500 - radius; // Keep within right border
            direction = 180 - direction;
        }

        if (y - radius < 0) {
            y = radius; // Keep within top border
            direction = -direction;
        } else if (y + radius > 500) {
            y = 500 - radius; // Keep within bottom border
            direction = -direction;
        }

        // Normalize direction
        direction = (direction + 360) % 360;
    }

    /**
     * Checks if the robot is colliding with another object.
     *
     * @param other The {@code AbstractObject} to check for a collision.
     * @return {@code true} if the robot is colliding with the other object, {@code false} otherwise.
     */
    public boolean checkCollision(AbstractObject other) {
        double distance = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        return distance < (this.radius + other.radius);
    }

    /**
     * Handles collisions with another object by reversing the robot's direction.
     *
     * @param other The {@code AbstractObject} involved in the collision.
     */
    public void handleCollision(AbstractObject other) {
        if (checkCollision(other)) {
            this.direction = (this.direction + 180) % 360;
        }
    }
}




