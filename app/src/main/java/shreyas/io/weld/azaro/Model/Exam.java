package shreyas.io.weld.azaro.Model;

/**
 * Created by shreyas on 21/11/16.
 */

public class Exam {

    private int examId;
    private int examCourseId;
    private String examName;
    private String examDescription;
    private String examDate;
    private String examTime;



    private String examCourseName;


    public String getExamCourseName() {
        return examCourseName;
    }

    public void setExamCourseName(String examCourseName) {
        this.examCourseName = examCourseName;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamDescription() {
        return examDescription;
    }

    public void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamCourseId() {
        return examCourseId;
    }

    public void setExamCourseId(int examCourseId) {
        this.examCourseId = examCourseId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }


}
