package com.cwoongc.study.fp.grouping;

import com.cwoongc.study.fp.ProgrammingStyle;
import com.cwoongc.study.fp.vo.Major;
import com.cwoongc.study.fp.vo.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GroupingMain {

    @Profile("grouping")
    @Bean
    CommandLineRunner initGrouping(ApplicationContext ctx) {
        return (args -> {

            List<Student> students = Arrays.asList(
                    new Student("wcchoi",1999, new Major("Science","과학")),
                    new Student("swchoi", 2029, new Major("Eng", "영어")),
                    new Student("whchae",1999, new Major("Science","과학"))
            );



            Grouping grouping = new Grouping();
            grouping.printStudentByGradYear(students,ProgrammingStyle.FUNCTIONAL);

            grouping.printStudentByMajor(students,ProgrammingStyle.FUNCTIONAL);

        });
    }
}
