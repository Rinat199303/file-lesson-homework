import gamedata.GameProgress;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        //Создаем список директорий и файлов
        StringBuilder log = new StringBuilder();
        // Метод для создания папок
        createFolder("C:/Plan/RoadToMexico/temp/", log);
        createFolder("C:/Plan/RoadToMexico/src/main", log);
        createFolder("C:/Plan/RoadToMexico/src/test", log);
        createFolder("C:/Plan/RoadToMexico/res/drawables", log);
        createFolder("C:/Plan/RoadToMexico/res/vectors", log);
        createFolder("C:/Plan/RoadToMexico/res/icons", log);
        createFolder("C:/Plan/RoadToMexico/saves", log);
//        Используем метод для создания дирректорий и файлов
        createFiles("C:/Plan/RoadToMexico/temp/text.txt", log);
        createFiles("C:/Plan/RoadToMexico/src/main/Main.java", log);
        createFiles("C:/Plan/RoadToMexico/src/main/Utils.java", log);
        logSaving("C:/Plan/RoadToMexico/temp/text.txt", log);
        GameProgress slot1 = new GameProgress(95, 10, 14, 500.64, "Mexico city");
        GameProgress slot2 = new GameProgress(60, 8, 9, 307.27, "California");
        GameProgress slot3 = new GameProgress(30, 3, 5, 150.25, "Madrid");
        String s1 = "C:/Plan/RoadToMexico/saves/slot1.dat";
        String s2 = "C:/Plan/RoadToMexico/saves/slot2.dat";
        String s3 = "C:/Plan/RoadToMexico/saves/slot3.dat";
        //используем метод для сохранения прогресса игры
        // принимает объекты типа
        // String и GameProgress
        saveGame(s1, slot1);
        saveGame(s2, slot2);
        saveGame(s3, slot3);
        //создаем путь к архиву
        String zipSave = "C:/Plan/RoadToMexico/saves/zipRoad.zip";
        //создаем список документов добавляемых в архив
        List<String> listSlots = new ArrayList<>();
        listSlots.add(s1);
        listSlots.add(s2);
        listSlots.add(s3);
        //Реализуем метод по созданию архивов с классом GameProgress
        //и заводим метод по удалению заархивированных данных
        zipFiles(zipSave, listSlots);
        zipDelete(s1);
        zipDelete(s2);
        zipDelete(s3);
        //Реализуем метод распаковки архива openZip и чтения одного из файлов
        //Создаем переменную типа String для распоковки архива
        openZip(zipSave);
        //Реализуем метод openProgress в качестве переменной берем
        //Переменную типа стринг путь к файлу
        System.out.println(openProgress("C:/Plan/RoadToMexico/saves/slot1.dat"));

    }

    private static void zipDelete(String slot) {
        File slotWay = new File(slot);
        slotWay.delete();
    }

    private static void createFolder(String nameFolder, StringBuilder log) {
        File file = new File(nameFolder);
        file.mkdirs();
        log.append("file - " + file.getName() + " created\n");
    }

    private static GameProgress openProgress(String s1) {
        GameProgress slot = null;
        //Создаем выходной поток для записи в файл
        try (FileInputStream fis = new FileInputStream(s1);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            //десереализуем объект, скастим его в класс
            slot = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return slot;
    }

    private static void openZip(String zipSave) {
        try (ZipInputStream zInS = new ZipInputStream(new FileInputStream(zipSave))) {
            ZipEntry entry;
            String name;
            while ((entry = zInS.getNextEntry()) != null) {
                name = entry.getName();// получаем название файла
                FileOutputStream fOutPut = new FileOutputStream(name);
                for (int c = zInS.read(); c != -1; c = zInS.read()) {
                    fOutPut.write(c);
                }
                fOutPut.flush();
                zInS.closeEntry();
                fOutPut.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String zipSave, List<String> listSlots) {
        /**
         * Создаем метод и записывае архив
         */
        try (ZipOutputStream zOutPut = new ZipOutputStream(new FileOutputStream(zipSave))) {
            for (int i = 0; i < listSlots.size(); i++) {
                FileInputStream fis = new FileInputStream(listSlots.get(i));
                ZipEntry entry = new ZipEntry(listSlots.get(i));
                zOutPut.putNextEntry(entry);
                //считываем файл по байтам
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zOutPut.write(buffer, 0, length);
                }
                fis.close();
                zOutPut.closeEntry();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveGame(String name, GameProgress slot) {
        //Создаем FileOutputStream для сохранения пути файла
        //
        try (FileOutputStream fos = new FileOutputStream(name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(slot);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void createFiles(String files, StringBuilder log) {
        //заводим переменную типа класс StringBuilder для записи созданных деррикотрий
        //проходим циклом по листу созданных деррикторий
        File file = new File(files);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        log.append("file - " + file.getName() + " created\n");
    }

    private static void logSaving(String fileName, StringBuilder log) {
        //ищем нужный файл для сохранения текста
        //Заполняем лог text
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            //перевод символьной строки в массив байт
            byte[] bytes = log.toString().getBytes();
            //запись байтов в файл
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}