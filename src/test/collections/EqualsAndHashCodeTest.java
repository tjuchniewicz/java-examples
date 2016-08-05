package test.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntUnaryOperator;

import javax.management.ImmutableDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.time.StopWatch;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EqualsAndHashCodeTest {

	private static final int COLLECTION_SIZE = 5_000_000;

	@FunctionalInterface
	interface ElementCreator {
		Object create();
	}

	@Test
	public void testPerformace() throws Exception {

		int TO_FIND_VALUE = 1_000_000;

		testEntityClass(new EntityWithEqualsWithHashCode(TO_FIND_VALUE), 
				() -> new EntityWithEqualsWithHashCode(RandomUtils.nextInt()));

		testEntityClass(new EntityWithEqualsNoHashCode(TO_FIND_VALUE),
				() -> new EntityWithEqualsNoHashCode(RandomUtils.nextInt()));

		testEntityClass(new EntityNoEqualsNoHashCode(TO_FIND_VALUE),
				() -> new EntityNoEqualsNoHashCode(RandomUtils.nextInt()));
	}

	public void testEntityClass(Object toFind, ElementCreator creator) throws Exception {

		System.out.println(toFind.getClass().getSimpleName());

		Set<Object> collection = new HashSet<>(COLLECTION_SIZE, 0.75f);

		StopWatch sw = new StopWatch();
		sw.start();

		for (int i = 0; i < COLLECTION_SIZE; i++) {
			Object instance = creator.create();
			collection.add(instance);
		}
		
		System.out.println(collection.size());

		sw.split();
		System.out.println(sw.toSplitString());

		sw.reset();
		sw.start();

		// object values are randomized so even for properly implemented equals() we may not find
		// object but it doesn't matter in this performance test.
		System.out.println(collection.contains(toFind));

		sw.split();
		System.out.println(sw.toSplitString());

		System.out.println(StringUtils.repeat("-", 80));

	}

	@Test
	public void consequencesNotUsingHashCode1() {

		// equals - nie
		// hashCode - nie
		Set<EntityNoEqualsNoHashCode> collection = new HashSet<>();

		collection.add(new EntityNoEqualsNoHashCode(10));
		collection.add(new EntityNoEqualsNoHashCode(10));

		Assert.assertEquals(collection.size(), 2); // bad
		Assert.assertFalse(collection.contains(new EntityNoEqualsNoHashCode(10))); // bad
	}

	@Test
	public void consequencesNotUsingHashCode2() {

		// equals - tak
		// hashCode - nie
		Set<EntityWithEqualsNoHashCode> collection = new HashSet<>();

		collection.add(new EntityWithEqualsNoHashCode(10));
		collection.add(new EntityWithEqualsNoHashCode(10));

		Assert.assertEquals(collection.size(), 2); // bad
		Assert.assertFalse(collection.contains(new EntityWithEqualsNoHashCode(10))); // bad
	}

	@Test
	public void properlyUsedHashCode() {

		// equals - tak
		// hashCode - tak
		Set<EntityWithEqualsWithHashCode> collection = new HashSet<>();

		collection.add(new EntityWithEqualsWithHashCode(10));
		collection.add(new EntityWithEqualsWithHashCode(10));

		Assert.assertEquals(collection.size(), 1);
		Assert.assertTrue(collection.contains(new EntityWithEqualsWithHashCode(10)));
	}

	static class EntityNoEqualsNoHashCode {

		private int value;

		public EntityNoEqualsNoHashCode() {
			super();
		}

		public EntityNoEqualsNoHashCode(int value) {
			super();
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

	static class EntityWithEqualsWithHashCode {

		private int value;

		public EntityWithEqualsWithHashCode() {
		}

		public EntityWithEqualsWithHashCode(int value) {
			super();
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + value;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof EntityWithEqualsWithHashCode))
				return false;
			EntityWithEqualsWithHashCode other = (EntityWithEqualsWithHashCode) obj;
			if (value != other.value)
				return false;
			return true;
		}
	}

	static class EntityWithEqualsNoHashCode {

		private int value;

		public EntityWithEqualsNoHashCode() {
		}

		public EntityWithEqualsNoHashCode(int value) {
			super();
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof EntityNoEqualsNoHashCode))
				return false;
			EntityNoEqualsNoHashCode other = (EntityNoEqualsNoHashCode) obj;
			if (value != other.value)
				return false;
			return true;
		}
	}
}
