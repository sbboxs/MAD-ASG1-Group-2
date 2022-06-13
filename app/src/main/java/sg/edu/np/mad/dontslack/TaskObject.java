package sg.edu.np.mad.dontslack;

import java.io.Serializable;

public class TaskObject implements Serializable {
    private String taskName;
    private String taskCategory;
    private String taskDetails;
    private String taskStartTime;
    private String taskDeadLine;
    private boolean taskStatus;

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

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskDoDate) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskDeadLine() {
        return taskDeadLine;
    }

    public void setTaskDeadLine(String taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
    }

    public String getTaskCategory(){return taskCategory;}

    public void setTaskCategory(String taskCategory){ this.taskCategory = taskCategory;}
}
