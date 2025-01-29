package Robot_Sim;

/**
 * Base abstract class for all objects in the arena
 * 
 * <p>
 * This class is responsible for common properties of objects (position and radius) 
 * and contains methods to update and render objects
 * </p>
 */

public abstract class AbstractObject {
    protected double x;		// x-coordinate of the object
    protected double y;		// y-coordinate of the object
    protected double radius; //radius of the object

   /**
    * Builds an AbstractObject with specific position and radius
    * 
    * 
    * @param x The x-coordinate of object
    * @param y The y-coordinate of object
    * @param radius The radius of object
    */
    public AbstractObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Responsible for updating the status of the object
     * 
     * Subclasses implement this method to change the status of an object in the arena
     * 
     */
    public abstract void update();

    /**
     * Returns x-coordinate of object
     * 
     * @return The x-coordinate of the object 
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y-coordinate of the object
     * 
     * @return The y-coordinate of the object
     */
    public double getY() {
        return y;
    }

    /**
     * Returns radius of the object 
     * 
     * @return The radius of the object
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Renders objects on the canvas
     * 
     * Subclasses implement this method to show how the objects are presented in the arena
     * 
     * 
     * @param gc GraphicsContext used to display the object
     */
    public abstract void render(javafx.scene.canvas.GraphicsContext gc);
    
    
    }

