package test.threads;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletionServiceExample {

	public static void main(String[] args) throws Exception {

		// service that wraps a thread pool with 5 threads
		CompletionService<String> compService = new ExecutorCompletionService<String>(
		    Executors.newFixedThreadPool(5));
		 
		// Futures for all submitted Callables that have not yet been checked
		Set<Future<String>> futures = new HashSet<Future<String>>();
		 
		for (int i=0; i<100; i++) {
			final int x = i;
		    Future<String> f = compService.submit(new Callable<String>() {

				public String call() {
					log.info("in callable: {}", x);
					if (x == 66) {
						throw new NullPointerException();
					}
					return "12345";
				}});
		    
		    // keep track of the futures that get created so we can cancel them if necessary
			futures.add(f);
		}
		 
		Future<String> completedFuture;
		String result;
		 
		while (futures.size() > 0) {
		    // block until a callable completes
		    completedFuture = compService.take();
		    futures.remove(completedFuture);
		 
		    try {
		        result = completedFuture.get();
		        assertTrue(result.equals("12345"));
		        System.out.println("ok");
		    } catch (ExecutionException e) {
		        Throwable cause = e.getCause();
		        log.error("result creation failed", cause);
		 
		        for (Future<String> f: futures) {
		            // pass true if you wish to cancel in-progress Callables as well as
		            // pending Callables
		        	log.info("canceling: {}", f);
		            f.cancel(true);
		            log.info("cancelled: {}", f);
		        }
		 
		        break;
		    }
		
		}
		log.info("not processed: {}", futures.size());
		
		System.exit(0);
		
	}
	
}
