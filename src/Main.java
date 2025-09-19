public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Uso: java Main <dt> <pauseTime> <trace> <archivo>");
            return;
        }

        double dt = Double.parseDouble(args[0]);
        int pauseTime = Integer.parseInt(args[1]);
        boolean trace = args[2].toLowerCase().equals("trace");
        String fname = args[3];

        Universe universe = new Universe(fname);
        NBodySimulator simulator = new NBodySimulator(universe, dt, pauseTime, trace);
        simulator.simulate();
    }
}