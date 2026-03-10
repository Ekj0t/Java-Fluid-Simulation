# Java Fluid Simulation

A real-time **2D fluid simulation** implemented in **Java** using a grid-based solver for the **Navier–Stokes equations**.
The project visualizes fluid motion with interactive dye injection and mouse-driven velocity forces.

This project was built as an exploration of **physics simulation, numerical methods, and graphical visualization**.

---

## Demo

The simulation allows interactive fluid manipulation:

* Inject colored dye into the fluid
* Apply velocity forces using mouse movement
* Watch vortices and fluid motion evolve in real time

Smoke-like fluid emerges naturally from the simulation rules.

---

## Features

* Real-time **2D fluid dynamics simulation**
* **Colored dye injection**
* **Mouse interaction**

  * Drag mouse to apply velocity forces
  * Inject fluid dye with mouse buttons
* **Diffusion and advection**
* **Basic Navier–Stokes solver**
* **Density fading for realistic smoke dissipation**
* **Smoothed rendering using oval cells and alpha blending**

---

## Controls

| Action              | Effect                      |
| ------------------- | --------------------------- |
| Left Mouse Button   | Inject red dye              |
| Right Mouse Button  | Inject green dye            |
| Middle Mouse Button | Inject blue dye             |
| Mouse Drag          | Apply velocity to the fluid |

Colors mix naturally during the simulation.

---

## How It Works

The simulation uses a grid-based fluid solver based on the **Stable Fluids method** by Jos Stam.

Each frame performs:

1. **Velocity diffusion**
2. **Velocity projection (incompressibility enforcement)**
3. **Velocity advection**
4. **Density diffusion**
5. **Density advection**

The result approximates the behavior of **incompressible fluid flow**.

---

## Project Structure

```
src/
 ├── Main.java       # Application entry point and interaction handling
 ├── Fluid.java      # Core fluid simulation logic
 └── Renderer.java   # Rendering and visualization
```

---

## Technologies Used

* Java
* JavaFX
* Maven
* IntelliJ IDEA

---

## Running the Project

Clone the repository:

```
git clone https://github.com/yourusername/java-fluid-simulation.git
```

Navigate into the project:

```
cd java-fluid-simulation
```

Run with Maven:

```
mvn javafx:run
```

---

## Possible Improvements

Future ideas for expanding the simulation:

* Vorticity confinement for stronger swirling motion
* Higher-resolution grids
* Velocity field visualization
* Interactive obstacles
* GPU acceleration (OpenGL / LWJGL)
* Improved interpolation-based rendering

---

## Inspiration

This project was inspired by classic **fluid simulation research and visualization demos**, particularly:

* Jos Stam — *Stable Fluids*
