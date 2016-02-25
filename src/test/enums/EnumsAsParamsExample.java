package test.enums;

public class EnumsAsParamsExample {

	enum MyEnum {
		ONE, TWO, THREE;
	}

	public <T extends Enum<?>> void method1(T e) {
		System.out.println("method1: " + e.name());
	}

	public void method2(String s) {
		System.out.println("method2(String): " + s);
	}

	public void method2(Enum<?> e) {
		System.out.println("method2(Enum): " + e);
	}

	public static void main(String[] args) {

		EnumsAsParamsExample example = new EnumsAsParamsExample();

		
		example.method1(MyEnum.ONE);
		example.method2(MyEnum.ONE);
	}
}
