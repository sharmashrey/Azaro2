package shreyas.io.weld.azaro.Model;

/**
 * Created by shreyas on 14/11/16.
 */

/**
 * This  model will store  details of repeatable task.
 * Repeatable task is a task which student needs to do every week at particular time.
 * For example : Consider a course Data Visualization ,it has one Paper Reading activity every week
 * which due on every monday.
 */

public class Task {
    private int taskId;
    private int taskCourseId;
    private String taskName;
    private String taskDescription;
    private String taskDueDate;
    private String taskType;

    public String getTaskDueTime() {
        return taskDueTime;
    }

    public void setTaskDueTime(String taskDueTime) {
        this.taskDueTime = taskDueTime;
    }

    private String taskDueTime;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskCourseId() {
        return taskCourseId;
    }

    public void setTaskCourseId(int taskCourseId) {
        this.taskCourseId = taskCourseId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}