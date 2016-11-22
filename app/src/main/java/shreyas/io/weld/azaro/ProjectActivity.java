package shreyas.io.weld.azaro;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import android.app.LoaderManager.LoaderCallbacks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import shreyas.io.weld.azaro.Database.DBHelper;
import shreyas.io.weld.azaro.Model.Course;
import shreyas.io.weld.azaro.Model.Project;

public class ProjectActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,View.OnClickListener{

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{"foo@example.com:hello", "bar@example.com:world"};
    private ProjectActivity.UserLoginTask mAuthTask = null;

    // UI references -create variables for storing message & button objects
    private AutoCompleteTextView mEmailView;
    private EditText mEditProjectName;
    private EditText mEditProjectDescription;
    DBHelper db;
    Button mSaveButton;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Context currentContext;

    AppCompatSpinner courseSpinner;

    int courseSelectedPosition = 0;
    List<Course> courseList;
    List<String> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        //make message text field & button object
        mEditProjectName = (EditText) findViewById(R.id.EditProjectName);
        mEditProjectDescription = (EditText) findViewById(R.id.EditProjectDescription);
        mSaveButton = (Button) findViewById(R.id.save_project_button);
        courseSpinner = (AppCompatSpinner) findViewById(R.id.coursesSpinner);
        //btn dt tm pckr
        btnDatePicker=(Button)findViewById(R.id.btn_project_due_date);
        btnTimePicker=(Button)findViewById(R.id.btn_project_due_time);
        txtDate=(EditText)findViewById(R.id.in_project_due_date);
        txtTime=(EditText)findViewById(R.id.in_project_due_time);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        DBHelper dbHelper = new DBHelper(this);
        currentContext = this;
        courseList = dbHelper.getAllCourses();
        courseNames = new ArrayList<String>();
        for(Course course : courseList){
            courseNames.add(course.getCourseName());
        }
        ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(currentContext, R.layout.spinner_text, R.id.spinner_textview, courseNames);
        //   Retrieve Extras are retrieved on the other side via:
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        // Create an ArrayAdapter using the string array and a default spinner layout
  /*      Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tasks_array, android.R.layout.select_dialog_multichoice);
        adapter.setDropDownViewResource(android.R.);
        spinner.setAdapter(adapter); // Apply the adapter to the spinner
*/
        courseSpinner.setAdapter(spinadapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseSelectedPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                courseSelectedPosition = 0;
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    //When the send button is clicked
    public void project_save(View v)
    {
        // ADD TO DATABASE
        db = new DBHelper(getApplicationContext());

        Project input1= new Project();
        input1.setProjectCourseId(courseList.get(courseSelectedPosition).getCourseId());
        input1.setProjectName  ( mEditProjectName.getText().toString());
        input1.setProjectDescription( mEditProjectDescription.getText().toString());
        String time = mHour+""+mMinute;
        String date = mYear+""+mMonth+""+mDay;
        input1.setProjectDueTime( Integer.parseInt(time) );
        input1.setProjectDueDate( Integer.parseInt(date) );
        Log.d("calling ", " ");
        db.addNewProject(input1);
        finish();
    }

    private void populateAutoComplete() {}
    private boolean mayRequestContacts() {return false;}
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {}


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {}
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) { }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) { }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}



    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(ProjectActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery { }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {/*  */  return true;}

        @Override
        protected void onPostExecute(final Boolean success) {/*      */  }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
