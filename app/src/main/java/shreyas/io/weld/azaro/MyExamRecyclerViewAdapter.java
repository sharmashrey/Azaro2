package shreyas.io.weld.azaro;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shreyas.io.weld.azaro.CourseFragment.OnListFragmentInteractionListener;
import shreyas.io.weld.azaro.Model.Exam;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyExamRecyclerViewAdapter extends RecyclerView.Adapter<MyExamRecyclerViewAdapter.ViewHolder> {

    private final List<Exam> mValues;
    private final ExamFragment.OnListFragmentInteractionListener mListener;

    public MyExamRecyclerViewAdapter(List<Exam> items, ExamFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getExamId()));
        holder.mContentView.setText(String.valueOf(mValues.get(position).getExamDescription()));
        holder.mTitleView.setText(mValues.get(position).getExamName());

     //   Log.d("Courselist",":ST :"+position + mValues.get(position).getCourseStartTime());
      //  Log.d("Courselist",":ET :"+position + mValues.get(position).getCourseEndTime());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
              //      mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mTitleView;
        public Exam mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.in_exam_time);
            mContentView = (TextView) view.findViewById(R.id.course_Id);
            mTitleView = (TextView) view.findViewById(R.id.course_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
