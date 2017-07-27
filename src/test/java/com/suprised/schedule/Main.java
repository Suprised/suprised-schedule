package com.suprised.schedule;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    // "proxool-0.9.1.jar", "proxool-cglib-1.0.jar",
    private static List<String> excludes = 
        Arrays.asList(
            "jsp-api-2.0.jar", "servlet-api.jar", "ehcache-1.5.0.jar"
            ,"quartz-2.2.1.jar","quartz-jobs-2.2.1.jar", "jtds-1.2.6.jar"
            ,"db2-db2j-1.0.jar", "db2-db2java-1.0.jar", "mysql-connector-java-5.1.18.jar"
            ,"pgjdbc.jar", "oracle-jdbc14-1.0.jar", "c3p0-0.9.1.2.jar", "junit-4.8.1.jar","catalina.jar");

    public static void main(String[] args) throws IOException {
        // includes-lib.txt
        // final String dir = "D:\\eclipse\\eclipse64\\workspace\\dasSuite_915\\lib\\";
        // final String replace = "";
        
        // init.bat init.sh
        final String dir = "D:\\eclipse\\eclipse64\\workspace\\dasSuite_915\\webapp\\WEB-INF\\lib\\";
        // final String replace = "set JAR_CP=%JAR_CP%;../lib/"; // windows
        final String replace = "JAR_CP=$JAR_CP:../lib/"; // linux
        
        Path path = Paths.get(dir);
        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String absolutePath = file.toAbsolutePath().toString();
                if (excludes.contains(file.getFileName().toString())) {
                    
                } else if (!absolutePath.endsWith(".jar") || (absolutePath.contains("isp") && !absolutePath.contains("isp\\casclient-1.0.jar"))) {
                    
                } else {
                    System.out.println(absolutePath.replace(dir, replace).replace("\\", "/"));
                    // System.out.println(absolutePath.replace(dir, "").replace("\\", "/"));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
}
