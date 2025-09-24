public abstract class Integrator {
    protected double timeStep;   // 🔑 compartido por todos los integradores

    public Integrator(double timeStep) {
        this.timeStep = timeStep;
    }

    // cada integrador implementa su propia versión
    public abstract void move(Universe universe);

}
