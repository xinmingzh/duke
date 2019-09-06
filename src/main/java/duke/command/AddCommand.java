package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddCommand extends Command {
    Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.save(tasks);
        return ui.showAddTaskMsg(task);
    }
}
