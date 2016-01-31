package test.lambda;

import org.testng.annotations.Test;

public class FunctionalInterfaceTest {

	@FunctionalInterface
	// only one method allowed in interface
	interface MySimpleFunctionalInterface {

		String produce();
	}

	@FunctionalInterface
	interface MyFunctionalParametrizedInterface {

		String produce(Integer i);
	}
	
	@FunctionalInterface
	public interface CombinedInterface {
		
		String produce(Integer i);
		
		default CombinedInterface combine(CombinedInterface i) {
			
			return ((p) -> {
				String s1 = produce(p);
				String s2 = s1.concat("; ").concat(i.produce(p));
				return s2;
			});
			
		}
	}

	String myMethod(MySimpleFunctionalInterface f) {

		return f.produce();
	}

	String myParametrizedMethod(MyFunctionalParametrizedInterface f) {

		return f.produce(10);
	}
	
	String myCombinedMethod(CombinedInterface f) {
		
		return f.produce(25);
	}
	
	@Test
	public void passSimpleExpressionTest() {

		System.out.println(myMethod(() -> "simple test"));
	}

	@Test
	public void passParametrizedExpressionTest() {

		System.out.println(myParametrizedMethod(x -> "param test: " + x));
	}
	
	@Test
	public void combinedInterfaceTest() {
		
		CombinedInterface f1 = (x) -> "string 1: " + x;
		CombinedInterface f2 = (x) -> "string 2: " + x;
		CombinedInterface f3 = f1.combine(f2);
		
		System.out.println(myCombinedMethod(f3));
		
	}
}
