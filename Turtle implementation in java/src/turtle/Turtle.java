/*
 * JavaFX turtle implementation. Provides an animated turtle object that can
 * be programmatically controlled to draw pictures using a turtle.
 *
 * @author Bobby St. Jacques
 */
package turtle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Field;

/**
 * A JavaFX implementation of Turtle Graphics.
 */
public class Turtle {
    /**
     * The color mode used if RGB values should be specified as numbers
     * between 0.0 and 1.0. This is the default color mode.
     */
    public static final double COLOR_MODE_1 = 1.0;

    /**
     * The color mode to use if RGB values should be specified as numbers
     * between 0.0 and 255.0.
     */
    public static final double COLOR_MODE_255 = 255.0;

    /**
     * The slowest configurable speed for the Turtle (1).
     */
    public static final int SPEED_SLOWEST = 1;

    /**
     * The default speed for the Turtle (5).
     */
    public static final int SPEED_DEFAULT = 5;

    /**
     * A fast speed for the Turtle (10).
     */
    public static final int SPEED_FAST = 10;

    /**
     * The fastest speed for the Turtle (0).
     */
    public static final int SPEED_FASTEST = 0;

    /**
     * The default width of the Turtle's world in pixels.
     */
    private static final double WIDTH = 800;

    /**
     * The default height of the Turtle's world.
     */
    private static final double HEIGHT = 800;

    /**
     * Default speed in pixels per second.
     */
    private static final double PIXELS_PER_UNIT_OF_SPEED = 200;

    /**
     * The default speed at which the Turtle turns in degrees per second.
     */
    private static final double DEGREES_PER_UNIT_0F_SPEED = 90;

    /**
     * The static (singleton) Turtle.
     */
    public static final Turtle Turtle = new Turtle();

    /**
     * The polygon that represents the Turtle's arrowhead.
     */
    private Shape turtleShape;

    /**
     * The angle that the Turtle is currently facing.
     */
    private double angle;

    /**
     * The current location of the Turtle as a {@link Point2D}. This is the
     * location of the Turtle in the Turtle's world (with the origin (0,0)) in
     * the center of the canvas.
     */
    private Point2D location;

    /**
     * The JavaFX group to which all shapes are added to be rendered.
     */
    private Group root;

    /**
     * The current state of the pen; true if the pen is down.
     */
    private boolean penDown;

    /**
     * The color mode. Set to either 1.0 for 0.0-1.0 RGB values or 255 for
     * 0-255 color values.
     */
    private double colorMode;

    /**
     * The color used for the Turtle's pen.
     */
    private Color penColor;

    /**
     * The color used for the Turtle's fills.
     */
    private Color fillColor;

    /**
     * The width of the Turtle's pen.
     */
    private int width;

    /**
     * The speed at which the Turtle moves.
     */
    private double speed;

    /**
     * Indicates whether or not the tracer is enabled; if true, the Turtle
     * draws at its configured speed. If disabled, the Turtle draws nearly
     * instantaneously.
     */
    private boolean tracer;

    /**
     * The current visibility of the Turtle's world; false if the Turtle's
     * world is not currently being displayed.
     */
    private boolean notDisplayed;

    /**
     * The JavaFX application in which the Turtle runs.
     */
    private TurtleApp application;

    /**
     * Initializes the Turtle with its default settings.
     */
    private Turtle() {
        // facing east
        angle = 0;

        // default pen width
        width = 1;

        //in the center of the canvas
        location = new Point2D(0, 0);

        // the group used to draw all of the various lines and shapes
        root = new Group();

        // sets the default color mode to 1.0
        colorMode = COLOR_MODE_1;

        // set the default pen color and fill color
        penColor = Color.BLACK;
        fillColor = Color.BLACK;

        speed = SPEED_DEFAULT;

        // initialize the Turtle to be an arrow head.
        turtleShape = new Polygon(0, 0, -3.75, -5, 10, 0, -3.75, 5);
        turtleShape.setStroke(penColor);
        turtleShape.setFill(fillColor);
        turtleShape.setTranslateX(WIDTH / 2);
        turtleShape.setTranslateY(HEIGHT / 2);

        // add the Turtle to the root node...
        root.getChildren().add(turtleShape);

        // the pen starts in the down state by default
        penDown = true;

        // the tracer is enabled by default
        tracer = true;

        // by default the Turtle;s world is not displayed.
        notDisplayed = true;

        // init the application
        application = new TurtleApp();
    }

    /**
     * Enables or disables the tracer. If the tracer is enabled, the Turtle's
     * drawings are animated. If the tracer is disabled, the drawings are
     * essentially instantaneous.
     *
     * @param tracer True if the tracer should be enabled, false otherwise.
     */
    public void tracer(boolean tracer) {
        this.tracer = tracer;
    }

    /**
     * Returns the tracer's current state.
     *
     * @return True if the tracer is enabled, false otherwise.
     */
    public boolean tracer() {
        return tracer;
    }

    /**
     * Sets the color mode to either 1.0 (all RGB values are specified as
     * floating point values between 0.0 and 1.0) or 255 (all RGB values are
     * specified as floating point values between 0.0 and 255.0). All other
     * values are ignored.
     *
     * @param colorMode Either 1.0 or 255. All other values are ignored.
     */
    public void colorMode(double colorMode) {
        if(colorMode == COLOR_MODE_1) {
            this.colorMode = COLOR_MODE_1;
        }
        else if(colorMode == COLOR_MODE_255) {
            this.colorMode = COLOR_MODE_255;
        }
    }

    /**
     * Returns the current color mode; either {@link #COLOR_MODE_1} or
     * {@link #COLOR_MODE_255}.
     *
     * @return The current color mode.
     */
    public double colorMode() {
        return colorMode;
    }

    /**
     * Sets the pen's color to the color matching the specified string.
     *
     * @param color The name of the color to which the pen color should be
     *              set. Must be a valid color from the colors defined in the
     *              {@link Color} class.
     */
    public void color(String color) {
        penColor(color);
    }

    /**
     * Sets the pen's color to the specified RGB values using decimal values
     * based on the current color mode.
     *
     * @param red The value for the red channel.
     * @param green The value for the green channel.
     * @param blue The value for the red channel.
     */
    public void color(double red, double green, double blue) {
        penColor(red, green, blue);
    }

    /**
     * Sets the pen's color to the specified RGB values using decimal values
     * based on the current color mode.
     *
     * @param red The value for the red channel.
     * @param green The value for the green channel.
     * @param blue The value for the red channel.
     */
    public void penColor(double red, double green, double blue) {
        penColor(makeColor(red, green, blue));
    }

    /**
     * Sets the pen's color to the color matching the specified string.
     *
     * @param color The name of the color to which the pen color should be
     *              set. Must be a valid color from the colors defined in the
     *              {@link Color} class.
     */
    public void penColor(String color) {
        penColor(makeColor(color));
    }

    /**
     * Sets the fill color to the specified RGB values using decimal values
     * based on the current color mode.
     *
     * @param red The value for the red channel.
     * @param green The value for the green channel.
     * @param blue The value for the red channel.
     */
    public void fillColor(double red, double green, double blue) {
        fillColor(makeColor(red, green, blue));
    }

    /**
     * Sets the fill color to the color matching the specified string.
     *
     * @param color The name of the color to which the pen color should be
     *              set. Must be a valid color from the colors defined in the
     *              {@link Color} class.
     */
    public void fillColor(String color) {
        fillColor(makeColor(color));
    }

    /**
     * Sets the pen into the down (drawing) position.
     */
    public void pd() {
        penDown();
    }

    /**
     * Sets the pen into the down (drawing) position.
     */
    public void down() {
        penDown();
    }

    /**
     * Sets the pen into the down (drawing) position.
     */
    public void penDown() {
        penDown = true;
    }

    /**
     * Lifts the pen into the up (not drawing) position.
     */
    public void pu() {
        penUp();
    }

    /**
     * Lifts the pen into the up (not drawing) position.
     */
    public void up() {
        penUp();
    }

    /**
     * Lifts the pen into the up (not drawing) position.
     */
    public void penUp() {
        penDown = false;
    }


    /**
     * Moves the Turtle the specified distance in the direction that it is
     * currently facing.
     *
     * @param distance The distance to move the Turtle.
     */
    public void fd(double distance) {
        forward(distance);
    }

    /**
     * Moves the Turtle the specified distance in the direction that it is
     * currently facing.
     *
     * @param distance The distance to move the Turtle.
     */
    public void forward(double distance) {
        Point2D end = calculateEndPoint(angle, location, distance);

        setPosition(end.getX(), end.getY());
    }

    /**
     * Moves the Turtle backwards the specified distance.
     *
     * @param distance This distance to move the Turtle backwards.
     */
    public void bk(double distance) {
        backward(distance);
    }

    /**
     * Moves the Turtle backwards the specified distance.
     *
     * @param distance This distance to move the Turtle backwards.
     */
    public void back(double distance) {
        backward(distance);
    }

    /**
     * Moves the Turtle backwards the specified distance.
     *
     * @param distance This distance to move the Turtle backwards.
     */
    public void backward(double distance) {
        Point2D end = calculateEndPoint(angle + 180, location, distance);
        setPosition(end.getX(), end.getY());
    }

    /**
     * Turns the Turtle right the specified number of degrees.
     *
     * @param degrees The number of degrees to turn the Turtle to the right.
     */
    public void rt(double degrees) {
        right(degrees);
    }

    /**
     * Turns the Turtle right the specified number of degrees.
     *
     * @param degrees The number of degrees to turn the Turtle to the right.
     */
    public synchronized void right(double degrees) {
        display();

        angle += degrees;

        Timeline animation = new Timeline(
                new KeyFrame(getDuration(degrees),
                new KeyValue(turtleShape.rotateProperty(), angle)));
        animate(animation);
    }

    /**
     * Turns the Turtle left the specified number of degrees.
     *
     * @param degrees The number of degrees to turn the Turtle to the left.
     */
    public void lt(double degrees) {
        left(degrees);
    }

    /**
     * Turns the Turtle left the specified number of degrees.
     *
     * @param degrees The number of degrees to turn the Turtle to the left.
     */
    public void left(double degrees) {
        display();

        angle -= degrees;

        Timeline animation = new Timeline(
                new KeyFrame(getDuration(degrees),
                new KeyValue(turtleShape.rotateProperty(), angle)));
        animate(animation);
    }

    /**
     * Moves the Turtle to the specified x/y coordinate. If the pen is down,
     * the Turtle draws a line.
     *
     * @param x The Turtle's new x coordinate.
     * @param y The Turtle's new y coordinate.
     */
    public void goTo(double x, double y) {
        setPosition(x, y);
    }

    /**
     * Moves the Turtle to the specified x/y coordinate. If the pen is down,
     * the Turtle draws a line.
     *
     * @param x The Turtle's new x coordinate.
     * @param y The Turtle's new y coordinate.
     */
    public void setPos(double x, double y) {
        setPosition(x, y);
    }

    /**
     * Moves the Turtle to the specified x/y coordinate. If the pen is down,
     * the Turtle draws a line.
     *
     * @param x The Turtle's new x coordinate.
     * @param y The Turtle's new y coordinate.
     */
    public void setPosition(double x, double y) {
        display();

        Point2D start = translateToCoordinates(location);
        location = new Point2D(x, y);
        Point2D end = translateToCoordinates(location);

        Timeline animation = new Timeline();

        KeyValue[] keyValues = new KeyValue[penDown ? 4 : 2];
        keyValues[0] = new KeyValue(turtleShape.translateXProperty(),
                end.getX());
        keyValues[1] = new KeyValue(turtleShape.translateYProperty(),
                end.getY());

        if(penDown) {
            Line line = new Line(start.getX(), start.getY(), start.getX(),
                    start.getY());
            line.setStrokeWidth(width);
            line.setStroke(Color.TRANSPARENT);

            animation.getKeyFrames().add(new KeyFrame(Duration.ONE,
                    new KeyValue(line.strokeProperty(), penColor)));

            runInApplicationThread(() -> root.getChildren().add(line));

            keyValues[2] = new KeyValue(line.endXProperty(), end.getX());
            keyValues[3] = new KeyValue(line.endYProperty(), end.getY());
        }

        Duration duration = getDuration(start.getX(), start.getY(),
                end.getX(), end.getY());

        animation.getKeyFrames().add(
                new KeyFrame(duration, keyValues));
        animate(animation);

        runInApplicationThread(() ->  turtleShape.toFront());
    }

    /**
     * Moves the Turtle to the specified x coordinate. The Turtle's y
     * coordinate remains unchanged. If the pen is down, the Turtle draws a
     * line.
     *
     * @param x The Turtle's new x coordinate.
     */
    public void setX(double x) {
        setPosition(x, location.getY());
    }

    /**
     * Moves the Turtle to the specified y coordinate. The Turtle's x
     * coordinate remains unchanged. If the pen is down, the Turtle draws a
     * line.
     *
     * @param y The Turtle's new x coordinate.
     */
    public void setY(double y) {
        setPosition(location.getX(), y);
    }

    /**
     * Sets the width of the pen to the specified value.
     *
     * @param width The width of the pen. Must be a positive number.
     */
    public void width(int width) {
        penSize(width);
    }

    /**
     * Sets the width of the pen to the specified value.
     *
     * @param width The width of the pen. Must be a positive number.
     */
    public void penSize(int width) {
        if(width < 0) {
            throw new IllegalArgumentException("Pen width must be positive: "
                    + width);
        }
        this.width = width;
    }

    /**
     * Sets the Turtle's speed to the specified value.
     *
     * @param speed The new speed of the Turtle, a value between 1 (slow) and
     *              10 (fast) or 0 (fastest).
     */
    public void speed(int speed) {
        if(speed <= SPEED_FASTEST) {
            this.speed = SPEED_FASTEST;
        }
        else if( speed >= SPEED_FAST) {
            this.speed = SPEED_FAST;
        }
        else {
            this.speed = speed;
        }
    }

    /**
     * Sets the title displayed in the Turtle's window.
     *
     * @param title The new title.
     */
    public void title(String title) {
        display();
        runInApplicationThread(() -> {
            application.setTitle(title);
        });
    }

    /**
     * Draws a circle with the specified radius.
     *
     * @param radius The radius of the circle to draw.
     */
    public void circle(double radius) {
        display();

        int circumference = (int)Math.ceil(Math.PI * radius * 2);
        double degrees = 360.0 / circumference;

        // this is currently very slow. but it works!
        for(int i=0; i<circumference; i++) {
            forward(1);
            Turtle.left(degrees);
        }
    }

    /**
     * Sets the world coordinates for the Turtle's world.
     *
     * @param llx The x coordinate for the lower left corner of the world.
     * @param lly The y coordinate for the lower left corner of the world.
     * @param urx The x coordinate for the upper right corner of the world.
     * @param ury The y coordinate for the upper right corner of the world.
     */
    public void setWorldCoordinates(int llx, int lly, int urx, int ury) {
        display();
        // does nothing...yet
    }

    /////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS. Most of these translate turtlish stuff to JavaFX.  //
    /////////////////////////////////////////////////////////////////////////
    /**
     * Performs the specified animation. First sets the on finished handler
     * to notify the Turtle when the animation is complete. Then starts the
     * animation. Finally, waits for notification that the animation is
     * complete.
     *
     * @param animation The animation to execute.
     */
    private synchronized void animate(Animation animation) {
        // set the on finished handler to stop the Turtle from blocking
        animation.setOnFinished((e) -> {
            synchronized(Turtle.this) {
                // this will wake the Turtle from the wait state
                Turtle.this.notify();
            }
        });
        // play the animation
        animation.play();
        // wait for notification that the animation is done
        waitForNotify();
    }

    /**
     * Helper method the sole purpose of which is to avoid clogging the code
     * throughout the class with try { wait() } catch(InterruptedException){}
     * blocks whenever a wait is needed.
     */
    private synchronized void waitForNotify() {
        try {
            wait();
        } catch (InterruptedException e) {
            // squash
        }
    }

    /**
     * Runs the specified {@link Runnable} in the JavaFX application thread,
     * which is the only thread in which modifications to the user interface
     * can be performed directly. Blocks until the runner is finished.
     *
     * @param runner The {@link Runnable} to execute in the JavaFX application
     *               thread.
     */
    private synchronized void runInApplicationThread(Runnable runner) {
        // wrap the runner in another runnable that...
        Platform.runLater(() -> {
            // obtains the synchronization lock...
            synchronized(Turtle.this) {
                // starts the runner
                runner.run();
                // once it is complete, notifies the waiting Turtle
                Turtle.this.notify();
            }
        });
        // wait for notification
        waitForNotify();
    }

    /**
     * The Turtle uses a coordinate plane where the origin, (0,0) is in the
     * center of the canvas the y is positive in the UP direction. JavaFX uses
     * a coordinate plane where the origin, (0, 0), is in the top left corner
     * of the screen and y is positive in the DOWN direction. Given a Turtle
     * coordinate as a {@link Point2D}, this method will translate it into a
     * JavaFX coordinate.
     *
     * @param point The Turtle coordinate as a {@link Point2D}.
     *
     * @return The translated coordinate as a{@link Point2D}.
     */
    private Point2D translateToCoordinates(Point2D point) {
        return translateToCoordinates(point.getX(), point.getY());
    }

    /**
     * The Turtle uses a coordinate plane where the origin, (0,0) is in the
     * center of the canvas the y is positive in the UP direction. JavaFX uses
     * a coordinate plane where the origin, (0, 0), is in the top left corner
     * of the screen and y is positive in the DOWN direction. Given a Turtle
     * coordinate as an x/y pair, this method will translate it into a JavaFX
     * coordinate.
     *
     * @param x The Turtle's x coordinate.
     * @param y The Turtle's y coordinate.
     *
     * @return The translated coordinate as a {@link Point2D}.
     */
    private Point2D translateToCoordinates(double x, double y) {
        double realX = x + WIDTH / 2;
        double realY = HEIGHT / 2 - y;

        return new Point2D(realX, realY);
    }

    /**
     * Given an angle, a starting point, and a distance, calculates the end
     * point that a Turtle would arrive at.
     *
     * @param angle The angle of the Turtle.
     * @param start The starting point.
     * @param distance The distance that the Turtle should move.
     * @return The end point.
     */
    private Point2D calculateEndPoint(double angle, Point2D start,
                                      double distance) {

        double radians = Math.toRadians(angle + 90);

        // calculate the distance in the x direction
        double sine = Math.sin(radians);
        double newX = distance * sine + start.getX();

        double cosine = Math.cos(radians);
        double newY = distance * cosine + start.getY();

        return new Point2D(newX, newY);
    }

    /**
     * Given a starting and an ending point, returns the approriate duration
     * of the animation given the current speed and tracer settings.
     *
     * @param startX The starting x-coordinate.
     * @param startY The starting y-coordinate.
     * @param endX The ending y-coordinate.
     * @param endY The ending y-coordinate.
     *
     * @return The {@link Duration} that the animation should require.
     */
    private Duration getDuration(double startX, double startY,
                               double endX, double endY) {
        // if the tracer is enabled, the duration is calculated...
        if(tracer) {
            // calculate the distance
            double distance = euclidianDistance(startX, startY, endX, endY);
            // calculate the number of pixels that the Turtle should travel
            // per second
            double pixels_per_second = PIXELS_PER_UNIT_OF_SPEED *
                    getRealSpeed();
            double seconds = distance / pixels_per_second;

            return Duration.millis(seconds * 1000.0);
        }
        else {
            // if the tracer is disabled, the speed is instantaneous (1 ms).
            return Duration.ONE;
        }
    }

    /**
     * Given a number of degrees, returns the approriate duration
     * of the animation given the current speed and tracer settings.
     *
     * @param degrees The number of degrees that the Turtle is to turn.
     *
     * @return The {@link Duration} that the animation should require.
     */
    private Duration getDuration(double degrees) {
        if(tracer) {
            double degrees_per_second = DEGREES_PER_UNIT_0F_SPEED *
                    getRealSpeed();
            double seconds = degrees / degrees_per_second;

            return Duration.millis(seconds * 1000);
        }
        else {
            return Duration.ONE;
        }
    }

    /**
     * Translates returns a real speed representing the Turtle's current
     * speed. For values 1 to 10, simple returns the speed. For 0, returns an
     * appropriately, ridiculously fast speed.
     *
     * @return The speed of the Turtle based on the current speed setting.
     */
    private double getRealSpeed() {
        return speed == SPEED_FASTEST ? 1000 : speed;
    }

    /**
     * Sets the Turtle's pen color using an animation to insure that it occurs
     * at the appropriate point in time (otherwise the color would change in
     * the midst of other animations; nonsensical).
     *
     * @param color The new pen color.
     */
    private void penColor(Color color) {
        penColor = color;

        Timeline animation = new Timeline(new KeyFrame(Duration.ONE,
                new KeyValue(turtleShape.strokeProperty(), color)));
        animate(animation);
    }

    /**
     * Sets the Turtle's fill color using an animation to insure that it
     * occurs at the appropriate point in time (otherwise the color would
     * change in the midst of other animations; nonsensical).
     *
     * @param color The new fill color.
     */
    private void fillColor(Color color) {
        fillColor = color;

        Timeline animation = new Timeline(new KeyFrame(Duration.ONE,
                new KeyValue(turtleShape.fillProperty(), color)));
        animate(animation);
    }

    /**
     * Helper method that makes a new {@link Color color} from the provided
     * RGB values. The provided values must be compatible with the current
     * color mode.
     *
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     *
     * @return The new {@link Color}.
     */
    private Color makeColor(double red, double green, double blue) {
        if(colorMode == COLOR_MODE_255) {
            red = red / 255.0;
            green = green / 255.0;
            blue = blue / 255.0;
        }

        if(red < 0 || red > 1 ||
                green < 0 || green > 1 ||
                blue < 0 || blue > 1 ) {
            throw new IllegalArgumentException("bad color sequence: (" +
                    red + ", " + green + ", " + blue + ")");
        }

        return new Color(red, green, blue, 1.0);
    }

    /**
     * Makes a {@link Color} from the specified string. Throws an illegal
     * argument exception of there is not a color constant in the
     * {@link Color} class with the specified name.
     *
     * @param color The name of the color.
     *
     * @return The corresponding {@link Color}.
     */
    private Color makeColor(String color) {
        try {
            Field theColor = Color.class.getField(color.toUpperCase());
            return (Color)theColor.get(null);
        }
        catch(Exception e) {
            throw new IllegalArgumentException("bad color string: " + color);
        }
    }

    /**
     * Calculates the Euclidian distance between two points.
     * @param startX The x coordinate of the first point.
     * @param startY The y coordinate of the first point.
     * @param endX The x coordinate of the second point.
     * @param endY The y coordinate of the second point.
     * @return The distance between the two points.
     */
    private double euclidianDistance(double startX, double startY,
                                     double endX, double endY) {
        Point2D start = new Point2D(startX, startY);
        Point2D end = new Point2D(endX, endY);

        return start.distance(end);
    }

    /**
     * If the JavaFX Turtle application is not yet displayed, this method will
     * display it. It is called automatically from any method that moves the
     * Turtle. This method blocks until the application has started.
     */
    private synchronized void display() {
        if(notDisplayed) {
            // initialize the JavaFX platform
            Platform.startup(() -> {
                //synchronized(Turtle.this) {
                //    Turtle.this.notify();
                //}
            });
            // wait for the platform to startup
            //waitForNotify();

            // launch the Turtle application
            Platform.runLater(() -> {
                try {
                    application.start(new Stage());
                } catch (Exception e) {
                    // squash
                }
                synchronized(Turtle.this) {
                    Turtle.this.notify();
                }
            });
            // wait for the application to start up
            waitForNotify();
            notDisplayed = false;
        }
    }

    /**
     * The Turtle application. Displays the Turtle's world in a JavaFX window.
     */
    private static class TurtleApp extends Application {
        /**
         * The main {@link Stage} for the application.
         */
        private Stage stage;

        /**
         * The title of the application.
         */
        private String title;

        /**
         * Constructs the app.
         */
        TurtleApp() {
            // default title
            title = "JTurtle!";
        }

        /**
         * Starts the application.
         *
         * @param primaryStage The {@link Stage} on which the Turtle will
         *                     draw.
         */
        @Override
        public void start(Stage primaryStage) {
            Scene scene = new Scene(Turtle.root, WIDTH, HEIGHT,
                    Color.WHITE);

            stage = primaryStage;

            stage.setTitle(title);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }

        /**
         * Updates the title used by the primary stage.
         *
         * @param title The new title for the stage.
         */
        public void setTitle(String title) {
            this.title = title;

            if(stage != null) {
                stage.setTitle(title);
            }
        }
    }
}
