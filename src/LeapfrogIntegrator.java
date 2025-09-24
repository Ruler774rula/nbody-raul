public class LeapfrogIntegrator extends Integrator {
    public LeapfrogIntegrator(double timeStep) {
        super(timeStep);
    }

    @Override
    public void move(Universe universe) {
        int n = universe.getNumBodies();
        // Paso 1: Calcular nuevas posiciones usando velocidad media
        for (int i = 0; i < n; i++) {
            Vector velocityHalf = universe.getBodyVelocity(i).plus(universe.getBodyAcceleration(i).scale(timeStep / 2.0));
            Vector newPosition = universe.getBodyPosition(i).plus(velocityHalf.scale(timeStep));
            universe.setBodyPosition(i, newPosition);
        }
        // Paso 2: Recalcular aceleraciones y actualizar velocidades
        for (int i = 0; i < n; i++) {
            Vector force = universe.computeForceOn(i);
            Vector newAcceleration = force.scale(1.0 / universe.getBodyMass(i));
            Vector newVelocity = universe.getBodyVelocity(i).plus(newAcceleration.scale(timeStep / 2.0)) // Usa media simétrica
                    .plus(universe.getBodyAcceleration(i).scale(timeStep / 2.0));
            universe.setBodyVelocity(i, newVelocity);
            universe.setBodyAcceleration(i, newAcceleration);
        }
    }
}