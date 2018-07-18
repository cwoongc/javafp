package com.cwoongc.study.fp.stream.creation;

import com.cwoongc.study.fp.vo.Major;
import com.cwoongc.study.fp.vo.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Configuration
public class StreamSource {

    private Student wcchoi = new Student("wcchoi", 2008, new Major("computer engineering","computer engineering desc"));
    private Student hymoon = new Student("hymoon", 2004, new Major("computer science", "computer science desc"));


    @Bean
    @Profile("stream-source")
    public CommandLineRunner studyStreamSource(ApplicationContext ctx) {
        return args -> {

            /**
             * 1. 정수 범위 스트림 자동생성 (Stream.range)
             *
             * IntStream, LongStream등의 정수 스트림은
             * range(inc,exc), rangeClosed(inc, inc)로
             * 임의 범위의 정수를
             * 스트림으로 바로 생성 가능하다.
             */
             OptionalDouble optionalDouble = IntStream
                    .range(0,10)
                    .map(i -> ++i)
                    .average();

             optionalDouble.ifPresent((d)-> System.out.println(d));


            /**
             * 2. 엘리먼트 직접 추가하여 객체 스트림 생성 (Stream.of)
             */
            Optional<String> gradeYears = Stream.of(wcchoi, hymoon)
                     .map(st->String.valueOf(st.getGradYear()))
                     .sorted()
                     .reduce((a,b)->a+","+b);

            gradeYears.ifPresent(gy-> System.out.println(gy));


            /**
             * 3. 스트림 빌더 이용 객체 스트림 생성
             */
            Stream<Object> studentStream = Stream.builder()
                     .add(wcchoi)
                     .add(hymoon)
                     .build();

             StringJoiner sj = new StringJoiner(",");
             long stCnt = studentStream.peek(s->sj.add(((Student)s).getName()))
                     .count();

            System.out.println(String.format(
                    "[%d](%s)",stCnt,sj.toString()
            ));


            /**
             * 4. Random을 이용하여 정수, 실수 랜덤수 스트림 생성(ints(), longs(), doubles())
             */
            Random r = new Random();
            String randomInts = r.ints(10)
                    .filter(e->e>=0)
                    .sorted()
                    .mapToObj(i->i+"")
                    .collect(Collectors.joining(","));

            System.out.println(randomInts);













        };
    }


    @Bean
    @Profile("stream-operation")
    public CommandLineRunner studyStreamOperations(ApplicationContext ctx) {
        return args -> {



            /**
             * 첫번째 elelment 찾기. findFirst
             */
            OptionalLong optionalLong = LongStream.range(0,10)
                    .map(l->++l)
                    .findFirst();

            optionalLong.ifPresent(l-> System.out.println(l));






        };
    }
}
