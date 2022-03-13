package com.example.hww8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Database.StudentDatabase;
import model.Student;

public class ActivityB extends AppCompatActivity {

    private EditText stdNameEdit;
    private EditText stdAddrEdit;
    private EditText stdPNumberEdit;
    private Button saveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        init();
        int pos = (int) getIntent().getExtras().get("pos");
        Student student = (Student) getIntent().getExtras().get("obj_std");

        stdNameEdit.setText(student.getName());
        stdAddrEdit.setText(student.getAddress());
        stdPNumberEdit.setText(student.getPhoneNumber());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = stdNameEdit.getText().toString();
                String newAddress = stdAddrEdit.getText().toString();
                String newPNumber = stdPNumberEdit.getText().toString();
                Student std = new Student(newName, newAddress, newPNumber,student.getId());

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putSerializable("obj_std", std);
                bundle.putInt("pos", pos);

                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);

                onBackPressed();

            }
        });


    }

    public void init() {
        stdNameEdit = findViewById(R.id.tv_std_name_edit);
        stdAddrEdit = findViewById(R.id.tv_std_address_edit);
        stdPNumberEdit = findViewById(R.id.tv_std_pnumber_edit);
        saveBtn = findViewById(R.id.save_btn_B);
    }

}
