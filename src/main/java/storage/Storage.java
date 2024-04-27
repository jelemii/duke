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

/**
 * A storage class which handles storing of tasks. It loads file contents or save tasks to the file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file path.
     *
     * @return the list of tasks loaded from the file. If file is empty, return null.
     * @throws DukeException If file does not exist in the file path or there is an error reading the file.
     */
    public ArrayList<Task> loadFileContents() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);
        assert file.exists() : "There must be an existing task file";
        try {
            if (!file.exists()) {
                throw new DukeException("File not found.");
            } else if (file.length() == 0) {
                System.out.println("An empty file exist. tasks.Task will be added to this file.\n");
                return taskList; //empty list
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

    /**
     * Saves the tasks which contains in an array list to the file.
     *
     * @param tasks The list of tasks to save into the file.
     * @throws IOException When a task cannot be saved into the file or there is a problem with the file.
     */
    public void saveTaskToFile(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "A list of tasks to save should exist.";
        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            fw.write(task.toString() + "\n");
        }
        fw.close();
    }
}
