package test.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFeatureExample {

	public static void main(String[] args) throws Exception {

		CompletableFeatureExample example = new CompletableFeatureExample();
		example.run();
	}

	public void run() throws InterruptedException, ExecutionException {

		String login = "dani";
		String password = "pass";
		String land = "poland";

		CompletableFuture<Boolean> loginCompletable = checkLogin(login, password);
		CompletableFuture<Boolean> checkLandCompletable = checkLand(land);
		CompletableFuture<String> welcomeOrNot = loginCompletable.thenCombine(checkLandCompletable,
				(cust, shop) -> welcome(cust, shop));

		System.out.println(welcomeOrNot.get());
	}

	private String welcome(Boolean login, Boolean land) {
		if (login && land)
			return "welcome";
		return "not welcome";
	}

	private CompletableFuture<Boolean> checkLand(String land) {
		// only Spanish are allowed
		return CompletableFuture.supplyAsync(() -> {
			// big task with back end dependencies
			sleep();

			return "spain".equals(land);
		});
	}

	private CompletableFuture<Boolean> checkLogin(String login, String password) {
		return CompletableFuture.supplyAsync(() -> {
			// very hard authentication process
			sleep();
			return login != null && password != null;
		});
	}

	private static void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
