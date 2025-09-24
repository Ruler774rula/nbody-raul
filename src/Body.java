/******************************************************************************
 *  Compilation:  javac Body.java
 *  Execution:    java Body
 *  Dependencies: Vector.java StdDraw.java

 *  Implementation of a 2D Body with a position, velocity and mass.
 *
 *
 ******************************************************************************/

public class Body {
    private Vector position;           // position
    private Vector velocity;           // velocity
    private Vector acceleration;  // aceleración (necesaria para Leapfrog)
    private final double mass;         // mass
    private final double G;            // gravitational constant

    public Body(Vector r, Vector v, double mass, double G) {
        this.position = r;
        this.velocity = v;
        this.mass = mass;
        this.G = G;
        this.acceleration = new Vector(new double[2]); // empieza en (0,0)
    }

    public void move(Vector f, double dt) {
        System.out.println("Move body");
        Vector a = f.scale(1/mass); // f = m a
        this.velocity = this.velocity.plus(a.scale(dt)); // v = a t
        this.position = this.position.plus(this.velocity.scale(dt)); // e = v t

    }

    // Fuerza que ejerce otro cuerpo b sobre este
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
    public void setPosition(Vector pos) {
        this.position = pos;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector vel) {
        this.velocity = vel;
    }

    public double getMass() {
        return mass;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acc) {
        this.acceleration = acc;
    }
}