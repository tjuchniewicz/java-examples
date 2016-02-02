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
		objects.add(new ObjectVO("Frank", null));

		System.out.println("(1) by name");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName)).forEach(System.out::println);
		System.out.println();

		System.out.println("(2) by name reversed");
		objects.stream().sorted(Comparator.comparing(ObjectVO::getName).reversed()).forEach(System.out::println);
		System.out.println();
		
		System.out.println("(3) by name and then age");
		objects.stream()
				.sorted(
						Comparator.comparing(ObjectVO::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
						.thenComparing(ObjectVO::getAge, Comparator.nullsFirst(Comparator.naturalOrder())))
				.forEach(System.out::println);
		System.out.println();

		System.out.println("(4) by name and then age reversed");
		objects.stream()
				.sorted(Comparator.comparing(ObjectVO::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
						.thenComparing(ObjectVO::getAge, Comparator.nullsFirst(Comparator.reverseOrder())))
				.forEach(System.out::println);
		System.out.println();
		
		System.out.println("(5) by (name and then age) and then all reversed");
		objects.stream()
				.sorted(
						Comparator.comparing(ObjectVO::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
						.thenComparing(ObjectVO::getAge, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
				.forEach(System.out::println);
		System.out.println();
	}
	
	static class ObjectVO {
		private String name;
		private Integer age;

		public ObjectVO(String name, Integer age) {
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

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "ObjectVO [name=" + name + ", age=" + age + "]";
		}
	}
}
