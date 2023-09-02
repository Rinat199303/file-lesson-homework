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
        List<File> files = new ArrayList<>();
        files.add(new File("C:/Plan/RoadToMexico/temp/"));
        files.add(new File("C:/Plan/RoadToMexico/src/main"));
        files.add(new File("C:/Plan/RoadToMexico/src/test"));
        files.add(new File("C:/Plan/RoadToMexico/res/drawables"));
        files.add(new File("C:/Plan/RoadToMexico/res/vectors"));
        files.add(new File("C:/Plan/RoadToMexico/res/icons"));
        files.add(new File("C:/Plan/RoadToMexico/saves"));
        files.add(new File("C:/Plan/RoadToMexico/temp/text.txt"));
        files.add(new File("C:/Plan/RoadToMexico/src/main/Main.java"));
        files.add(new File("C:/Plan/RoadToMexico/src/main/Utils.java"));
//        Используем метод для создания дирректорий и файлов
        createFiles(files);
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
        zipFiles(zipSave, listSlots);
        //Реализуем метод распаковки архива openZip и чтения одного из файлов
        //Создаем переменную типа String для распоковки архива
//        String unZipWay = "C:/Plan/RoadToMexico/saves";
        openZip(zipSave);
        //Реализуем метод openProgress в качестве переменной берем
        //Переменную типа стринг путь к файлу
        System.out.println(openProgress("C:/Plan/RoadToMexico/saves/slot1.dat"));

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
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                //закрываем запись текущей операции
                fis.close();
                zOutPut.closeEntry();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < listSlots.size(); i++) {
            File slot = new File(listSlots.get(i));
            slot.delete();
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


    private static void createFiles(List<File> files) {
        //заводим переменную типа класс StringBuilder для записи созданных деррикотрий
        StringBuilder log = new StringBuilder();
        //проходим циклом по листу созданных деррикторий
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getName().contains(".")) {
                try {
                    files.get(i).createNewFile();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else files.get(i).mkdirs();
            log.append("file - " + files.get(i).getName() + " created\n");
        }
        //ищем нужный файл для сохранения текста
        for (int i = 0; i < files.size(); i++) {
            //Заполняем лог text
            if (files.get(i).getName().contains("text.txt")) {
                try (FileOutputStream fos = new FileOutputStream(files.get(i))) {
                    //перевод символьной строки в массив байт
                    byte[] bytes = log.toString().getBytes();
                    //запись байтов в файл
                    fos.write(bytes, 0, bytes.length);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}