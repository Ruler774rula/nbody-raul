public class Universe {
    private int numBodies;
    private double radius;
    private Body[] bodies;

    public Universe(Body[] bodies, double radius) {
        this.bodies = bodies;
        this.numBodies = bodies.length;
        this.radius = radius;
        System.out.println("Number of bodies: " + numBodies);
        System.out.println("Radius: " + radius);
    }

    public double getRadius() {
        return radius;
    }

    public int getNumBodies() {
        return numBodies;
    }

    public Vector getBodyPosition(int i) {
        return bodies[i].getPosition();
    }

    public void update(double dt) {
        // Inicializar fuerzas a cero
        Vector[] f = new Vector[numBodies];
        for (int i = 0; i < numBodies; i++) {
            f[i] = new Vector(new double[2]);  // Vector cero
        }
        // Calcular fuerzas
        for (int i = 0; i < numBodies; i++) {
            for (int j = 0; j < numBodies; j++) {
                if (i != j) {
                    f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }
        // Mover cuerpos
        for (int i = 0; i < numBodies; i++) {
            bodies[i].move(f[i], dt);
        }
    }
}