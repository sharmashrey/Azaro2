package shreyas.io.weld.azaro.Database;

/**
 * Created by shreyas on 14/11/16.
 */

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;

        import shreyas.io.weld.azaro.Model.Assignment;
        import shreyas.io.weld.azaro.Model.Course;
        import shreyas.io.weld.azaro.Model.Project;
        import shreyas.io.weld.azaro.Model.Task;
        import shreyas.io.weld.azaro.Model.Term;


        import static shreyas.io.weld.azaro.Database.DBRelatedConstants.TABLE_ASSIGNMENTS;
//WILL have to check
        import static shreyas.io.weld.azaro.Database.DBRelatedConstants.CREATE_TABLE_COURSES;
        import static shreyas.io.weld.azaro.Database.DBRelatedConstants.TABLE_COURSES;
        import static shreyas.io.weld.azaro.Database.DBRelatedConstants.TABLE_PROJECTS;
        import static shreyas.io.weld.azaro.Database.DBRelatedConstants.TABLE_TASKS;

/**
 * This class will help to in database related activities
 */

public class DBHelper  extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DBRelatedConstants.DATABASE_NAME, null, DBRelatedConstants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(DBRelatedConstants.CREATE_TABLE_TERMS);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_COURSES);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_TASKS);
        Log.d("on create", "onCreate: "+CREATE_TABLE_COURSES);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_TASK_ATTACHMENTS);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_ASSIGNMENT_ATTACHMENTS);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_PROJECTS);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_PROJECT_PHASES);
        db.execSQL(DBRelatedConstants.CREATE_TABLE_PROJECT_PHASE_ATTACHMENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_TASK_ATTACHMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_ASSIGNMENT_ATTACHMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_PROJECT_PHASES);
        db.execSQL("DROP TABLE IF EXISTS " + DBRelatedConstants.TABLE_PROJECT_PHASES_ATTACHMENTS);
        // create new tables
        onCreate(db);
    }

    public void updateCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        /*String strSQL = "UPDATE Courses SET "+
                DBRelatedConstants.COURSE_COURSENAME +"="+ course.getCourseName()+", "
                +DBRelatedConstants.COURSE_TERMID +"="+ course.getCourseTermId()+", "
                +DBRelatedConstants.COURSE_COURSELOCATION +"="+ course.getCourseLocation()+", "
                +DBRelatedConstants.COURSE_COURSELOCATION +"="+ course.getCourseLocation()+", "
                +DBRelatedConstants.COURSE_WEEKDAY+"="+course.getWeekDay()+", "
                + DBRelatedConstants.COURSE_COURSESTARTTIME +"="+ course.getCourseStartTime()+", "
                + DBRelatedConstants.COURSE_COURSEENDTIME +"="+ course.getCourseEndTime()

                +" WHERE "+ DBRelatedConstants.COURSE_ID +"=" + course.getCourseId();
        Log.d("updatecourse", "updateCourse: "+strSQL);

        db.execSQL(strSQL);*/

        // SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBRelatedConstants.COURSE_COURSENAME,course.getCourseName());
        values.put(DBRelatedConstants.COURSE_TERMID,course.getCourseTermId());
        values.put(DBRelatedConstants.COURSE_COURSELOCATION,course.getCourseLocation());
        values.put(DBRelatedConstants.COURSE_WEEKDAY,course.getWeekDay());
        // values.put(DBRelatedConstants.COURSE_COURSESTARTTIME,course.getCourseStartTime());
        Log.d("Course Update", "updateCourse: location"+course.getCourseLocation());
        // values.put(DBRelatedConstants.COURSE_COURSEENDTIME,course.getCourseEndTime());
        database.update("Courses", values, DBRelatedConstants.COURSE_ID+"=?", new String[] {Integer.toString(course.getCourseId())});
        database.close();

    }

    public long addNewAssignment(Assignment newAssignment) {
        
        Log.d("In DB add new assign ", " ");
        
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTID, newAssignment.getAssignmentId());
        values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTCOURSEID, newAssignment.getAssignmentCourseId());
        values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTNAME, newAssignment.getAssignmentName());
        values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDESCRIPTION, newAssignment.getAssignmentDescription());
        values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDUEDATE, newAssignment.getAssignmentDueDate());
        values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDUETIME, newAssignment.getAssignmentDueTime());
        
        // insert row
        long newAssignmentRow = db.insert(TABLE_ASSIGNMENTS, null, values);
        Log.d(" New Row iD", "New row inserted  in Term table" + newAssignmentRow);
        Log.d("addNewCourse","assgn id "+ newAssignment.getAssignmentId());
        return newAssignmentRow;
    }

    public long addNewProject(Project newProject) {

        Log.d("In DB add new project ", " ");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTID, newAssignment.getAssignmentId());
        values.put(DBRelatedConstants.PROJECT_PROJECTCOURSEID, newProject.getProjectCourseId());
        values.put(DBRelatedConstants.PROJECT_PROJECTNAME, newProject.getProjectName());
        values.put(DBRelatedConstants.PROJECT_PROJECTDESCRIPTION, newProject.getProjectDescription());
        values.put(DBRelatedConstants.PROJECT_PROJECTDUEDATE, newProject.getProjectDueDate());
        values.put(DBRelatedConstants.PROJECT_PROJECTDUETIME, newProject.getProjectDueTime());

        // insert row
        long newProjectRow = db.insert(TABLE_PROJECTS, null, values);
        Log.d(" New Row iD", "New row inserted  in Term table" + newProjectRow);
        Log.d("addNewProject","project id "+ newProject.getProjectId());
        return newProjectRow;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> outputTermValues = new ArrayList<Assignment>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS;
        Log.e(LOG, selectQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultAssignmentValues = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (resultAssignmentValues.moveToFirst()) {
            do {
                Assignment outputResult = new Assignment();
                outputResult.setAssignmentCourseId(resultAssignmentValues.getInt(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTCOURSEID)));
                outputResult.setAssignmentDescription(resultAssignmentValues.getString(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDESCRIPTION)));
                outputResult.setAssignmentDueDate(resultAssignmentValues.getString(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDUEDATE)));
                outputResult.setAssignmentDueTime(resultAssignmentValues.getString(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTDUETIME)));
                outputResult.setAssignmentId(resultAssignmentValues.getInt(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTID)));
                outputResult.setAssignmentName(resultAssignmentValues.getString(resultAssignmentValues.getColumnIndex(DBRelatedConstants.ASSIGNMENT_ASSIGNMENTNAME)));
                outputTermValues.add(outputResult);
            } while (resultAssignmentValues.moveToNext());
        }
        
        return outputTermValues;
    }
    
    
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    /*  Term table related activity */
    public long addNewTerm(Term newTerm) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBRelatedConstants.TERM_TERMNAME, newTerm.getTermName());
        values.put(DBRelatedConstants.TERM_TERMSTARTDATE, newTerm.getTermStartDate());
        values.put(DBRelatedConstants.TERM_TERMENDDATE, newTerm.getTermEndDate());
        // insert row
        long newTermRow = db.insert(DBRelatedConstants.TABLE_TERMS, null, values);
        Log.d(" New Row iD", "New row inserted  in Term table" + newTermRow);

        return newTermRow;

    }

    public Term getTerm(int term_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DBRelatedConstants.TABLE_TERMS + " WHERE "
                + DBRelatedConstants.TERM_TERMID + " = " + term_id;

        Log.e(LOG, selectQuery);

        Cursor singleTerm = db.rawQuery(selectQuery, null);

        if (singleTerm != null)
            singleTerm.moveToFirst();

        Term outputResult = new Term();
        outputResult.setTermId(singleTerm.getInt(singleTerm.getColumnIndex(DBRelatedConstants.TERM_TERMID)));
        outputResult.setTermName(singleTerm.getString(singleTerm.getColumnIndex(DBRelatedConstants.TERM_TERMNAME)));
        outputResult.setTermStartDate(singleTerm.getString(singleTerm.getColumnIndex(DBRelatedConstants.TERM_TERMSTARTDATE)));
        outputResult.setTermEndDate(singleTerm.getString(singleTerm.getColumnIndex(DBRelatedConstants.TERM_TERMENDDATE)));

        return  outputResult;


    }

    public List<Term> getAllTerms() {
        List<Term> outputTermValues = new ArrayList<Term>();
        String selectQuery = "SELECT  * FROM " + DBRelatedConstants.TABLE_TERMS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultTermValues = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (resultTermValues.moveToFirst()) {
            do {
                Term outputResult = new Term();
                outputResult.setTermId(resultTermValues.getInt(resultTermValues.getColumnIndex(DBRelatedConstants.TERM_TERMID)));
                outputResult.setTermName(resultTermValues.getString(resultTermValues.getColumnIndex(DBRelatedConstants.TERM_TERMNAME)));
                outputResult.setTermStartDate(resultTermValues.getString(resultTermValues.getColumnIndex(DBRelatedConstants.TERM_TERMSTARTDATE)));
                outputResult.setTermEndDate(resultTermValues.getString(resultTermValues.getColumnIndex(DBRelatedConstants.TERM_TERMENDDATE)));
                outputTermValues.add(outputResult);
            } while (resultTermValues.moveToNext());
        }

        return outputTermValues;
    }


    /*  Course table related activity */
    public long addNewCourse(Course newCourse) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBRelatedConstants.COURSE_TERMID, newCourse.getCourseTermId());
        values.put(DBRelatedConstants.COURSE_COURSENAME, newCourse.getCourseName());
        values.put(DBRelatedConstants.COURSE_COURSELOCATION, newCourse.getCourseLocation());
        values.put(DBRelatedConstants.COURSE_WEEKDAY,newCourse.getWeekDay());
        values.put(DBRelatedConstants.COURSE_COURSESTARTTIME, newCourse.getCourseStartTime());
        values.put(DBRelatedConstants.COURSE_COURSEENDTIME, newCourse.getCourseEndTime());

        // insert row
        long newCourseRow = db.insert(TABLE_COURSES, null, values);
        Log.d(" New Row iD", "New row inserted  in Term table" + newCourseRow);

        return newCourseRow;

    }

    public int deleteCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        int courseId = course.getCourseId();
        String whereclause = DBRelatedConstants.COURSE_ID+" = "+String.valueOf(courseId);

        return db.delete(DBRelatedConstants.TABLE_COURSES, whereclause, null);
        //Get courseId from above course object

        //use sqlite db.delete method to delete row having that course id, use this in where
    }

    public int deleteProject(Project project){
        SQLiteDatabase db = this.getWritableDatabase();
        int projectId = project.getProjectId();
        String whereclause = DBRelatedConstants.PROJECT_PROJECTID+" = "+String.valueOf(projectId);
        return db.delete(DBRelatedConstants.TABLE_PROJECTS, whereclause, null);
        //Get courseId from above course object
        //use sqlite db.delete method to delete row having that course id, use this in where
    }
      public int deleteAssignment(Assignment assignment){
        SQLiteDatabase db = this.getWritableDatabase();
        int assignmentId = assignment.getAssignmentId();
        String whereclause = DBRelatedConstants.ASSIGNMENT_ASSIGNMENTID+" = "+String.valueOf(assignmentId);
        return db.delete(DBRelatedConstants.TABLE_ASSIGNMENTS, whereclause, null);
        //Get courseId from above course object
        //use sqlite db.delete method to delete row having that course id, use this in where
    }
    public int deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        int taskId = task.getTaskId();
        String whereclause = DBRelatedConstants.TASK_TASKID+" = "+String.valueOf(taskId);
        return db.delete(DBRelatedConstants.TABLE_TASKS, whereclause, null);
        //Get courseId from above course object
        //use sqlite db.delete method to delete row having that course id, use this in where
    }


    public List<Course> getAllCourses(){
        List<Course> outputTermValues = new ArrayList<Course>();

        String selectQuery = "SELECT  * FROM " + TABLE_COURSES;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultCourseValues = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (resultCourseValues.moveToFirst()) {
            do {
                Course outputResult = new Course();
                outputResult.setCourseId(resultCourseValues.getInt(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_ID)));
                outputResult.setCourseTermId(resultCourseValues.getInt(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_TERMID)));
                outputResult.setCourseName(resultCourseValues.getString(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_COURSENAME)));
                outputResult.setCourseLocation(resultCourseValues.getString(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_COURSELOCATION)));
                outputResult.setCourseStartTime(resultCourseValues.getString(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_COURSESTARTTIME)));
                outputResult.setCourseEndTime(resultCourseValues.getString(resultCourseValues.getColumnIndex(DBRelatedConstants.COURSE_COURSEENDTIME)));
                outputTermValues.add(outputResult);
            } while (resultCourseValues.moveToNext());
        }

        return outputTermValues;
    }

    public void addNewTask(Task newTask) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBRelatedConstants.TASK_TASKCOURSEID,newTask.getTaskCourseId());
        values.put(DBRelatedConstants.TASK_TASKNAME, newTask.getTaskName());
        values.put(DBRelatedConstants.TASK_TASKDESCRIPTION, newTask.getTaskDescription());
        values.put(DBRelatedConstants.TASK_TASKDUEDATE, newTask.getTaskDueDate());
        values.put(DBRelatedConstants.TASK_TASKDUETIME, newTask.getTaskDueTime());
        values.put(DBRelatedConstants.TASK_TYPE, newTask.getTaskType());


        // insert row
        long newTaskRow = db.insert(TABLE_TASKS, null, values);
        Log.d(" New Row iD", "New row inserted  in task table : " + newTaskRow);

    }


    public List<Task> getAllTasks() {
        List<Task> outputTermValues = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
        Log.d("get all task", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultTaskValues = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (resultTaskValues.moveToFirst()) {
            do {
                Task outputResult = new Task();
                outputResult.setTaskCourseId(resultTaskValues.getInt(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKID)));
                outputResult.setTaskDescription(resultTaskValues.getString(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKDESCRIPTION)));
                outputResult.setTaskDueDate(resultTaskValues.getString(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKDUETIME)));
                outputResult.setTaskDueTime(resultTaskValues.getString(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKDUEDATE)));
                outputResult.setTaskId(resultTaskValues.getInt(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKID)));
                outputResult.setTaskName(resultTaskValues.getString(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TASKNAME)));
                outputResult.setTaskType(resultTaskValues.getString(resultTaskValues.getColumnIndex(DBRelatedConstants.TASK_TYPE)));
                outputTermValues.add(outputResult);
            } while (resultTaskValues.moveToNext());
        }

        return outputTermValues;
    }

    public List<Project> getAllProjects() {
        List<Project> outputTermValues = new ArrayList<Project>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTS;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultProjectValues = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (resultProjectValues.moveToFirst()) {
            do {
                Project outputResult = new Project();
                outputResult.setProjectCourseId(resultProjectValues.getInt(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTCOURSEID)));
                outputResult.setProjectDescription(resultProjectValues.getString(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTDESCRIPTION)));
                outputResult.setProjectDueDate(resultProjectValues.getString(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTDUEDATE)));
                outputResult.setProjectDueTime(resultProjectValues.getString(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTDUETIME)));
                outputResult.setProjectId(resultProjectValues.getInt(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTID)));
                outputResult.setProjectName(resultProjectValues.getString(resultProjectValues.getColumnIndex(DBRelatedConstants.PROJECT_PROJECTNAME)));
                outputTermValues.add(outputResult);
            } while (resultProjectValues.moveToNext());
        }

        return outputTermValues;
    }
}
