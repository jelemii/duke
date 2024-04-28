package duke;

import commands.Command;
import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main class which consist of the main method to run the Duke application.
 */
public class Duke {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Loads tasks from the file path given by the main method
     *
     * @param filePath the path to the file which stores all the saved tasks
     */
    public Duke(String filePath) throws DukeException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        parser = new Parser(tasks);
        try {
            tasks.loadTasks(storage.loadFileContents());
            ui.showUpcomingTasks(tasks); //Reminder to user of upcoming tasks
        } catch (DukeException e) {
            ui.fileNotFoundError();
            storage.createFile(filePath);
            ui.showNewFileCreated(filePath);
        }
    }

    public static void main(String[] args) throws DukeException {
        new Duke("duke.txt").run();
    }

    /**
     * runs the main loop of the application
     */
    public void run() {
        ui.greetUser();
        ui.showMenu();
        boolean isNotExit = true;
        Scanner scanner = new Scanner(System.in);


        while (isNotExit) {
            String input = scanner.nextLine().trim().toLowerCase();
            try {
                Command command = parser.parseInput(input);
                command.executeCommand(tasks, ui, storage);
                isNotExit = !(command.isExit());
            } catch (IOException | DukeException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        scanner.close();

        try {
            storage.saveTaskToFile(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showErrorMessage("Error saving tasks to file.");
        }
    }
}
