package test.stream.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StreamSortExample {

	static class ObjectVO {
		private String name;
		private int age;

		public ObjectVO(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "ObjectVO [name=" + name + ", age=" + age + "]";
		}
	}

	public static void main(String[] args) {

		List<ObjectVO> objects = new ArrayList<>();
		objects.add(new ObjectVO("John", 50));
		objects.add(new ObjectVO("John", 60));
		objects.add(new ObjectVO("Andy", 50));
		objects.add(new ObjectVO("Andy", 55));
		objects.add(new ObjectVO("Frank", 70));

		System.out.println("by name");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName)).forEach(System.out::println);
		System.out.println();

		System.out.println("by name reversed");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).reversed()).forEach(System.out::println);
		System.out.println();
		
		System.out.println("by name and age");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).thenComparing(ObjectVO::getAge))
				.forEach(System.out::println);
		System.out.println();

		System.out.println("by name and age reversed");
//		TODO why in this case we have:
//			ObjectVO [name=John, age=60]
//			ObjectVO [name=John, age=50]
//			ObjectVO [name=Frank, age=70]
//			ObjectVO [name=Andy, age=55]
//			ObjectVO [name=Andy, age=50]
//		?
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).thenComparing(ObjectVO::getAge).reversed())
				.forEach(System.out::println);
		System.out.println();

		System.out.println("by name reversed and age reversed");
		objects.stream()
				.sorted(Comparator.comparing(ObjectVO::getName).reversed().thenComparing(ObjectVO::getAge).reversed())
				.forEach(System.out::println);
		System.out.println();

		System.out.println("by name reversed and age");
		objects.stream()
				.sorted(Comparator.comparing(ObjectVO::getName).reversed().thenComparing(ObjectVO::getAge))
				.forEach(System.out::println);
		System.out.println();
		
	}
}
