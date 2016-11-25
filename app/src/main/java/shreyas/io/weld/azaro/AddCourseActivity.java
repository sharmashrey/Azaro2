package shreyas.io.weld.azaro;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import shreyas.io.weld.azaro.Database.DBHelper;
import shreyas.io.weld.azaro.Model.Course;



/**
 * A login screen that offers login via email/password.
 */

class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;

    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString(), mContext);

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                    //notifyDataSetChanged();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}

public class AddCourseActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,View.OnClickListener{

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
    private AutoCompleteTextView mEditCourseLocationView;
    private EditText mEditCourseName;
    // private EditText mEditCourseLocationView;
    private EditText mEditCourseWeekDay;
    //private EditText txtDate;
    private EditText txtTime;
    private EditText txtEndTime;

    //Button btnDatePicker;
    Button btnStartTimePicker;
    Button btnEndTimePicker;
    Button btnCourseSave;
    DBHelper db;

    //In order to send date to DB
    private int mYear, mMonth, mDay;
    //In order to send time in DB
    private int startHour, startMinute;
    private int endHour, endMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //make message text field & button object

        mEditCourseLocationView = (AutoCompleteTextView) this.findViewById(R.id.EditCourseLocation);
        PlacesAutoCompleteAdapter paa=new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item);
        mEditCourseLocationView.setAdapter(paa);
        mEditCourseName = (EditText) findViewById(R.id.EditCourseName);
        //mEditCourseLocationView = (EditText) findViewById(R.id.EditCourseLocation);
        mEditCourseWeekDay=(EditText) findViewById(R.id.EditCourseWeekDay);
        btnCourseSave = (Button) findViewById(R.id.save_button);
        //btn dt tm pckr
        //btnDatePicker=(Button)findViewById(R.id.btn_date);
        //txtDate=(EditText)findViewById(R.id.in_date);
        //btnDatePicker.setOnClickListener(this);

        btnStartTimePicker=(Button)findViewById(R.id.btn_time);
        txtTime=(EditText)findViewById(R.id.in_time);
        btnStartTimePicker.setOnClickListener(this);

        btnEndTimePicker=(Button)findViewById(R.id.btn_end_time);
        txtEndTime= (EditText)findViewById(R.id.in_end_time);
        btnEndTimePicker.setOnClickListener(this);


        //   Retrieve info from other end
        //    Extras are retrieved on the other side via:
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
    }


    @Override
    public void onClick(View v) {
       /*
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
        else
        */
        if (v == btnStartTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            startHour = c.get(Calendar.HOUR_OF_DAY);
            startMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, startHour, startMinute, false);
            timePickerDialog.show();
        }else if(v == btnEndTimePicker){
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            endHour = c.get(Calendar.HOUR_OF_DAY);
            endMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtEndTime.setText(hourOfDay + ":" + minute);
                        }
                    }, endHour, endMinute, false);

            timePickerDialog.show();
        }
    }

    //When the send button is clicked
    public void saveCourse(View v)
    {
        // ADD TO DATABASE
        db = new DBHelper(getApplicationContext());
        Log.d("In Save button", " " + mEditCourseName.getText().toString());
        Course newCourse= new Course();
        newCourse.setCourseName( mEditCourseName.getText().toString());
        newCourse.setCourseLocation( mEditCourseLocationView.getText().toString());
        newCourse.setWeekDay(mEditCourseWeekDay.getText().toString());
        // String[] startTimeString =  txtTime.getText().toString().split(":");
        //String startTimeString = Integer.toString(startHour)+Integer.toString(startMinute);
        //long startTime = Long.parseLong(startTimeString[0]+startTimeString[1]);
        //String startTime = String.valueOf(startHour)+":"+String.valueOf(startMinute);
        Log.d("In Save button", "ST " + txtTime.getText().toString());
        newCourse.setCourseStartTime(txtTime.getText().toString());
        Log.d("In Save button", " sT " + txtTime.getText().toString());


        /*String[] endTimeString =  txtEndTime.getText().toString().split(":");
        //String endTimeString = Integer.toString(endHour)+Integer.toString(endMinute);
        //long endTime = Long.parseLong(endTimeString[0]+endTimeString[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeString[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(endTimeString[1]));
        cal.set(Calendar.SECOND, 0);*/

        // obtain given time in ms
        //String endTime = String.valueOf(endHour)+":"+String.valueOf(endMinute);
        //long endTime = convertTimeStringToMillis(txtEndTime.getText().toString());

        //Log.d("In Save button","Convert back to date"+convertTimeMillisToString(endTime));
        Log.d("In Save button", "ET " + txtEndTime.getText().toString());
        newCourse.setCourseEndTime(txtEndTime.getText().toString());
        Log.d("In Save button", " eT " + txtEndTime.getText().toString());



        db.addNewCourse(newCourse);
        finish();
    }

    String convertTimeMillisToString(long ip){
        Date d=new Date(ip);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(d);
        return dateString;
    }

    long convertTimeStringToMillis(String iptime){

        String[] endTimeString =  iptime.split(":");
        //String endTimeString = Integer.toString(endHour)+Integer.toString(endMinute);
        //long endTime = Long.parseLong(endTimeString[0]+endTimeString[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeString[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(endTimeString[1]));
        cal.set(Calendar.SECOND, 0);

        // obtain given time in ms
        long endTime = cal.getTimeInMillis();
        return endTime;
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
                new ArrayAdapter<>(AddCourseActivity.this,
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

