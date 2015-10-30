package test.threads;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample { 
	
	private static final int N = 3;

	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);

		for (int i = 0; i < N; ++i) // create and start threads
			new Thread(new Worker(startSignal, doneSignal)).start();

		doSomethingElse(); // don't let run yet
		startSignal.countDown(); // let all threads proceed
		
		System.out.println("Waiting for doneSignal");
		doneSignal.await(); // wait for all to finish
		doSomethingElse();
	}

	private static void doSomethingElse() {
		System.out.println("doSomethingElse()");

	}
}

class Worker implements Runnable {
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	public void run() {
		try {
			System.out.println("Waiting for startSignal");
			startSignal.await();
			doWork();
			doneSignal.countDown();
		} catch (InterruptedException ex) {
		} 
	}

	void doWork() {
		System.out.println("doWork()");
	}
}

