public class Main {
    public static void main(String[] args) {
        if (args.length < 5) { //validació dels arguments
            System.out.println("Uso: java Main <dt> <pauseTime> <trace> <integrator> <configType> [params...]");
            System.out.println("integrator: euler | leapfrog");
            System.out.println("configType: file <fname> | central <numBodies> <angleVelPos> | planetary <numPlanets> | choreo <nchoreography>");
            return;
        }

        //Paràmetres de configuració de la simulació
        double dt = Double.parseDouble(args[0]);                    //Pas del temps, contra més petit, més precis
        int pauseTime = Integer.parseInt(args[1]);                  //temps de pausa entre fotogrames
        boolean trace = args[2].toLowerCase().equals("trace");      //Activa / desactiva el rastre de les òrbites
        String integratorType = args[3].toLowerCase();              //Tipus d'integrador (euler o leapfrog)
        String configType = args[4].toLowerCase();                  //Tipus de configuració inicial (arxiu, central, etc.)

        Universe universe = null;
        int argIndex = 5;

        // ============================
        // Construir Universe segons configType
        // ============================

        if (configType.equals("file")) {// Càrrega des d'un fitxer.
            if (args.length < 6) {
                System.out.println("Falta fname para file");
                return;
            }
            String fname = args[argIndex++];
            universe = UniverseFactory.makeUniverseFromFile(fname);

        } else if (configType.equals("central")) {
            if (args.length < 7) {
                System.out.println("Faltan numBodies y angleVelPos para central");
                return;
            }
            int numBodies = Integer.parseInt(args[argIndex++]);
            double angleVelPos = Double.parseDouble(args[argIndex++]);
            universe = UniverseFactory.makeCentralConfiguration(numBodies, angleVelPos);

        } else if (configType.equals("planetary")) {
            if (args.length < 6) {
                System.out.println("Falta numPlanets para planetary");
                return;
            }
            int numPlanets = Integer.parseInt(args[argIndex++]);
            universe = UniverseFactory.makePlanetaryConfiguration(numPlanets);

        } else if (configType.equals("choreo")) {
            if (args.length < 6) {
                System.out.println("Falta nchoreography para choreo");
                return;
            }
            int nchoreography = Integer.parseInt(args[argIndex++]);
            universe = UniverseFactory.makeChoreography(nchoreography);

        } else {
            System.out.println("configType no es correcto");
            return;
        }

        if (universe == null) {
            System.out.println("Error creando universe");
            return;
        }

        // ============================
        // Selecció del integrador segons el seleccionat amb el parametre "integratorType"
        // ============================

        Integrator integrator;
        if (integratorType.equals("euler")) {
            integrator = new EulerIntegrator(dt);
        } else if (integratorType.equals("leapfrog")) {
            integrator = new LeapfrogIntegrator(dt);
        } else {
            System.out.println("Método de integración no válido: " + integratorType);
            return;
        }

        // ============================
        // Llençament de la simulació
        // ============================

        NBodySimulator simulator = new NBodySimulator(universe, integrator, pauseTime, trace);
        simulator.simulate();
    }
}
