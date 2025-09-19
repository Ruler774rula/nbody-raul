import java.util.ArrayList;
import java.util.List;

public class NBodySimulator {
    private Universe universe;
    private double timeStep;
    private int pauseTime;
    private boolean trace;
    private List<Vector>[] trajectories;  // Lista de trayectorias por cuerpo

    @SuppressWarnings("unchecked")
    public NBodySimulator(Universe universe, double dt, int pt, boolean doTrace) {
        this.universe = universe;
        timeStep = dt;
        pauseTime = pt;
        trace = doTrace;
        // Inicializar trayectorias
        int n = universe.getNumBodies();
        trajectories = new List[n];
        for (int i = 0; i < n; i++) {
            trajectories[i] = new ArrayList<>();
            trajectories[i].add(universe.getBodyPosition(i));  // Posición inicial
        }
    }

    private void createCanvas() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.025);
        double radius = universe.getRadius();
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
    }

    public void simulate() {
        createCanvas();
        while (true) {
            StdDraw.clear();  // Limpiar pantalla en cada iteración
            if (trace) {
                StdDraw.setPenColor(StdDraw.GRAY);
                // Dibujar todas las posiciones pasadas
                for (int i = 0; i < universe.getNumBodies(); i++) {
                    for (Vector pos : trajectories[i]) {
                        StdDraw.point(pos.cartesian(0), pos.cartesian(1));
                    }
                }
            }
            universe.update(timeStep);
            // Actualizar trayectorias con la nueva posición
            for (int i = 0; i < universe.getNumBodies(); i++) {
                trajectories[i].add(universe.getBodyPosition(i));
            }
            StdDraw.setPenColor(StdDraw.BLACK);  // Dibujar posición actual en negro
            drawUniverse();
            StdDraw.show();
            StdDraw.pause(pauseTime);
        }
    }

    private void drawUniverse() {
        int n = universe.getNumBodies();
        for (int i = 0; i < n; i++) {
            Vector pos = universe.getBodyPosition(i);
            StdDraw.point(pos.cartesian(0), pos.cartesian(1));
        }
    }
}