package test.function;

import java.util.function.Function;

import org.junit.Test;

public class CurryingExample {

    public static int add(int x, int y, int z) {
        return x + y + z;
    }

    public static Function<Integer, Function<Integer, Function<Integer, Integer>>> add() {
        return x -> y -> z -> x + y + z;
    }

    @Test
    public void classicTest() {
        
        String vatType = "low";
        
        int tax;
        if (vatType.equals("low")) {
            tax = add(1, 2, 8);
        }
        else {
            tax = add(1, 2, 23);
        }
        System.out.println(tax);
    }

    @Test
    public void curryingTest() {

        String vatType = "low";
        
        Function<Integer, Integer> f3 = add().apply(1).apply(2);
        
        int tax;
        if (vatType.equals("low")) {
            tax = f3.apply(8);
        }
        else {
            tax = f3.apply(23);
        }
        System.out.println(tax);
    }
}
