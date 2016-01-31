package test.lambda;

import org.testng.annotations.Test;

public class FunctionalInterfaceTest {

	@FunctionalInterface
	// only one method allowed in interface
	interface MySimpleFunctionalInterface {

		String produce();
	}

	@FunctionalInterface
	// only one method allowed in interface
	interface MyFunctionalParametrizedInterface {

		String produce(Integer i);
	}

	String myMethod(MySimpleFunctionalInterface f) {

		return f.produce();
	}

	String myParametrizedMethod(MyFunctionalParametrizedInterface f) {

		return f.produce(10);
	}

	@Test
	public void passSimpleExpressionTest() {

		System.out.println(myMethod(() -> "simple test"));
	}

	@Test
	public void passParametrizedExpressionTest() {

		System.out.println(myParametrizedMethod(x -> "param test: " + x));
	}
}
