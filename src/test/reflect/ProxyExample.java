package test.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyExample {

	private static class FooInvocationHandler implements InvocationHandler {

		private Object obj;

		public FooInvocationHandler(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			System.out.println("before");

			Object result = method.invoke(obj, args);

			System.out.println("after");

			return result;
		}
	}

	public interface Foo {

		public void test(String s);
	}

	public static class FooImpl implements Foo {

		@Override
		public void test(String s) {
			System.out.println("Foo: " + s);
		}
	}

	public static void main(String[] args) {

		Foo obj = new FooImpl();

		Foo proxy = (Foo) Proxy.newProxyInstance(
				Foo.class.getClassLoader(), new Class[] { Foo.class },
				new FooInvocationHandler(obj));

		proxy.test("hello");

	}
}
