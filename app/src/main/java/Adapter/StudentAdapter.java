package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hww8.ActivityA;
import com.example.hww8.ActivityB;
import com.example.hww8.R;

import java.util.List;

import Database.StudentDatabase;
import model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private List<Student> studentsList;
    private Context context;
    private ActivityA activityA;
    private static final int REQUEST_CODE_1 = 22;

    public StudentAdapter(ActivityA activityA, List<Student> studentsList, Context context) {
        this.activityA = activityA;
        this.studentsList = studentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View studentView = layoutInflater.inflate(R.layout.student, parent, false);

        return new StudentViewHolder(studentView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentsList.get(position);

        holder.getStudentName().setText(student.getName());
        holder.getStudentAddress().setText(student.getAddress());
        holder.getStudentPNumber().setText(student.getPhoneNumber());

        holder.getStudentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = activityA.getRecyclerView().getChildLayoutPosition(view);
                Intent intent = new Intent(context, ActivityB.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj_std", student);
                bundle.putInt("pos", pos);
                intent.putExtras(bundle);
                activityA.startActivityForResult(intent, REQUEST_CODE_1);
            }
        });

        holder.getStudentDel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();

                StudentDatabase studentDatabase = new StudentDatabase(activityA);
                studentDatabase.delete(studentsList.get(pos));

                studentsList.remove(pos);
                activityA.getStudentAdapter().notifyItemRemoved(pos);


            }
        });

    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public static int getRequestCode1() {
        return REQUEST_CODE_1;
    }
}

class StudentViewHolder extends RecyclerView.ViewHolder {
    private View studentView;
    private ImageView studentPic;
    private TextView studentName;
    private TextView studentAddress;
    private TextView studentPNumber;
    private ImageView studentDel;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        studentView = itemView.findViewById(R.id.std_view);
        studentPic = itemView.findViewById(R.id.std_pic_iv);
        studentName = itemView.findViewById(R.id.std_name);
        studentAddress = itemView.findViewById(R.id.std_address);
        studentPNumber = itemView.findViewById(R.id.std_pn);
        studentDel = itemView.findViewById(R.id.del_iv);
    }


    public View getStudentView() {
        return studentView;
    }

    public ImageView getStudentPic() {
        return studentPic;
    }

    public TextView getStudentName() {
        return studentName;
    }

    public TextView getStudentAddress() {
        return studentAddress;
    }

    public TextView getStudentPNumber() {
        return studentPNumber;
    }

    public ImageView getStudentDel() {
        return studentDel;
    }


}
