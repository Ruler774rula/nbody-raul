import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UniverseFactory {

    public static double randomBetween(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public static Universe makeUniverseFromFile(String fname) {
        int numBodies = 0;
        double radius = 0;
        Body[] bodies = null;
        try {
            Scanner in = new Scanner(new FileReader(fname));
            numBodies = Integer.parseInt(in.next());
            radius = Double.parseDouble(in.next());
            bodies = new Body[numBodies];
            final double G = 6.67e-11;
            for (int i = 0; i < numBodies; i++) {
                double rx = Double.parseDouble(in.next());
                double ry = Double.parseDouble(in.next());
                double vx = Double.parseDouble(in.next());
                double vy = Double.parseDouble(in.next());
                double mass = Double.parseDouble(in.next());
                bodies[i] = new Body(new Vector(new double[]{rx, ry}), new Vector(new double[]{vx, vy}), mass, G);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Universe(bodies, radius);
    }

    public static Universe makeCentralConfiguration(int numBodies, double angleVelPos) {
        final double RADIUS = 1e11;
        final double GAMMA = 5e-5;
        final double MASS = 1e33;
        final double DISTANCE = 0.4 * RADIUS;
        double velocityMagnitude = GAMMA * DISTANCE;
        Body[] bodies = new Body[numBodies];
        final double G = 6.67e-11;
        for (int i = 0; i < numBodies; i++) {
            double anglePos = (2 * Math.PI * i) / numBodies;
            double rx = DISTANCE * Math.cos(anglePos);
            double ry = DISTANCE * Math.sin(anglePos);
            double vx = velocityMagnitude * Math.cos(anglePos + angleVelPos);
            double vy = velocityMagnitude * Math.sin(anglePos + angleVelPos);
            bodies[i] = new Body(new Vector(new double[]{rx, ry}), new Vector(new double[]{vx, vy}), MASS, G);
        }
        return new Universe(bodies, RADIUS);
    }

    public static Universe makePlanetaryConfiguration(int numPlanets) {
        final double MAX_VELOCITY = 1e05;
        final double MIN_VELOCITY = 1e04;
        final double MIN_MASS = 1e20;
        final double MAX_MASS = 1e30;
        final double RADIUS = 1e12;

        int numBodies = numPlanets + 1;
        Body[] bodies = new Body[numBodies];
        final double MASS = 1e39;
        final double G = 6.67e-11;
        bodies[0] = new Body(new Vector(new double[2]), new Vector(new double[2]), MASS, G);
        for (int i = 1; i < numBodies; i++) {
            double angle = randomBetween(-Math.PI, Math.PI);
            double rho = randomBetween(RADIUS / 4, RADIUS / 2);
            double rx = Math.cos(angle) * rho;
            double ry = Math.sin(angle) * rho;
            double vx = -ry / 1000. + randomBetween(MIN_VELOCITY, MAX_VELOCITY);
            double vy = rx / 1000. + randomBetween(MIN_VELOCITY, MAX_VELOCITY);
            double mass = randomBetween(MIN_MASS, MAX_MASS);
            bodies[i] = new Body(new Vector(new double[]{rx, ry}), new Vector(new double[]{vx, vy}), mass, G);
        }
        return new Universe(bodies, RADIUS);
    }

    public static Universe makeChoreography(int nchoreography) {
        String fname = "data/simo-initial-conditions.txt";
        final int NUM_CHOREOGRAPHIES = 345;
        if (!(nchoreography >= 1 && nchoreography <= NUM_CHOREOGRAPHIES)) {
            throw new IllegalArgumentException("Invalid choreography number");
        }
        double c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;
        try {
            Scanner in = new Scanner(new FileReader(fname));
            for (int i = 1; i <= nchoreography; i++) {
                c1 = Double.parseDouble(in.next());
                c2 = Double.parseDouble(in.next());
                c3 = Double.parseDouble(in.next());
                c4 = Double.parseDouble(in.next());
                c5 = Double.parseDouble(in.next());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Vector r1 = new Vector(new double[]{-2 * c1, 0});
        Vector r2 = new Vector(new double[]{c1, c2});
        Vector r3 = new Vector(new double[]{c1, -c2});
        Vector v1 = new Vector(new double[]{0, -2 * c4});
        Vector v2 = new Vector(new double[]{c3, c4});
        Vector v3 = new Vector(new double[]{-c3, c4});

        double radius = 0.5;
        int numBodies = 3;
        Body[] bodies = new Body[numBodies];
        double mass = 1. / 3;
        double G = 1.0;
        bodies[0] = new Body(r1, v1, mass, G);
        bodies[1] = new Body(r2, v2, mass, G);
        bodies[2] = new Body(r3, v3, mass, G);
        return new Universe(bodies, radius);
    }
}