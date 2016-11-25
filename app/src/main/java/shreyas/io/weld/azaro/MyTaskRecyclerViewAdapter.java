package shreyas.io.weld.azaro;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import shreyas.io.weld.azaro.Model.Task;
import shreyas.io.weld.azaro.TaskFragment.OnListFragmentInteractionListener;
import java.util.List;

public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.TaskViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTaskName.setText(String.valueOf(mValues.get(position).getTaskName()));
        holder.mTaskCourseName.setText(String.valueOf(mValues.get(position).getTaskCourseName()));
        holder.mTaskDueDate.setText(mValues.get(position).getTaskDueDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface(the activity, if fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTaskName;
        public final TextView mTaskCourseName;
        public final TextView mTaskDueDate;
        public Task mItem;

        public TaskViewHolder(View view) {
            super(view);
            mView = view;
            mTaskName = (TextView) view.findViewById(R.id.task_name);
            mTaskCourseName = (TextView) view.findViewById(R.id.task_course_name);
            mTaskDueDate = (TextView) view.findViewById(R.id.task_date);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTaskName.getText() + "'";
        }
    }
}
