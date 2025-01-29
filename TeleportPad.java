package Robot_Sim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a teleport pad obstacle
 * <p>
 * An object is randomly moved within the arena's boundaries when it 
 * interacts with the teleport pad. A purple circle with a yellow border 
 * serves as the visual representation of the teleport pad.
 * </p>
 */
public class TeleportPad extends AbstractObject {

    /**
     * Constructs a teleport pad with a specified position and radius.
     *
     * @param x      The x-coordinate of the teleport pad
     * @param y      The y-coordinate of the teleport pad
     * @param radius The radius of the teleport pad.
     */
    public TeleportPad(double x, double y, double radius) {
        super(x, y, radius);
    }

    /**
     * Updates the status of the teleport pad.
     * <p>
     * Since the teleport pad is static, this method is included for good practice
     * </p>
     */
    @Override
    public void update() {
        // TeleportPad is static
    }

    /**
     * Teleports the object to a random position within the arena bounds.
     *
     * @param obj The object to teleport. Its x and y coordinates is updated.
     */
    public void teleport(AbstractObject obj) {
        // Generate random coordinates within arena borders
        double randomX = Math.random() * 480 + 10; // Random X coordinate
        double randomY = Math.random() * 480 + 10; // Random Y coordinate

        // Set object's position to the new random location
        obj.x = randomX;
        obj.y = randomY;

        System.out.println("Object teleported to: (" + randomX + ", " + randomY + ")");
    }

    /**
     * Renders the teleport pad in the arena
     * <p>
     * The teleport pad is rendered as a purple circle with a yellow border.
     * </p>
     *
     * @param gc The {@code GraphicsContext} used to draw the teleport pad.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}



