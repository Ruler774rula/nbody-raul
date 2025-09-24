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
        for (Body b : bodies) {
            System.out.println(b);
        }
    }

    public double getRadius() {
        return radius;
    }

    public int getNumBodies() {
        return numBodies;
    }

    // GETTERS

    public Vector getBodyPosition(int i) {
        return bodies[i].getPosition();
    }

    public Vector getBodyVelocity(int i) {
        return bodies[i].getVelocity();
    }

    public double getBodyMass(int i) {
        return bodies[i].getMass();
    }

    public Vector getBodyAcceleration(int i) {
        return bodies[i].getAcceleration();
    }

    // SETTERS

    public void setBodyPosition(int i, Vector pos) {
        bodies[i].setPosition(pos);
    }

    public void setBodyVelocity(int i, Vector vel) {
        bodies[i].setVelocity(vel);
    }

    public void setBodyAcceleration(int i, Vector acc) {
        bodies[i].setAcceleration(acc);
    }

    //Computem les forces. Complexitat O(N^2) però més precisa
    public Vector computeForceOn(int i) {
        Vector f = new Vector(new double[2]); // inicializamos en (0,0)
        for (int j = 0; j < numBodies; j++) {
            if (i != j) {
                f = f.plus(bodies[i].forceFrom(bodies[j]));
            }
        }
        return f;
    }

}