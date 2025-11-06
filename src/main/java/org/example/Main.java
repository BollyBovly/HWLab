package org.example;

/*
    ХОД РЕШЕНИЯ:
    1. сначала надо как-то читать введенные пользователем данных, потом обрабатывать их по их содержанию
    2. создать отдельный метод получения http и вывода его соответсвенно (не забудь про логгер)
    3. создать отдельный класс в котором будет вся логика получения и обработки api через
    HttpClient, HttpResponse, HttpRequest. Через try-catch проверить response
    4. СЕРИАЛИЗАЦИЯ, не забудь записывать все это в файле через FileOutputStream, а
    потом десериалзовывать из файла через FileInputStream
 */

import db.UniversityDatabaseManager;
import db.UniversityEntity;
import http.University;
import http.UniversityHTTPException;
import http.UniversitySerializer;
import http.UniversityService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static final UniversityService universityService = new UniversityService();
    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final UniversitySerializer fileSerialization = new UniversitySerializer();
    private static final UniversityDatabaseManager universityDatabaseManager = new UniversityDatabaseManager();

    public static void main(String[] args) throws  SQLException{
        universityDatabaseManager.init();
        StopCycle:
        while (true) {
            switch (scanner.nextLine().toUpperCase()) {
                case "HTTP" -> getHttp();
                case "FILE" -> showFromFile();
                case "DB" -> showFromDB();
                case "EXIT" -> {
                    break StopCycle;
                }
            }
        }
    }

    private static void showFromDB() {
        try {
            for (var university: universityDatabaseManager.getAllUniversities()){
                System.out.println(university);
            }
        } catch (SQLException e) {
            log.severe("SQL error " + e);
        }
    }

    private static void showFromFile() {
        List<University> university = fileSerialization.deserializationUniversity();
        if (university != null){
            university.forEach(System.out::println);
        } else {
            System.out.println("No university found");
        }
    }

    public static void getHttp() {
        try {
            var university = universityService.getUniversity();
            fileSerialization.SerializeUniversity(university);
            for (University u: university){
                var entity = new UniversityEntity(u.name(), u.country());
                universityDatabaseManager.saveUniversity(entity);
            }
            university.forEach(System.out::println);
            System.out.println(university);
        } catch (UniversityHTTPException | SQLException e) {
            log.severe("HTTP error" + e);
        }
    }
}