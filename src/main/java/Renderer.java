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

                double d = fluid.density[i][j];

                if(d > 0) {

                    double brightness = Math.min(1, d / 100);

                    g.setFill(Color.gray(brightness));
                    g.fillRect(
                            i * scale,
                            j * scale,
                            scale,
                            scale
                    );
                }
            }
        }
    }
}