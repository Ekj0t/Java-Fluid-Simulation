import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int SIZE = 100;
    public static final int SCALE = 6;

    Fluid fluid;
    Renderer renderer;

    double prevMouseX;
    double prevMouseY;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(SIZE * SCALE, SIZE * SCALE);

        fluid = new Fluid(0.0001, 0.0000001, 0.1);
        renderer = new Renderer(canvas, fluid, SCALE);

        canvas.setOnMousePressed(e -> {
            prevMouseX = e.getX();
            prevMouseY = e.getY();
        });

        canvas.setOnMouseDragged(e -> {

            double x = e.getX();
            double y = e.getY();

            int gridX = (int)(x / SCALE);
            int gridY = (int)(y / SCALE);

            if(e.isPrimaryButtonDown())
                fluid.addDensity(gridX, gridY, 400,0,0);

            if(e.isSecondaryButtonDown())
                fluid.addDensity(gridX, gridY, 0,400,0);

            if(e.isMiddleButtonDown())
                fluid.addDensity(gridX, gridY, 0,0,400);

            double velX = x - prevMouseX;
            double velY = y - prevMouseY;

            fluid.addVelocity(gridX, gridY, velX, velY);

            prevMouseX = x;
            prevMouseY = y;
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                fluid.step();
                renderer.render();
            }
        };

        timer.start();

        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root);

        stage.setTitle("Java Fluid Simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}