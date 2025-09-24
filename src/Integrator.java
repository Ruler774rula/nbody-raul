public abstract class Integrator {
    protected double timeStep;   // Compartit per tots els integradors

    public Integrator(double timeStep) {
        this.timeStep = timeStep;
    }

    // cada integrador implementa la seva propia versió
    public abstract void move(Universe universe);

}
