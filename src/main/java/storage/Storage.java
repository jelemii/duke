package storage;

import duke.DukeException;
import parser.Parser;
import tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadFileContents() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                throw new DukeException("File not found.");
            } else if (file.length() == 0) {
                System.out.println("An empty file exist. tasks.Task will be added to this file.\n");
                return null;
            }
            System.out.println("An existing file exist. Loading task from file...\n");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Parser.parseTaskFromFile(line);
                taskList.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found.");
        }
        return taskList;
    }

    public void saveTaskToFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            fw.write(task.toString() + "\n");
        }
        fw.close();
    }
}
