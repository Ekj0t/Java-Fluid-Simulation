public class Fluid {

    int size = 100;
    int iter = 16;

    double diffusion;
    double viscosity;
    double dt;

    public double[][] density;

    double[][] s;

    double[][] Vx;
    double[][] Vy;

    double[][] Vx0;
    double[][] Vy0;

    public Fluid(double diffusion, double viscosity, double dt){

        this.diffusion = diffusion;
        this.viscosity = viscosity;
        this.dt = dt;

        density = new double[size][size];
        s = new double[size][size];

        Vx = new double[size][size];
        Vy = new double[size][size];

        Vx0 = new double[size][size];
        Vy0 = new double[size][size];
    }

    public void addDensity(int x, int y, double amount){
        if(x < 0 || x >= size || y < 0 || y >= size) return;
        density[x][y] += amount;
    }

    public void addVelocity(int x, int y, double amountX, double amountY){
        if(x < 0 || x >= size || y < 0 || y >= size) return;
        Vx[x][y] += amountX;
        Vy[x][y] += amountY;
    }

    public void step(){

        diffuse(1, Vx0, Vx, viscosity);
        diffuse(2, Vy0, Vy, viscosity);

        project(Vx0, Vy0, Vx, Vy);

        advect(1, Vx, Vx0, Vx0, Vy0);
        advect(2, Vy, Vy0, Vx0, Vy0);

        project(Vx, Vy, Vx0, Vy0);

        diffuse(0, s, density, diffusion);
        advect(0, density, s, Vx, Vy);
    }

    void diffuse(int b, double[][] x, double[][] x0, double diff){

        double a = dt * diff * (size - 2) * (size - 2);
        linSolve(b, x, x0, a, 1 + 6 * a);
    }

    void advect(int b, double[][] d, double[][] d0, double[][] velocX, double[][] velocY){

        double dt0 = dt * (size - 2);

        for(int i = 1; i < size - 1; i++){
            for(int j = 1; j < size - 1; j++){

                double x = i - dt0 * velocX[i][j];
                double y = j - dt0 * velocY[i][j];

                if(x < 0.5) x = 0.5;
                if(x > size - 1.5) x = size - 1.5;

                int i0 = (int)x;
                int i1 = i0 + 1;

                if(y < 0.5) y = 0.5;
                if(y > size - 1.5) y = size - 1.5;

                int j0 = (int)y;
                int j1 = j0 + 1;

                double s1 = x - i0;
                double s0 = 1 - s1;

                double t1 = y - j0;
                double t0 = 1 - t1;

                d[i][j] =
                        s0 * (t0 * d0[i0][j0] + t1 * d0[i0][j1]) +
                                s1 * (t0 * d0[i1][j0] + t1 * d0[i1][j1]);
            }
        }

        setBoundary(b, d);
    }

    void project(double[][] velocX, double[][] velocY, double[][] p, double[][] div){

        for(int i = 1; i < size - 1; i++){
            for(int j = 1; j < size - 1; j++){

                div[i][j] = -0.5 * (
                        velocX[i+1][j] - velocX[i-1][j] +
                                velocY[i][j+1] - velocY[i][j-1]
                ) / size;

                p[i][j] = 0;
            }
        }

        setBoundary(0, div);
        setBoundary(0, p);

        linSolve(0, p, div, 1, 6);

        for(int i = 1; i < size - 1; i++){
            for(int j = 1; j < size - 1; j++){

                velocX[i][j] -= 0.5 * (p[i+1][j] - p[i-1][j]) * size;
                velocY[i][j] -= 0.5 * (p[i][j+1] - p[i][j-1]) * size;
            }
        }

        setBoundary(1, velocX);
        setBoundary(2, velocY);
    }

    void linSolve(int b, double[][] x, double[][] x0, double a, double c){

        double cRecip = 1.0 / c;

        for(int k = 0; k < iter; k++){

            for(int i = 1; i < size - 1; i++){
                for(int j = 1; j < size - 1; j++){

                    x[i][j] =
                            (x0[i][j] +
                                    a * (
                                            x[i+1][j] +
                                                    x[i-1][j] +
                                                    x[i][j+1] +
                                                    x[i][j-1]
                                    )
                            ) * cRecip;
                }
            }

            setBoundary(b, x);
        }
    }

    void setBoundary(int b, double[][] x){

        for(int i = 1; i < size - 1; i++){

            x[i][0] = b == 2 ? -x[i][1] : x[i][1];
            x[i][size - 1] = b == 2 ? -x[i][size - 2] : x[i][size - 2];
        }

        for(int j = 1; j < size - 1; j++){

            x[0][j] = b == 1 ? -x[1][j] : x[1][j];
            x[size - 1][j] = b == 1 ? -x[size - 2][j] : x[size - 2][j];
        }

        x[0][0] = 0.5 * (x[1][0] + x[0][1]);
        x[0][size - 1] = 0.5 * (x[1][size - 1] + x[0][size - 2]);
        x[size - 1][0] = 0.5 * (x[size - 2][0] + x[size - 1][1]);
        x[size - 1][size - 1] = 0.5 * (x[size - 2][size - 1] + x[size - 1][size - 2]);
    }
}