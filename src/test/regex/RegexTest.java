package test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;


public class RegexTest {

    @Test
    public void reluctantMatchTest() {
        
        String str = "CN=test.pl, Serial number=00 B0 77 52 C0 17 53 40 4F;"
                + "   CN=test.pl, Serial number=01 B2 87 32 C1 17 53 40 B0;";

        // .*? reluctant
        // http://docs.oracle.com/javase/tutorial/essential/regex/quant.html
        Pattern pattern = Pattern.compile("(Serial number=)(?<serial>.*?)(;)"); // ?<serial> - named group - since JDK 7
        
        Matcher matcher = pattern.matcher(str);
        
        System.out.println("Serial numbers found:");
        
        while(matcher.find()) {
            
            System.out.println(matcher.group("serial")); // named group - since JDK 7
            // or
            //System.out.println(matcher.group(2)); // group index
        }
    }
    
    @Test
    public void replaceTest() {
        
        String str = "--- value=10px; value=15px ---";
        
        System.out.println("Original string: " + str);

        Pattern pattern = Pattern.compile("(value=)(?<value>\\d+)"); // named group - since JDK 7
        
        Matcher matcher = pattern.matcher(str);
        
        StringBuffer s = new StringBuffer();
        
        while(matcher.find()) {

            matcher.appendReplacement(s, 
                    "$1" + // group 1 
                    String.valueOf(Integer.parseInt(matcher.group("value")) * 2)); // group 2
        }
        matcher.appendTail(s);
        
        System.out.println("Replaced string: " + s);
    }
}
