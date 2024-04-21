package duke;

import commands.Command;
import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        parser = new Parser(tasks);
        try {
            tasks.loadTasks(storage.loadFileContents());
        } catch (DukeException e) {
            ui.fileNotFoundError();
        }
    }

    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }

    public void run() {
        ui.greetUser();
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
