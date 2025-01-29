package Robot_Sim;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * The main class for the robot simulation application.
 * <p>
 * The {@code Simulation} class creates a graphical interface for simulating a dynamic arena
 * with user-controlled and autonomous robots, as well as certain obstacles. It includes
 * functionality for adding objects, controlling a robot, saving/loading arena configurations,
 * and viewing an information panel.
 * </p>
 */

public class Simulation extends Application {
    private javafx.animation.AnimationTimer animationTimer;
    private boolean isRunning = false;
    private AbstractObject selectedObject = null; // Tracks the currently selected object
    private ControlBot controlBot; // ControlBot reference
    private boolean isMovingUp = false, isMovingDown = false, isMovingLeft = false, isMovingRight = false;
    private Text infoText;
    
    
    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage The primary stage for this application.
     */

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new javafx.geometry.Insets(0)); // Remove any default padding


        

        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);
        Arena arena = new Arena();

        // Information panel
        VBox infoPanel = new VBox();
        infoPanel.setLayoutX(520);
        infoPanel.setLayoutY(10);
        infoPanel.setSpacing(10);
        root.setRight(infoPanel);
        
        //Information

        Text panelTitle = new Text("Arena Information");
        panelTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        infoText = new Text("No data available yet.");
        infoText.setWrappingWidth(200);
        infoText.setTextAlignment(TextAlignment.LEFT);

        infoPanel.getChildren().addAll(panelTitle, infoText);

        //Create a ComboBox for robot selection
        ComboBox<String> robotSelector = new ComboBox<>();
        robotSelector.getItems().addAll("BumpSensorRobot", "WhiskerRobot", "BeamSensorRobot", "ControlBot");
        robotSelector.setValue("BumpSensorRobot"); // Default 
        
        //Create a ComboBox for obstacle selection
        ComboBox<String> obstacleSelector = new ComboBox<>();
        obstacleSelector.getItems().addAll("Normal Obstacle", "TeleportPad", "BlackHole");
        obstacleSelector.setValue("Normal Obstacle"); // Default 


        
        //Buttons for controlling animation and deleting objects
        Button addRobotButton = new Button("Add Selected Robot");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button newArenaButton = new Button("New Arena");
        Button deleteButton = new Button("Delete Selected"); 
        Button addObstacleButton = new Button("Add Selected Obstacle");
        Button aboutButton = new Button("About");
        Button helpButton = new Button("Help");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        //Top button menu
        HBox topMenu = new HBox(10, aboutButton, helpButton, saveButton, loadButton);
        topMenu.setAlignment(Pos.TOP_LEFT); 
        topMenu.setPadding(new javafx.geometry.Insets(10, 0, 0, 10));
        root.setTop(topMenu);

        //Bottom button menu
        HBox buttonBox = new HBox(10, robotSelector, addRobotButton, obstacleSelector, addObstacleButton, startButton, stopButton, newArenaButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new javafx.geometry.Insets(10, 0, 10, 0)); 
        root.setBottom(buttonBox);
        buttonBox.setLayoutX(10);
        buttonBox.setLayoutY(450);

        

        // Animation loop
        animationTimer = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Draw arena borders
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(3);
                gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Render arena objects
                arena.updateObjects();
                for (AbstractObject obj : arena.getObjects()) {
                    if (obj == selectedObject) {
                        // Highlight selected object
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(2);
                        gc.strokeOval(obj.getX() - obj.getRadius() - 2, obj.getY() - obj.getRadius() - 2,
                                obj.getRadius() * 2 + 4, obj.getRadius() * 2 + 4);
                    }
                    obj.render(gc);
                }

                // Handle ControlBot movement
                if (controlBot != null) {
                    if (isMovingUp) controlBot.moveUp();
                    if (isMovingDown) controlBot.moveDown();
                    if (isMovingLeft) controlBot.moveLeft();
                    if (isMovingRight) controlBot.moveRight();
                }

                // Update the information panel
                updateInfoPanel(arena);
            }
        };

        startButton.setOnAction(e -> {
            if (!isRunning) {
                animationTimer.start();
                isRunning = true;
                selectedObject = null; // Disable selection while running
            }
        });

        stopButton.setOnAction(e -> {
            if (isRunning) {
                animationTimer.stop();
                isRunning = false;
            }
        });

        newArenaButton.setOnAction(e -> {
            arena.clearObjects();
            controlBot = null; // Reset ControlBot when clearing arena
            selectedObject = null; // Clear selection
            updateInfoPanel(arena); // Ensure panel updates after clearing
        });

        addRobotButton.setOnAction(e -> {
            String selectedRobot = robotSelector.getValue();
            switch (selectedRobot) {
                case "BumpSensorRobot":
                    arena.addObject(arena.createRandomRobot());
                    break;
                case "WhiskerRobot":
                    arena.addObject(arena.createRandomWhiskerRobot());
                    break;
                case "BeamSensorRobot":
                    arena.addObject(arena.createRandomBeamSensorRobot());
                    break;
                case "ControlBot":
                    if (controlBot == null) {
                        controlBot = new ControlBot(250, 250, 15, 2, 0); // Initial position and size
                        arena.addObject(controlBot);
                    }
                    break;
            }
            updateInfoPanel(arena); // Update panel after adding a robot
        });
        
        
        addObstacleButton.setOnAction(e -> {
            String selectedObstacle = obstacleSelector.getValue();
            switch (selectedObstacle) {
                case "Normal Obstacle":
                    arena.addObject(arena.createRandomObstacle()); // Normal obstacle creation method
                    break;
                case "TeleportPad":
                    arena.addObject(arena.createRandomTeleportPad()); // TeleportPad creation method
                    break;
                case "BlackHole":
                    arena.addObject(arena.createRandomBlackHole()); // BlackHole creation method
                    break;
            }
            updateInfoPanel(arena); // Update the information panel after adding an obstacle
        });

     // About button action
        aboutButton.setOnAction(e -> {
            javafx.scene.control.Alert aboutAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("About");
            aboutAlert.setHeaderText("About the Robot Simulation Project");
            aboutAlert.setContentText("This project simulates robots in a dynamic arena with various obstacles.\n"
                    + "Developed by: Sarthak Fouzdar\n"
                    + "Student Number: 32011790");
            aboutAlert.showAndWait();
        });

        // Help button action
        helpButton.setOnAction(e -> {
            javafx.scene.control.Alert helpAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            helpAlert.setTitle("Help");
            helpAlert.setHeaderText("How to Use the Simulation");
            helpAlert.setContentText(
                    "Buttons:\n"
                    + "- Add Selected Robot: Adds a robot based on the selected type in the dropdown.\n"
                    + "- Add Selected Obstacle: Adds an obstacle based on the selected type in the dropdown.\n"
                    + "- Start: Starts the simulation.\n"
                    + "- Stop: Stops the simulation.\n"
                    + "- New Arena: Clears all robots and obstacles from the arena.\n"
                    + "- Delete Selected: Removes the currently selected robot or obstacle from the arena.\n"
                    + "Controls:\n"
                    + "- Use W/A/S/D to control the ControlBot."
            );
            helpAlert.showAndWait();
        });
        
        saveButton.setOnAction(e -> {
            System.out.println("Save button clicked");
            saveConfiguration(primaryStage);
        });
        loadButton.setOnAction(e -> {
            System.out.println("Load button clicked");
            loadConfiguration(primaryStage);
        });





        canvas.setOnMousePressed(e -> {
            if (!isRunning) {
                // Check if a robot or obstacle is clicked
                selectedObject = arena.getObjects().stream()
                        .filter(obj -> obj.getX() - obj.getRadius() <= e.getX()
                                && obj.getX() + obj.getRadius() >= e.getX()
                                && obj.getY() - obj.getRadius() <= e.getY()
                                && obj.getY() + obj.getRadius() >= e.getY())
                        .findFirst()
                        .orElse(null);

                // Update the information panel to show the selected object
                updateInfoPanel(arena);

                // Refresh the canvas to highlight the selected object
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (AbstractObject obj : arena.getObjects()) {
                    if (obj == selectedObject) {
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(2);
                        gc.strokeOval(obj.getX() - obj.getRadius() - 2, obj.getY() - obj.getRadius() - 2,
                                obj.getRadius() * 2 + 4, obj.getRadius() * 2 + 4);
                    }
                    obj.render(gc);
                }
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (!isRunning && selectedObject != null) {
                // Drag the selected object
                selectedObject.x = e.getX();
                selectedObject.y = e.getY();

                // Render the arena manually during drag
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (AbstractObject obj : arena.getObjects()) {
                    if (obj == selectedObject) {
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(2);
                        gc.strokeOval(obj.getX() - obj.getRadius() - 2, obj.getY() - obj.getRadius() - 2,
                                obj.getRadius() * 2 + 4, obj.getRadius() * 2 + 4);
                    }
                    obj.render(gc);
                }

                // Update the information panel
                updateInfoPanel(arena);
            }
        });

        deleteButton.setOnAction(e -> {
            if (selectedObject != null) {
                // Remove the selected object from the arena
                arena.getObjects().remove(selectedObject);

                // Clear selection
                if (selectedObject == controlBot) {
                    controlBot = null; // Reset controlBot reference if it was deleted
                }
                selectedObject = null;

                // Refresh the canvas
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (AbstractObject obj : arena.getObjects()) {
                    obj.render(gc);
                }

                // Update the information panel
                updateInfoPanel(arena);
            }
        });


        // Key event handling for ControlBot
        Scene scene = new Scene(root, 920, 600); // Increased width for info panel
        scene.setOnKeyPressed(e -> {
            if (controlBot != null) {
                if (e.getCode() == KeyCode.W) isMovingUp = true;
                if (e.getCode() == KeyCode.S) isMovingDown = true;
                if (e.getCode() == KeyCode.A) isMovingLeft = true;
                if (e.getCode() == KeyCode.D) isMovingRight = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            if (controlBot != null) {
                if (e.getCode() == KeyCode.W) isMovingUp = false;
                if (e.getCode() == KeyCode.S) isMovingDown = false;
                if (e.getCode() == KeyCode.A) isMovingLeft = false;
                if (e.getCode() == KeyCode.D) isMovingRight = false;
            }
        });

       
        primaryStage.setTitle("Robot Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Updates the information panel with info about the arena, including the number of robots and obstacles,
     * and the status of the {@code ControlBot} and any selected object.
     *
     * @param arena The {@code Arena} instance containing the robots and obstacles to display information about.
     */
    private void updateInfoPanel(Arena arena) {
        StringBuilder info = new StringBuilder();
        info.append("Robots: ").append(arena.getRobotsCount()).append("\n");
        info.append("Obstacles: ").append(arena.getObstaclesCount()).append("\n");
        if (controlBot != null) {
            info.append("\nControlBot Position:\n");
            info.append("  X: ").append((int) controlBot.getX()).append("\n");
            info.append("  Y: ").append((int) controlBot.getY()).append("\n");
        } else {
            info.append("\nControlBot: Not added\n");
        }
        if (selectedObject != null) {
            info.append("\nSelected Object:\n");
            info.append("  Type: ").append(selectedObject.getClass().getSimpleName()).append("\n");
            info.append("  X: ").append((int) selectedObject.getX()).append("\n");
            info.append("  Y: ").append((int) selectedObject.getY()).append("\n");
        } else {
            info.append("\nSelected Object: None\n");
        }
        infoText.setText(info.toString());
    }
    
    /**
     * Saves the current configuration of the arena to a file.
     *
     * @param stage The primary stage, used to show a file chooser dialog.
     */
    private void saveConfiguration(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                StringBuilder data = new StringBuilder();
                for (AbstractObject obj : Arena.getInstance().getObjects()) {
                    data.append(obj.getClass().getSimpleName()).append(",")
                            .append(obj.getX()).append(",")
                            .append(obj.getY()).append(",")
                            .append(obj.getRadius()).append("\n");
                }
                Files.write(file.toPath(), data.toString().getBytes());
                System.out.println("Configuration saved successfully.");
            } catch (IOException e) {
                System.err.println("Failed to save configuration: " + e.getMessage());
            }
        }
    }

    
    /**
     * Loads a configuration file and updates the arena accordingly.
     *
     * @param stage The primary stage, used to show a file chooser dialog.
     */

    private void loadConfiguration(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Arena.getInstance().clearObjects();
                List<String> lines = Files.readAllLines(file.toPath());
                for (String line : lines) {
                    String[] parts = line.split(",");
                    String type = parts[0];
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double radius = Double.parseDouble(parts[3]);

                    switch (type) {
                        case "BumpSensorRobot":
                            Arena.getInstance().addObject(new BumpSensorRobot(x, y, radius, 2, 0));
                            break;
                        case "WhiskerRobot":
                            Arena.getInstance().addObject(new WhiskerRobot(x, y, radius, 2, 0));
                            break;
                        case "Obstacle":
                            Arena.getInstance().addObject(new Obstacle(x, y, radius));
                            break;
                        case "TeleportPad":
                            Arena.getInstance().addObject(new TeleportPad(x, y, radius));
                            break;
                        case "BlackHole":
                            Arena.getInstance().addObject(new BlackHole(x, y, radius));
                            break;
                    }
                }
                System.out.println("Configuration loaded successfully.");
            } catch (IOException e) {
                System.err.println("Failed to load configuration: " + e.getMessage());
            }
        }
    }



    /**
     * The main start point for launching the application.
     *
     * @param args The command-line arguments.
     */
    
    public static void main(String[] args) {
        launch(args);
    }
}










