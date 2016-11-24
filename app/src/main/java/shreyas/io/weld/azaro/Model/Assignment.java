package shreyas.io.weld.azaro.Model;

/**
 * Created by shreyas on 14/11/16.
 */

/**
 * This model is used to store attchment details of Assignments.
 */

public class Assignment {

    private int assignmentId;
    private int assignmentCourseId;
    private String assignmentName;
    private String assignmentDescription;
    private String assignmentDueDate;
    private String assignmentDueTime;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getAssignmentCourseId() {
        return assignmentCourseId;
    }

    public void setAssignmentCourseId(int assignmentCourseId) {
        this.assignmentCourseId = assignmentCourseId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public String getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public void setAssignmentDueDate(String assignmentDueDate) {
        this.assignmentDueDate = assignmentDueDate;
    }

    public String getAssignmentDueTime() {
        return assignmentDueTime;
    }

    public void setAssignmentDueTime(String assignmentDueTime) {
        this.assignmentDueTime = assignmentDueTime;
    }

}