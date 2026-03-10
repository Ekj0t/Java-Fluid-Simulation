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

                double r =
                        (fluid.densityR[i][j] +
                                fluid.densityR[Math.min(i+1,fluid.size-1)][j] +
                                fluid.densityR[i][Math.min(j+1,fluid.size-1)]) / 3.0;

                double gVal =
                        (fluid.densityG[i][j] +
                                fluid.densityG[Math.min(i+1,fluid.size-1)][j] +
                                fluid.densityG[i][Math.min(j+1,fluid.size-1)]) / 3.0;

                double b =
                        (fluid.densityB[i][j] +
                                fluid.densityB[Math.min(i+1,fluid.size-1)][j] +
                                fluid.densityB[i][Math.min(j+1,fluid.size-1)]) / 3.0;

                r = Math.min(1, r / 50);
                gVal = Math.min(1, gVal / 100);
                b = Math.min(1, b / 100);

                g.setFill(Color.color(r, gVal, b));
                g.fillRect(i * scale, j * scale, scale, scale);
            }
        }
    }
}