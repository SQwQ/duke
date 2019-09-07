import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Storage {
    private String filePath;
    private String TODO = "[T]";
    private String DEADLINE = "[D]";
    private String EVENT = "[E]";
    private String COMPLETE = "[✓]";

    /**
     * Constructor
     *
     * @param s location of file
     */
    public Storage(String s) {
        this.filePath = s;
    }

    /**
     * Loads tasks to task list
     *
     * @return LinkedList containing tasks
     * @throws DukeException if file does not exist
     */
    public LinkedList<Task> load() throws DukeException {
        File file = new File(filePath);
        LinkedList<Task> result = new LinkedList<>();
        if (!file.exists()) {
            throw new DukeException("File not found!");
        }
        try {
            List<String> temp = Files.lines(file.toPath())
                    .collect(Collectors.toList());
            for (String s : temp) {
                Task curr = null;
                if (s.contains(TODO)) {
                    curr = new Todo(s.substring(7));
                } else if (s.contains(DEADLINE)) {
                    curr = new Deadline(s.substring(7));
                } else if (s.contains(EVENT)) {
                    curr = new Event(s.substring(7));
                }
                if (s.contains(COMPLETE)) {
                    curr.setDone();
                }
                result.add(curr);
            }
        } catch (IOException e) {
            throw new DukeException("File not found!");
        }
        return result;
    }

    /**
     * Updates Save file with any changes
     *
     * @param tasks Class dealing with manipulating global LinkedList storing tasks
     * @throws DukeException if save file fails
     */
    public void updateSaveFile(LinkedList<Task> tasks) throws DukeException {
        try {
            File saveFile = new File(filePath);
            FileWriter fw = new FileWriter(saveFile, false);
            int i = 1;
            //StringBuilder result = new StringBuilder();
            for (Task t : tasks) {
                fw.write(i + ". " + t + "\n");
                i++;
            }

            fw.close();
        } catch (IOException e) {
            throw new DukeException("Save failed");
        }

    }


}
