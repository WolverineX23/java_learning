package org.example.base.string;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringApplicationTest {

    @Test
    private void newStringTest1() {
        String s1 = "abc";
        String s2 = "abc";

        System.out.println(s1 == s2);
    }

    @Test
    private void newStringTest2() {
        String s1 = "abc";
        String s2 = new String("abc");

        System.out.println(s1 == s2);
    }

    @Test
    private void newStringTest3() {
        String s1 = new String("abc");
        String s2 = "abc";

        System.out.println(s1 == s2);
    }

}
