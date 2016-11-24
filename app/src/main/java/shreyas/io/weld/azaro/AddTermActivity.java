package shreyas.io.weld.azaro;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import shreyas.io.weld.azaro.Database.DBHelper;
import shreyas.io.weld.azaro.Model.Term;

/**
 * A login screen that offers login via email/password.
 */
public class AddTermActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,View.OnClickListener{

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };private UserLoginTask mAuthTask = null;

    // UI references.
    //create variables for storing message & button objects
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private EditText mEditTermName;
    //private EditText mEditCourseLocationView;
    //private EditText mEditCourseWeekDay;
    private EditText txtDate;
    private EditText txtEndDate;

    Button btnStartDatePicker;
    Button btnEndDatePicker;
    //Button btnStartTimePicker;
    //Button btnEndTimePicker;
    Button btnTermSave;
    DBHelper db;

    //In order to send date to DB
    private int mStartYear, mStartMonth, mStartDay;
    private int mEndYear, mEndMonth, mEndDay;
    //In order to send time in DB
    //private int startHour, startMinute;
    //private int endHour, endMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        //make message text field & button object
        mEditTermName = (EditText) findViewById(R.id.EditTermName);
        Log.d("on create term name", "onCreate: edit term name"+mEditTermName);
        //mEditCourseLocationView = (EditText) findViewById(R.id.EditCourseLocation);
        //mEditCourseWeekDay=(EditText) findViewById(R.id.EditCourseWeekDay);
        btnTermSave = (Button) findViewById(R.id.save_Tbutton);

        Log.d("On create save", "onCreate: button save"+btnTermSave);
        //btn dt tm pckr
        btnStartDatePicker=(Button)findViewById(R.id.btn_start_Tdate);

        Log.d("On Create Start", "onCreate: start date picker"+btnStartDatePicker);
        txtDate=(EditText)findViewById(R.id.in_start_Tdate);
        btnStartDatePicker.setOnClickListener(this);
        Log.d("Term Start Date", txtDate.toString());

        btnEndDatePicker=(Button)findViewById(R.id.btn_end_Tdate);
        Log.d("on create end", "onCreate: end date picker"+btnEndDatePicker);
        txtEndDate=(EditText)findViewById(R.id.in_end_Tdate);
        btnEndDatePicker.setOnClickListener(this);
        Log.d("Term End Date", txtEndDate.toString());

       /* btnStartTimePicker=(Button)findViewById(R.id.btn_time);
        txtTime=(EditText)findViewById(R.id.in_time);
        btnStartTimePicker.setOnClickListener(this);

        btnEndTimePicker=(Button)findViewById(R.id.btn_end_time);
        txtEndTime= (EditText)findViewById(R.id.in_end_time);
        btnEndTimePicker.setOnClickListener(this); */


        //   Retrieve info from other end
        //    Extras are retrieved on the other side via:
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
      }


    @Override
    public void onClick(View v) {
        if (v == btnStartDatePicker){

            // Get Current Time
            final Calendar c = Calendar.getInstance();

            mStartYear = c.get(Calendar.YEAR);
            mStartMonth = c.get(Calendar.MONTH);
            mStartDay = c.get(Calendar.DAY_OF_YEAR);

            // Launch Time Picker Dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mStartYear, mStartMonth, mStartDay);
            datePickerDialog.show();

        }else if(v == btnEndDatePicker){
            // Get Current Time
            final Calendar c = Calendar.getInstance();

            mEndYear = c.get(Calendar.YEAR);
            mEndMonth = c.get(Calendar.MONTH);
            mEndDay = c.get(Calendar.DAY_OF_YEAR);

            // Launch Time Picker Dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mEndYear, mEndMonth, mEndDay);
            datePickerDialog.show();
        }
    }

    //When the send button is clicked
    public void saveTerm(View v)
    {
        // ADD TO DATABASE
        db = new DBHelper(getApplicationContext());
        Log.d("In Save button", " " + mEditTermName.getText().toString());
        Term newTerm= new Term();
        newTerm.setTermName( mEditTermName.getText().toString());
        String strtdate = String.valueOf(mStartYear)+"/"+String.valueOf(mStartMonth)+"/"+String.valueOf(mStartDay);
        String enddate = String.valueOf(mEndYear)+"/"+String.valueOf(mEndMonth)+"/"+String.valueOf(mEndDay);

        newTerm.setTermStartDate(strtdate);
        newTerm.setTermEndDate(enddate);

        // obtain given time in ms




        db.addNewTerm(newTerm);
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
    private void showProgress(final boolean show) {/*
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
   */ }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
  /*      return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
        */  }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {/*
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
   */ }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(AddTermActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {/*
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;*/
    }

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

