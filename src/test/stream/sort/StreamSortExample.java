package test.stream.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Example sorting using stream.
 * 
 * Shows how to sort by multiple fields which is not so easy to implement before Java 8
 *
 */
public class StreamSortExample {

	public static void main(String[] args) {

		List<ObjectVO> objects = new ArrayList<>();
		objects.add(new ObjectVO("John", 50));
		objects.add(new ObjectVO("John", 60));
		objects.add(new ObjectVO("Andy", 50));
		objects.add(new ObjectVO("Andy", 55));
		objects.add(new ObjectVO("Frank", 70));

		System.out.println("(1) by name");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName)).forEach(System.out::println);
		System.out.println();

		System.out.println("(2) by name reversed");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).reversed()).forEach(System.out::println);
		System.out.println();
		
		System.out.println("(3) by name and then age");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).thenComparing(ObjectVO::getAge))
				.forEach(System.out::println);
		System.out.println();

		System.out.println("(4) by name and then age reversed");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName, Comparator.naturalOrder()).thenComparing(ObjectVO::getAge, Comparator.reverseOrder()))
				.forEach(System.out::println);
		System.out.println();
		
		System.out.println("(5) by (name and then age) and then all reversed");
		objects.stream()
				.sorted(Comparator.comparing(ObjectVO::getName).thenComparing(ObjectVO::getAge).reversed())
				.forEach(System.out::println);
		System.out.println();
	}
	
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
}
