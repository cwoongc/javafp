package com.cwoongc.study.fp.function;


import com.cwoongc.study.fp.vo.Major;
import com.cwoongc.study.fp.vo.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Configuration
public class PredefinedFunctionInterface {

    @Profile("function-predicate")
    @Bean
    CommandLineRunner initPredicate(ApplicationContext ctx) {
        return args -> {

            Predicate<String> nonNull1 = Objects::nonNull;
            Predicate<String> nonNull2 = s->Objects.nonNull(s);

            String nullstring = null;
            String nonnullstring = "abc";
            String emptyString = "";

            System.out.println(nonNull1.test(nullstring)); //false
            System.out.println(nonNull2.test(nullstring)); //false
            System.out.println(nonNull1.test(nonnullstring)); //true
            System.out.println(nonNull2.test(nonnullstring)); //true
            System.out.println(nonNull1.test(emptyString)); //true
            System.out.println(nonNull2.test(emptyString)); //true

            Predicate<String> isNull1 = Objects::isNull;
            Predicate<String> isNull2 = s->Objects.isNull(s);

            System.out.println();

            System.out.println(isNull1.test(nullstring));//true
            System.out.println(isNull2.test(nullstring));//true
            System.out.println(isNull1.test(nonnullstring)); //false
            System.out.println(isNull2.test(nonnullstring));//false
            System.out.println(isNull1.test(emptyString)); //false
            System.out.println(isNull2.test(emptyString));//false

            Predicate<String> isEmpty1= String::isEmpty;
            Predicate<String> isEmpty2= s->s.isEmpty();

            System.out.println();

            System.out.println(isEmpty1.test(nullstring));//false
            System.out.println(isEmpty2.test(nullstring));//false
            System.out.println(isEmpty1.test(nonnullstring)); //false
            System.out.println(isEmpty2.test(nonnullstring));//false
            System.out.println(isEmpty1.test(emptyString));//true
            System.out.println(isEmpty2.test(emptyString));//true


        };
    }

    /**
     * Function<P2,P3> 을 Delegate 처럼 이용해 단일 parameter와 return값을 갖는
     * 함수 체이닝을 구성할 수 있다.
     * 현재 할당된 함수의 앞에 pre-function (before)을 앞에 끼워넣을땐 compose(Function<P1,P2> before)로,
     * post-function을 뒤에 끼워넣을땐 andThen(Function<P3,P4> post)를 사용한다.
     *
     * @param context
     * @return
     */
    @Profile("function-function")
    @Bean
    CommandLineRunner initFunction(ApplicationContext context) {
        return args -> {
            Function<String, Float> toFloat = Float::valueOf; //String을 받아 Float

            //andThen : a ppst function chaining
            Function<String, String> backToString = toFloat.andThen(String::valueOf); //String->Float + ->String

            //apply : all chained functions execute sequentially
            String result = backToString.apply("123");

            System.out.println(result);

            //compose : a pre function chaining
            Function<Double, String> fromDoubleToToString = backToString.compose(String::valueOf);

            System.out.println(fromDoubleToToString.apply(12D));


        };
    }

    /**
     * Function<P,R>의 compose, andThen은 각각 prefunction, postfunction을 인자로 받아 체이닝을 하고
     * 체이닝된 함수를 반환한다. 주의할 것은 반환된 함수 R만 chaining이 되있고, 호출객체함수나 P로 쓰인 함수는 체이닝되지 않는다는점이다.
     * 항상 반환된 함수만이 체이닝 된 함수집합이란걸 명심하자.
     * @param context
     * @return
     */
    @Profile("function-function-chain")
    @Bean
    CommandLineRunner initFunctionChain(ApplicationContext context) {
        return args -> {

            Function<String, String> firstFunction = s-> {
                s += "_first_";
                System.out.println(s);
                return s;
            };

            Function<String, String> secondFunction = s-> {
                s += "_second_";
                System.out.println(s);
                return s;
            };

            Function<String, String> thirdFunction = s-> {
                s += "_third_";
                System.out.println(s);
                return s;
            };

            Function<String, String> secondNthird =  secondFunction.andThen(thirdFunction);
            Function<String, String> firstNsecond = secondFunction.compose(firstFunction);
            Function<String, String> firstNsecondNthird = secondNthird.compose(firstFunction);

            firstFunction.apply("start1");

            secondFunction.apply("start2");

            thirdFunction.apply("start3");

            System.out.println();

            firstNsecond.apply("startFNS");

            secondNthird.apply("startSNT");

            System.out.println();

            firstNsecondNthird.apply("startFNSNT");


        };
    }

    @Profile("function-supplier")
    @Bean
    CommandLineRunner initSupplier(ApplicationContext context) {
        return args -> {
            /**
             * Suppliers
             * Suppliers produce a result of a given generic type. Unlike Functions, Suppliers don't accept arguments.
             */

            Supplier<Student> studentSupplier = Student::new;
            Student student1 = studentSupplier.get();

            Supplier<Student> student2 = ()->{
                    return new Student(
                            "hymoon",2000,new Major("math", "mathmatics")
                    );
            };

            System.out.println(student2.get().getName());

        };
    }

    @Profile("function-consumer")
    @Bean
    CommandLineRunner initConsumer(ApplicationContext context) {
        return args -> {
            /**
             * Consumers
             * Consumers represents operations to be performed on a single input argument.
              */

            Consumer<Student> greeter = s-> System.out.println("hello "+s.getName());
            greeter.accept(new Student(
                    "hymoon",2000,new Major("math", "mathmatics")
            ));
        };
    }

    @Profile("function-comparator")
    @Bean
    CommandLineRunner initComparator(ApplicationContext context) {
        return args -> {
            /**
             * Comparators
             * Comparators are well known from older versions of Java. Java 8 adds various default methods to the interface.
             */
            Comparator<Student> comparator = (s1,s2)->s1.getName().compareTo(s2.getName());
            Student s1 = new Student(
                    "hymoon",2000,new Major("math", "mathmatics")
            );
            Student s2 = new Student(
              "wcchoi",2008, new Major("computer","Computer Engineering")
            );

            System.out.println(comparator.compare(s1, s2));
            System.out.println(comparator.reversed().compare(s1,s2));


        };
    }





}
