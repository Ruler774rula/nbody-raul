import java.util.ArrayList;
import java.util.List;

public class NBodySimulator {
    private Universe universe;
    private Integrator integrator;
    private int pauseTime;
    private boolean trace;
    private List<Vector>[] trajectories;

    @SuppressWarnings("unchecked")

    public NBodySimulator(Universe universe, Integrator integrator, int pauseTime, boolean trace) { //Constructor per crear simulació
        this.universe = universe;
        this.integrator = integrator;
        this.pauseTime = pauseTime;
        this.trace = trace;
        int n = universe.getNumBodies();
        trajectories = new List[n];

        //Trajectories de la simulació
        for (int i = 0; i < n; i++) {
            trajectories[i] = new ArrayList<>();
            trajectories[i].add(universe.getBodyPosition(i));
            Vector a = universe.computeForceOn(i).scale(1.0 / universe.getBodyMass(i));
            universe.setBodyAcceleration(i, a);
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
            StdDraw.clear();
            if (trace) {
                StdDraw.setPenColor(StdDraw.GRAY); // Color trace

                for (int i = 0; i < universe.getNumBodies(); i++) {
                    for (Vector pos : trajectories[i]) {
                        StdDraw.point(pos.cartesian(0), pos.cartesian(1));
                    }
                }
            }
            integrator.move(universe);

            for (int i = 0; i < universe.getNumBodies(); i++) {
                Vector a = universe.computeForceOn(i).scale(1.0 / universe.getBodyMass(i));
                universe.setBodyAcceleration(i, a);
                trajectories[i].add(universe.getBodyPosition(i));
            }


            StdDraw.setPenColor(StdDraw.YELLOW); // Color render d'objecte
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