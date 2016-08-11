package test.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface FallbackFactory<T> {
	T create(Throwable cause);
}

interface GitHub {
	List<String> contributors(String owner,String repo);
}
public class FactoryLambdaExample {

	public static void main(String[] args) {
		
		FallbackFactory<GitHub> fallbackFactory = new FallbackFactory<GitHub>() {

			@Override
			public GitHub create(Throwable cause) {

				return new GitHub() {
					
					@Override
					public List<String> contributors(String owner, String repo) {
						System.out.println(owner + ":" + repo);
						System.out.println("cause: " + cause.getMessage());
						if (cause instanceof IllegalArgumentException) {
							return Arrays.asList("cops");
						} else {
							return Collections.emptyList();
						}
					}
				};
			}
		};
		
		// is equivalent to...
		
		FallbackFactory<GitHub> fallbackFactoryLambda = cause -> (owner, repo) -> {

			System.out.println(owner + ":" + repo);
			System.out.println("cause: " + cause.getMessage());
			if (cause instanceof IllegalArgumentException) {
				return Arrays.asList("cops");
			} else {
				return Collections.emptyList();
			}
		};

		GitHub gitHub = fallbackFactory.create(new Throwable("test"));
		gitHub.contributors("john", "jrepo");
		
		GitHub gitHub2 = fallbackFactoryLambda.create(new Throwable("test"));
		gitHub2.contributors("tom", "bigone");
		
		System.out.flush();
	}
}
