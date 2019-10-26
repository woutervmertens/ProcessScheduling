public class Program {
	public static void main(String[] args) {
		//Choose algorithm here
		ProcessSchedulingAlgorithm algorithm = new NoSchedulingAlgorithm();

		ProcessScheduler scheduler = new ProcessScheduler();
		scheduler.schedule(algorithm);
	}
}
