package com.example.hww8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.StudentAdapter;
import Database.StudentDatabase;
import model.Student;

public class ActivityA extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;

    private ImageView addStd;
    private TextView studentName;
    private TextView studentAddr;
    private TextView studentPNumber;

    private static final int REQUEST_CODE_2 = 222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        init();
        handleEvent();
    }

    public void init() {
        addStd = findViewById(R.id.add_std_iv);
        studentName = findViewById(R.id.std_name);
        studentAddr = findViewById(R.id.std_address);
        studentPNumber = findViewById(R.id.std_pn);


        StudentDatabase studentDatabase = new StudentDatabase(this);
        studentList = studentDatabase.getDataFromDB();

//        for (int i = 0; i < 20; i++) {
//            studentList.add(new Student("Name " + i, "Address " + i, "Phone number " + i));
//        }


        recyclerView = findViewById(R.id.std_rcv);
        studentAdapter = new StudentAdapter(this, studentList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void handleEvent() {
        addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityC.class);
                startActivityForResult(intent, REQUEST_CODE_2);
            }
        });
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public StudentAdapter getStudentAdapter() {
        return studentAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StudentAdapter.getRequestCode1() && resultCode == RESULT_OK) {
            int pos = (int) data.getExtras().get("pos");
            Student student = (Student) data.getExtras().get("obj_std");

            writetoDB(student);

            studentList.get(pos).setName(student.getName());
            studentList.get(pos).setAddress(student.getAddress());
            studentList.get(pos).setPhoneNumber(student.getPhoneNumber());

            studentAdapter.notifyItemChanged(pos);



        } else if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_FIRST_USER) {
            Student student = (Student) data.getExtras().get("obj_std");
            studentList.add(student);
            studentAdapter.notifyItemInserted(studentList.size() - 1);

            Toast.makeText(this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
            recyclerView.scrollToPosition(studentList.size() - 1);
        }
    }

    public static int getRequestCode2() {
        return REQUEST_CODE_2;
    }

    public void writetoDB(Student std) {
        StudentDatabase studentDatabase = new StudentDatabase(this);
        studentDatabase.update(std);
    }
}