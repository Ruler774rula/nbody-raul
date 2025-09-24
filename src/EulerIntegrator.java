public class EulerIntegrator extends Integrator {
    public EulerIntegrator(double timeStep) {
        super(timeStep);
    }

    @Override
    public void move(Universe universe) {
        int n = universe.getNumBodies();
        Vector[] newPositions = new Vector[n];
        Vector[] newVelocities = new Vector[n];

        //Càlcul de les noves velocitats i posicions per a cada cos
        for (int i = 0; i < n; i++) {
            Vector force = universe.computeForceOn(i);
            double mass = universe.getBodyMass(i);
            Vector a = force.scale(1.0 / mass);

            // v(t+dt) = v(t) + a*dt (calcul nova velocitat)
            newVelocities[i] = universe.getBodyVelocity(i).plus(a.scale(timeStep));

            // x(t+dt) = x(t) + v(t+dt)*dt (càlcul nova posició utilitzant la nova velocitat)
            newPositions[i] = universe.getBodyPosition(i).plus(newVelocities[i].scale(timeStep));
        }
        
        //Actualització de l'Univers amb les noves dades calculades
        for (int i = 0; i < n; i++) {
            universe.setBodyPosition(i, newPositions[i]);
            universe.setBodyVelocity(i, newVelocities[i]);
        }
    }
}
