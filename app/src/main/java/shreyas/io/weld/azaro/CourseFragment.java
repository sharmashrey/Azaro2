package shreyas.io.weld.azaro;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import shreyas.io.weld.azaro.Database.DBHelper;
import shreyas.io.weld.azaro.Model.Course;

/** Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface. */
public class CourseFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    Context currentContext;
    MyItemRecyclerViewAdapter courseAdapter;
    DBHelper dbHelper;
    List<Course> coursesList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CourseFragment() {}

    //TODO :=- refresh fragment
   // FragmentTransaction ft = getFragmentManager().beginTransaction();
   // ft.detach(this).attach(this).commit();

    public static CourseFragment newInstance(int columnCount) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            //if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //} else {
                //recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            //}

            //write code to retrieve list of courses from database and pass that to

            coursesList = dbHelper.getAllCourses();
            courseAdapter = new MyItemRecyclerViewAdapter(coursesList, mListener);
            recyclerView.setAdapter(courseAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        coursesList = dbHelper.getAllCourses();;
        courseAdapter.setCourseList(coursesList);
        courseAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentContext = context;
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        dbHelper = new DBHelper(currentContext);
        
        
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Course item);
    }



}
