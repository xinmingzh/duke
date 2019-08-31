package duke.storage;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    String filePath;
    File file;
    Ui ui;
    String gap = "  ";

    /**
     * Initialize a Storage object.
     *
     * @param filePath Path of file whether data is stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
        ui = new Ui();
    }

    /**
     * Load data from file specified by filePath.
     *
     * @return ArrayList of tasks.
     * @throws DukeException If no file is found at FilePath.
     */
    public List<Task> load() throws DukeException {
        Task task;
        List<Task> tasks = new ArrayList<>();
        String[] inputs;
        boolean isDone;

        ui.showLoadTaskMsg();
        task = new Task("");
        inputs = new String[5];
        try {
            Scanner sc = new Scanner(file);
            if (!sc.hasNext()) {
                ui.noRecordsFoundMsg();
            }
            while (sc.hasNext()) {
                inputs = sc.nextLine().split(gap);
                isDone = inputs[1].equals("1");
                switch (inputs[0]) {
                case "T":
                    task = new Todo(inputs[2], isDone);
                    break;
                case "D":
                    task = new Deadline(inputs[2], LocalDateTime.parse(inputs[3]), isDone);
                    break;
                case "E":
                    task = new Event(inputs[2], LocalDateTime.parse(inputs[3]), LocalDateTime.parse(inputs[4]), isDone);
                    break;
                default:
                    break;
                }
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new DukeException();
        }
        ui.showDataLoadedMsg(tasks.size());
        return tasks;
    }

    /**
     * Save program data to file at filePath.
     *
     * @param taskList List of tasks to be written to file.
     */
    public void save(TaskList taskList) {
        String type;
        String isDone;
        String desc;
        String time;

        if (!taskList.isEmpty()) {
            ui.showSaveDataMsg();
        } else {
            ui.showNothingToSaveMsg();
        }
        try {
            FileWriter fw = new FileWriter(file);
            for (Task t : taskList.getTasks()) {
                type = t instanceof Todo ? "T" : t instanceof Deadline ? "D" : "E";
                isDone = t.isDone() ? "1" : "0";
                desc = t.getDesc();
                time = t instanceof Todo ? "" :
                        t instanceof Deadline ? String.valueOf(((Deadline) t).getDateBy()) :
                                ((Event) t).getStartDate() + gap + ((Event) t).getEndDate();
                fw.write(type + gap + isDone + gap + desc + (time.equals("") ? "" : gap) + time + "\n");
            }
            fw.close();
            ui.showDataSavedMsg();
        } catch (IOException e) {
            ui.showSaveErrMsg();
        }
    }
}