package sg.edu.np.mad.dontslack;

import java.io.Serializable;

public class TaskObject implements Serializable {
    private String taskName;
    private boolean taskStatus;
    private String taskDetails;
    private String taskDoDate;
    private String taskDeadLine;
    public TaskObject(){

    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getTaskDoDate() {
        return taskDoDate;
    }

    public void setTaskDoDate(String taskDoDate) {
        this.taskDoDate = taskDoDate;
    }

    public String getTaskDeadLine() {
        return taskDeadLine;
    }

    public void setTaskDeadLine(String taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
    }
}
