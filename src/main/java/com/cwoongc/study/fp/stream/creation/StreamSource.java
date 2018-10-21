package com.cwoongc.study.fp.stream.creation;

import com.cwoongc.study.fp.vo.Major;
import com.cwoongc.study.fp.vo.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


/**
 * Stream을 만드는 API가 JAVA lib 전체에 걸쳐 어떤 클래스에서 어떻게 제공되는지 그 출처(StreamSource)를 살펴본다.
 *
 */
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



            String randomInts = r.ints(10,1,101)
                    .sorted()
                    .mapToObj(i->i+"")
                    .collect(Collectors.joining(","));

            System.out.println(randomInts);


            /**
             * 5. java.nio.file.Files 을 이용한 Stream 얻기(lines)
             */
            System.out.println("--------------5.java.nio.file.Files.lines(java.nio.file.Path, java.nio.charset.Charset)------------");

            //spring Resource는 resource location string을 이용하여 File, URI, URL등을 반환받을수 있게 해준다.
            Resource resource = ctx.getResource("classpath:application.properties"); //Spring application context는 spring ResourceLoader의 구현체이다.
            System.out.println(resource.exists());
            System.out.println(resource.getFile().getAbsolutePath());


            String propertiesValues = java.nio.file.Files.lines( //java.nio.file.Files는 file operations static methods를 제공하는 final 클래스이다. 물론 FileSystem provider를 이용한 operation을 제공.
                    //Files.lines는 Path와 Charset을 받아 파일을 읽어들여 Stream<String>을 반환한다.
                        java.nio.file.Paths.get(resource.getURI()), //java.nio.file.Paths는 path string이나 URI를 arg로 이용해서 java.nio.file.Path를 반환하는 static 메소드를 제공하는 final class이다.
                        Charset.forName("EUC-KR")
//                      Charset.availableCharsets().getOrDefault("EUC-KR",Charset.forName("UTF-8")) //존재하는 java.nio.charset.Charset 중에서 GET해서 없으면 여분의 default값을 활용하는 법
                    )
                    .map(s->{String substr = s.substring(s.indexOf("=")+1, s.length());
                        System.out.println(substr); return substr;})
                    .collect(Collectors.joining(",")); // oracle agglist 처럼 목록의 값을 딜리미터로 붙여(joining)서 한 행(String)으로 모아(collect)준다.

            System.out.println(propertiesValues);












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






