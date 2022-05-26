package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    protected static final String zipPath = "/Users/mikhailmachulski/IdeaProjects/Games/savegames/zip.zip";

    public static void main(String[] args) throws IOException {
        GameProgress playerOne = new GameProgress(88, 2, 13, 11);
        GameProgress playerTwo = new GameProgress(52, 4, 23, 44);
        GameProgress playerThree = new GameProgress(5, 9, 63, 153);

        GameProgress[] players = new GameProgress[3];
        players[0] = playerOne;
        players[1] = playerTwo;
        players[2] = playerThree;

        String[] savePaths = new String[3];
        savePaths[0] = "/Users/mikhailmachulski/IdeaProjects/Games/savegames/saveOne.java";
        savePaths[1] = "/Users/mikhailmachulski/IdeaProjects/Games/savegames/saveTwo.java";
        savePaths[2] = "/Users/mikhailmachulski/IdeaProjects/Games/savegames/saveThree.java";

        ArrayList<String> allSavePath = new ArrayList<>();

        for (String s : savePaths) {
            allSavePath.add(s);
        }

        for (int i = 0; i < savePaths.length; i++) {
            saveGame(savePaths[i], players[i]);
        }

        zipFiles(zipPath, allSavePath);

        for (String s : savePaths) {
            deleteFile(s);
        }

    }

    public static void saveGame(String filePath, GameProgress progress) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            stream.writeObject(progress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, ArrayList filePath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {

            for (int i = 0; i < filePath.size(); i++) {
                FileInputStream fis = new FileInputStream(filePath.get(i).toString());

                ZipEntry entry = new ZipEntry(filePath.get(i).toString());
                zout.putNextEntry(entry);

                byte[] buffer = new byte[fis.available()];

                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.delete())
                System.out.println(filePath + " is deleted");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
