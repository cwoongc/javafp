package com.cwoongc.study.fp;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LogProcessor {


    public List<String> getErrorLines(String filename, int limit, ProgrammingStyle ps) {

        List<String> errorLines = null;

        switch (ps) {
            case IMPERATIVE:errorLines = getErrorLines(filename, limit);break;
            case FUNCTIONAL:errorLines = getErrorLinesStream(filename,limit);break;
            default:break;
        }

        return errorLines;
    }


    private List<String> getErrorLines(String filename, int limit) {

        List<String> errorLines = new ArrayList<>();
        int errorCnt = 0;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {


            String line = br.readLine();
            while (errorCnt < limit && line != null) {
                if (line.startsWith("ERROR")) {
                    errorLines.add(line);
                    errorCnt++;
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return errorLines;
    }

    private List<String> getErrorLinesStream(String filename, int limit) {
        List<String> errorLines = new ArrayList<>();

        try {
            errorLines = Files.lines(Paths.get(filename))
                    .filter(l->l.startsWith("ERROR"))
                    .limit(40)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return errorLines;
    }


}
