package shreyas.io.weld.azaro;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.Calendar;

import shreyas.io.weld.azaro.Database.DBHelper;
import shreyas.io.weld.azaro.Model.Assignment;
import shreyas.io.weld.azaro.Model.Course;
import shreyas.io.weld.azaro.Model.Project;
import shreyas.io.weld.azaro.Model.Task;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirstFragment.OnFragmentInteractionListener, CourseFragment.OnListFragmentInteractionListener,
                    TaskFragment.OnListFragmentInteractionListener, ProjectFragment.OnListFragmentInteractionListener, AssignmentFragment.OnListFragmentInteractionListener{

    public int currentfragment = 0;
    // Database Helper
    DBHelper db;
    private String m_Text = ""; //text for inner fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db operations
        db = new DBHelper(getApplicationContext());
        //fillup db

        //DUMP DATABASE
        //context.deleteDatabase(db);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on click of floating button, start new activity
                if(currentfragment == 1){
                    //

                }else if(currentfragment == 2){
                    Intent myIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                    myIntent.putExtra("key", 2); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }else if(currentfragment == 3){
                    //launch related activity
                    Intent myIntent = new Intent(MainActivity.this, AddCourseActivity.class);
                    myIntent.putExtra("key", 3); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }else if(currentfragment == 4){
                    Intent myIntent = new Intent(MainActivity.this, ProjectActivity.class);
                    myIntent.putExtra("key", 4); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }else if(currentfragment == 5){
                    
                    Intent myIntent = new Intent(MainActivity.this, AddAssignmentActivity.class);
                    myIntent.putExtra("key", 5); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_dashboard_fragment) {
            // Stay on Homepage
        }
        else if (id == R.id.nav_task_fragment) {
            fragment = TaskFragment.newInstance(2);
            currentfragment = 2;
        }
        else if (id == R.id.nav_course) {
            fragment = CourseFragment.newInstance(3);
            currentfragment = 3;
        }
        else if (id == R.id.nav_project) {
            fragment = ProjectFragment.newInstance(4);
            currentfragment = 4;
        }else if (id == R.id.nav_assignment_fragment) {
            fragment = AssignmentFragment.newInstance(5);
            currentfragment = 5;
        }
        
        else if (id == R.id.nav_navigation) {

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction(final Course item) {

        //first show an alert dialog to edit/delete. on delete click call delete method. on edit click
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_speaker_dark);
        builderSingle.setTitle("Edit Course "+ item.getCourseName());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dialog_item_layout);

        arrayAdapter.add("Update");
        arrayAdapter.add("Delete");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); }
                });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if(strName.equalsIgnoreCase("Update") ){
                        final AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                            final EditText input = new EditText(MainActivity.this);
                            final EditText input2 = new EditText(MainActivity.this);

                            builderInner.setMessage(strName);
                            builderInner.setTitle("Update Relevant Info");
                            final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            final View myView=inflater.inflate(R.layout.update_dialog_course,null);


                            // Set up the Edit Text Containing Information
                           /* input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builderInner.setView(input);
                            input2.setInputType(InputType.TYPE_CLASS_TEXT);
                            builderInner.setView(input2);*/
                            builderInner.setView(myView);
                            EditText updateWeekDay=((EditText)myView.findViewById(R.id.UpdateCourseWeekDay));
                            EditText updateCourseName= ((EditText)myView.findViewById(R.id.UpdateCourseName));
                            EditText updateLocation=((EditText)myView.findViewById(R.id.UpdateCourseLocation));
                            EditText updateStartTime=(((EditText)myView.findViewById(R.id.update_in_time)));
                            EditText updateEndTime=(((EditText)myView.findViewById(R.id.update_in_end_time)));

                     //       updateEndTime.setText(convertTimeMillisToString(item.getCourseEndTime()));

                    //        updateStartTime.setText(convertTimeMillisToString(item.getCourseStartTime() ));
                            updateCourseName.setText(item.getCourseName());
                            updateWeekDay.setText(item.getWeekDay());
                            updateLocation.setText(item.getCourseLocation());


                            final EditText input3 = new EditText(MainActivity.this);

                           // EditText mEditCourseName = (EditText) findViewById(R.id.EditCourseNameDialogueBx);

                            // Set up the buttons
                        builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                m_Text = input.getText().toString();
                                m_Text = input2.getText().toString();
                            }
                        });


                            builderInner.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("onclick", "onClick: "+"done");
                                    String courselocation=((EditText)myView.findViewById(R.id.UpdateCourseLocation)).getText().toString();
                                    String coursename=((EditText)myView.findViewById(R.id.UpdateCourseName)).getText().toString();

                                    String weekday=((EditText)myView.findViewById(R.id.UpdateCourseWeekDay)).getText().toString();
                                    //long starttime=convertTimeStringToMillis(((EditText)myView.findViewById(R.id.update_in_time)).getText().toString());
                                    //long endtime=convertTimeStringToMillis(((EditText)myView.findViewById(R.id.update_in_end_time)).getText().toString());
                                    Course updatedcourse=new Course();
                         //           updatedcourse.setCourseEndTime(endtime);
                                    updatedcourse.setCourseLocation(courselocation);
                                    updatedcourse.setCourseName(coursename);
                        //            updatedcourse.setCourseStartTime(starttime);
                                    updatedcourse.setCourseId(item.getCourseId());
                                    db.updateCourse(updatedcourse);

                                }


//                                long convertTimeStringToMillis(String iptime){
//
//                                    String[] endTimeString =  iptime.split(":");
//                                    //String endTimeString = Integer.toString(endHour)+Integer.toString(endMinute);
//                                    //long endTime = Long.parseLong(endTimeString[0]+endTimeString[1]);
//                                    Calendar cal = Calendar.getInstance();
//                                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeString[0]));
//                                    cal.set(Calendar.MINUTE, Integer.parseInt(endTimeString[1]));
//                                    cal.set(Calendar.SECOND, 0);
//
//                                    // obtain given time in ms
//                                    long endTime = cal.getTimeInMillis();
//                                    return endTime;
//                                }

                            });
                        builderInner.show();
                    }else {  // Delete selected , implement it
                            db.deleteCourse(item);
                        }


                    }
                });
        builderSingle.show();
        //for alert dialogue

        //this is the item which was clicked.
        //display all info in an alert dialog in edit texts. but button save. on click of that button
        //call updatestudentcourse method and pass the new updated student course object
        //
    }

    @Override
    public void onListFragmentInteraction(final Task item) {

        //first show an alert dialog to edit/delete. on delete click call delete method. on edit click
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_speaker_dark);
        builderSingle.setTitle("Edit Task "+ item.getTaskName());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dialog_task_layout);

        arrayAdapter.add("Update");
        arrayAdapter.add("Delete");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName.equalsIgnoreCase("Update") ){
                    final AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    final EditText input2 = new EditText(MainActivity.this);

                    builderInner.setMessage(strName);
                    builderInner.setTitle("Update Relevant Info");


                    // Set up the Edit Text Containing Information
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input);
                    input2.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input2);

                    final EditText input3 = new EditText(MainActivity.this);

                    // EditText mEditCourseName = (EditText) findViewById(R.id.EditCourseNameDialogueBx);

                    // Set up the buttons
                    builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            m_Text = input.getText().toString();
                            m_Text = input2.getText().toString();
                        }
                    });

                    builderInner.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
                else if(strName.equalsIgnoreCase("Delete")){  // Delete selected , implement it
                    db.deleteTask(item);
                }
            }
        });
        builderSingle.show();
    }

    @Override
    public void onListFragmentInteraction(final Project item) {

        //first show an alert dialog to edit/delete. on delete click call delete method. on edit click
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_speaker_dark);
        builderSingle.setTitle("Edit Project "+ item.getProjectName());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dialog_project_layout);

        arrayAdapter.add("Add Project Phase");
        arrayAdapter.add("Update");
        arrayAdapter.add("Delete");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName.equalsIgnoreCase("Update") ){
                    final AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    final EditText input2 = new EditText(MainActivity.this);

                    builderInner.setMessage(strName);
                    builderInner.setTitle("Update Relevant Info");


                    // Set up the Edit Text Containing Information
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input);
                    input2.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input2);

                    final EditText input3 = new EditText(MainActivity.this);

                    // EditText mEditCourseName = (EditText) findViewById(R.id.EditCourseNameDialogueBx);

                    // Set up the buttons
                    builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            m_Text = input.getText().toString();
                            m_Text = input2.getText().toString();
                        }
                    });

                    builderInner.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
                else if(strName.equalsIgnoreCase("Add Project Phase")){  // Delete selected , implement it




                }
                else if(strName.equalsIgnoreCase("Delete")){  // Delete selected , implement it
                    db.deleteProject(item);
                }
            }
        });
        builderSingle.show();
    }

    @Override
    public void onListFragmentInteraction(final Assignment item) {
        //first show an alert dialog to edit/delete. on delete click call delete method. on edit click
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_speaker_dark);
        builderSingle.setTitle("Edit Assignment "+ item.getAssignmentName());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dialog_assignment_layout);

        arrayAdapter.add("Update");
        arrayAdapter.add("Delete");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName.equalsIgnoreCase("Update") ){
                    final AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    final EditText input2 = new EditText(MainActivity.this);

                    builderInner.setMessage(strName);
                    builderInner.setTitle("Update Relevant Info");


                    // Set up the Edit Text Containing Information
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input);
                    input2.setInputType(InputType.TYPE_CLASS_TEXT);
                    builderInner.setView(input2);

                    final EditText input3 = new EditText(MainActivity.this);

                    // EditText mEditCourseName = (EditText) findViewById(R.id.EditCourseNameDialogueBx);

                    // Set up the buttons
                    builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            m_Text = input.getText().toString();
                            m_Text = input2.getText().toString();
                        }
                    });

                    builderInner.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
                else if(strName.equalsIgnoreCase("Delete")){  // Delete selected , implement it
                    db.deleteAssignment(item);
                }
            }
        });
        builderSingle.show();
    }
                        
}
