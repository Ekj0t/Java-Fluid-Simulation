import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {

    Canvas canvas;
    GraphicsContext g;
    Fluid fluid;
    int scale;

    public Renderer(Canvas canvas, Fluid fluid, int scale) {

        this.canvas = canvas;
        this.g = canvas.getGraphicsContext2D();
        this.fluid = fluid;
        this.scale = scale;
    }

    public void render() {

        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for(int i = 0; i < fluid.size; i++) {
            for(int j = 0; j < fluid.size; j++) {

                double r = fluid.densityR[i][j];
                double gVal = fluid.densityG[i][j];
                double b = fluid.densityB[i][j];

                r = Math.min(1, r / 50);
                gVal = Math.min(1, gVal / 100);
                b = Math.min(1, b / 100);

                g.setFill(Color.color(r, gVal, b));
                g.fillRect(i * scale, j * scale, scale, scale);
            }
        }
    }
}