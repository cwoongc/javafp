package com.cwoongc.study.fp.grouping;

import com.cwoongc.study.fp.ProgrammingStyle;
import com.cwoongc.study.fp.vo.Major;
import com.cwoongc.study.fp.vo.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grouping {


    public void printStudentByGradYear(List<Student> students, ProgrammingStyle ps) {
        switch (ps) {
            case IMPERATIVE:
                printStudentByGradeYear(students);
                break;
            case FUNCTIONAL:
                printStudentByGradeYearStream(students);
                break;
            default:break;

        }
    }

    private void printStudentByGradeYear(List<Student> students) {
        Map<Integer,List<Student>> studentByGradYear = new HashMap<>();

        for(Student student : students) {
            int year = student.getGradYear();
            List<Student> list = studentByGradYear.get(year);
            if(list == null) {
                list = new ArrayList<>();
                studentByGradYear.put(year,list);
            }
            list.add(student);
        }

        studentByGradYear.forEach((year,stds)-> System.out.println(year+"\n "+
                stds.stream().map((s)->s.getName())
                             .collect(Collectors.joining(","))));

    }

    private void printStudentByGradeYearStream(List<Student> students) {
        Map<Integer,List<Student>> studentByGradYear =
                students.stream()
                    .collect(Collectors.groupingBy(Student::getGradYear));

        studentByGradYear.forEach((k,v)-> System.out.println(k+"\n "+ v.stream()
                                                                        .map((s)->s.getName())
                                                                        .collect(Collectors.joining(","))));

    }

    public void printStudentByMajor(List<Student> students, ProgrammingStyle ps) {
        switch (ps) {
            case IMPERATIVE:printStudentByMajor(students);break;
            case FUNCTIONAL:printStudentByMajorStream(students);break;
            default:break;
        }
    }

    private void printStudentByMajor(List<Student> students) {

    }

    private void printStudentByMajorStream(List<Student> students) {

        Map<Major, List<Student>> studentByMajor = students.stream()
                .collect(Collectors.groupingBy(Student::getMajor));

        studentByMajor.forEach((m,stds)-> System.out.println(m.getName()+"\n "+stds.stream()
                                                                                   .map((s)->s.getName())
                                                                                   .collect(Collectors.joining(","))));
    }










}
