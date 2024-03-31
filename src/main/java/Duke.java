import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try{
            tasks = new TaskList(storage.loadFileContents());
        } catch (DukeException e) {
            ui.fileNotFoundError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.greetUser();
        boolean isNotExit = true;
        Scanner in = new Scanner(System.in);


        while (isNotExit) {
            String input = in.nextLine().trim().toLowerCase();
            if(input.equals("bye")) {
                ui.showGoodbyeMessage();
                isNotExit = false;
            }
            else {
                Parser.parseInput(input);
            }
        }

        try {
            storage.saveTaskToFile(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showErrorMessage("Error loading tasks from file.");
        }
    }
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }
}
