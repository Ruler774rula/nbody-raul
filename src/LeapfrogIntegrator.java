public class LeapfrogIntegrator extends Integrator {
    public LeapfrogIntegrator(double timeStep) {
        super(timeStep);
    }

    @Override
    public void move(Universe universe) {
        int n = universe.getNumBodies();
        //Calcul de les noves posicions utilitzant la velocitat mitja
        for (int i = 0; i < n; i++) {
            Vector velocityHalf = universe.getBodyVelocity(i).plus(universe.getBodyAcceleration(i).scale(timeStep / 2.0));
            Vector newPosition = universe.getBodyPosition(i).plus(velocityHalf.scale(timeStep));
            universe.setBodyPosition(i, newPosition);
        }
        //Recàlcul d'acceleracions i actualització de velocitats
        for (int i = 0; i < n; i++) {
            //calcul de les noves acceleracions i velocitats amb les noves posicions dels cosos
            Vector force = universe.computeForceOn(i);
            Vector newAcceleration = force.scale(1.0 / universe.getBodyMass(i));
            Vector newVelocity = universe.getBodyVelocity(i).plus(newAcceleration.scale(timeStep / 2.0)) 
                    .plus(universe.getBodyAcceleration(i).scale(timeStep / 2.0));
            universe.setBodyVelocity(i, newVelocity);
            universe.setBodyAcceleration(i, newAcceleration);
        }
    }
}