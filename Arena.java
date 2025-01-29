package Robot_Sim;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Arena class manages all objects and their interactions in the simulation environment.
 * 
 * This class supports functionalities of objects and its special interactions such as teleport, black hole absorption
 * and collisions. 
 * 
 * 
 */

public class Arena {
 private ArrayList<AbstractObject> objects; 	// List of every object in the arena
 private static final Random random = new Random();	// Instance for random generation
 private static Arena instance;
 
/**
 * Responsible for initialisation of default objects
 * 
 * 
 * Adds two robots and two obstacles as default arena
 * 
 */

 public Arena() {
	 
     objects = new ArrayList<>();
     instance = this;
     // Add robots
     addObject(createRandomRobot());
     addObject(createRandomRobot());
     // Add obstacles
     addObject(createRandomObstacle());
     addObject(createRandomObstacle());
 }

 
 /**
  * Returns single instance of the arena
  * 
  * 
  * @return single instance of the arena
  */
 public static Arena getInstance() {
     return instance;
 }
 
 
 /**
  * Adds an object to the arena
  * @param obj The object (Robot or Obstacle)
  */

 public void addObject(AbstractObject obj) {
     objects.add(obj);
 }
 
 /**
  * Clears objects from the arena
  */

 public void clearObjects() {
     objects.clear();
 }

 /**
  * Updates the status of objects in the arena
  * 
  * <p>
  * This handles movement, detection of collisions, and special interactions (black hole absorption and teleportation)
  */
 
 public void updateObjects() {
     // Update objects
     for (AbstractObject obj : objects) {
         obj.update();
     }

  // Check for robot absorption by black holes
     objects.removeIf(obj -> {
         if (obj instanceof Robot) {
             Robot robot = (Robot) obj;
             for (AbstractObject obstacle : objects) {
                 if (obstacle instanceof BlackHole && ((BlackHole) obstacle).absorbs(robot)) {
                     System.out.println("Robot " + robot + " was absorbed by a BlackHole!");
                     return true; // Remove the robot
                 }
             }
         }
         return false;
     });
     
     // Check for all types of collisions
     for (int i = 0; i < objects.size(); i++) {
         AbstractObject obj1 = objects.get(i);
         
      // Check for robot to teleport pad interaction
         for (AbstractObject obj : objects) {
             if (obj instanceof Robot) {
                 Robot robot = (Robot) obj;
                 for (AbstractObject pad : objects) {
                     if (pad instanceof TeleportPad && robot.checkCollision(pad)) {
                         ((TeleportPad) pad).teleport(robot); // Teleport the robot
                     }
                 }
             }
         }

         if (obj1 instanceof Robot) {
             Robot robot = (Robot) obj1;

             // Check for collisions with other objects
             for (int j = 0; j < objects.size(); j++) {
                 if (i != j) {
                     AbstractObject obj2 = objects.get(j);

                     if (obj2 instanceof Obstacle && robot.checkCollision(obj2)) {
                         robot.handleCollision(obj2); // Handle robot to obstacle collision
                     } else if (obj2 instanceof Robot && robot.checkCollision(obj2)) {
                         robot.handleCollision(obj2); // Handle robot to robot collision
                     } else if (obj2 instanceof TeleportPad && robot.checkCollision(obj2)) {
                         ((TeleportPad) obj2).teleport(robot); // Handle robot to teleport interaction
                     }
                 }
             }
         }
     }
 }


 /**
  * Retrieves a list of objects currently in the arena
  *
  * @return An ArrayList containing all objects in the arena
  */
 public ArrayList<AbstractObject> getObjects() {
     return objects;
 }

 /**
  * Creates a randomly positioned and directed {@code BumpSensorRobot}.
  * <p>
  * The robot is placed within the bounds of the arena with a fixed radius
  * and a random speed and direction.
  * </p>
  *
  * @return A new instance of {@code BumpSensorRobot}.
  */
 public Robot createRandomRobot() {
     double x = random.nextInt(480) + 10; // Ensure robot is within bounds
     double y = random.nextInt(480) + 10; // Ensure robot is within bounds
     double radius = 15;
     double speed = 3;
     double direction = random.nextInt(360);
     return new BumpSensorRobot(x, y, radius, speed, direction);
 }

 /**
  * Creates a randomly positioned and directed {@code WhiskerRobot}.
  * <p>
  * The robot is placed within the bounds of the arena with a fixed radius
  * and a random speed and direction.
  * </p>
  *
  * @return A new instance of {@code WhiskerRobot}.
  */
 public Robot createRandomWhiskerRobot() {
     double x = random.nextInt(480) + 10; // Ensure robot is within bounds
     double y = random.nextInt(480) + 10; // Ensure robot is within bounds
     double radius = 15;
     double speed = 4;
     double direction = random.nextInt(360);
     return new WhiskerRobot(x, y, radius, speed, direction);
 }

 /**
  * Creates a randomly positioned {@code Obstacle}.
  * <p>
  * The obstacle is placed within the bounds of the arena with a fixed size.
  * </p>
  *
  * @return A new instance of {@code Obstacle}.
  */
 public Obstacle createRandomObstacle() {
     double x = random.nextInt(480) + 10; // Ensure obstacle is within bounds
     double y = random.nextInt(480) + 10; // Ensure obstacle is within bounds
     double radius = 20; // Size of the obstacle
     return new Obstacle(x, y, radius);
 }

 /**
  * Creates a randomly positioned and directed {@code BeamSensorRobot}.
  * <p>
  * The robot is placed within the bounds of the arena with a fixed radius,
  * random speed, and direction.
  * </p>
  *
  * @return A new instance of {@code BeamSensorRobot}.
  */
 public Robot createRandomBeamSensorRobot() {
     double x = random.nextInt(480) + 10;
     double y = random.nextInt(480) + 10;
     double radius = 10;
     double speed = 5;
     double direction = random.nextInt(360);
     return new BeamSensorRobot(x, y, radius, speed, direction);
 }

 /**
  * Creates a randomly positioned {@code TeleportPad}.
  * <p>
  * The teleport pad is placed within the bounds of the arena with a fixed radius.
  * </p>
  *
  * @return A new instance of {@code TeleportPad}.
  */
 public TeleportPad createRandomTeleportPad() {
     double x = random.nextInt(480) + 10; // Ensure within bounds
     double y = random.nextInt(480) + 10; 
     double radius = 15; // Size of the teleport pad
     return new TeleportPad(x, y, radius);
 }

 /**
  * Creates a randomly positioned {@code BlackHole}.
  * <p>
  * The black hole is placed within the bounds of the arena with a fixed radius.
  * </p>
  *
  * @return A new instance of {@code BlackHole}.
  */
 public BlackHole createRandomBlackHole() {
     double x = random.nextInt(480) + 10; // Ensure within bounds
     double y = random.nextInt(480) + 10;
     double radius = 20; // Size of the BlackHole
     return new BlackHole(x, y, radius);
 }

 /**
  * Counts the number of {@code Robot} objects currently in the arena.
  *
  * @return The total count of robots in the arena.
  */
 public int getRobotsCount() {
     return (int) objects.stream().filter(obj -> obj instanceof Robot).count();
 }

 /**
  * Counts the number of {@code Obstacle} objects currently in the arena.
  *
  * @return The total count of obstacles in the arena.
  */
 public int getObstaclesCount() {
     return (int) objects.stream().filter(obj -> obj instanceof Obstacle).count();
 }
}

