/******************************************************************************
 *  Compilation:  javac Body.java
 *  Execution:    java Body
 *  Dependencies: Vector.java StdDraw.java
 *
 *  Implementation of a 2D Body with a position, velocity and mass.
 *
 *
 ******************************************************************************/

public class Body {
    private Vector position;           // position
    private Vector velocity;           // velocity
    private final double mass;         // mass
    private final double G;            // gravitational constant

    public Body(Vector r, Vector v, double mass, double G) {
        this.position = r;
        this.velocity = v;
        this.mass = mass;
        this.G = G;
    }

    public void move(Vector f, double dt) {
        Vector a = f.scale(1/mass);
        velocity = velocity.plus(a.scale(dt));
        position = position.plus(velocity.scale(dt));
    }

    public Vector forceFrom(Body b) {
        Body a = this;
        Vector delta = b.position.minus(a.position);
        double dist = delta.magnitude();
        if (dist == 0) {
            return new Vector(new double[2]); // Evitar división por cero
        }
        double magnitude = (G * a.mass * b.mass) / (dist * dist);
        return delta.direction().scale(magnitude);
    }

    @Override
    public String toString() {
        return "position "+ position.toString()+", velocity "+ velocity.toString() + ", mass "+mass;
    }

    public Vector getPosition() {
        return position;
    }
}