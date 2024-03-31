import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadFileContents() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<>();
        File f = new File(filePath);
        try {
            if (f.length() == 0) {
                System.out.println("An empty file exist. Task will be added to this file.\n");
                return null;
            }
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                Task task = Parser.parseTaskFromFile(line);
                taskList.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found. Creating a new file..");
        }
        return taskList;
    }

    public void saveTaskToFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            fw.write(t + "\n");
        }
        fw.close();
    }
}
