package com.example.hww8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import Database.StudentDatabase;
import model.Student;

public class ActivityC extends AppCompatActivity {
    private EditText stdNameAdd;
    private EditText stdAddrAdd;
    private EditText stdPNumberAdd;
    private Button saveBtnC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        init();
        handleEvent();
    }

    public void init() {
        stdNameAdd = findViewById(R.id.tv_std_name_add);
        stdAddrAdd = findViewById(R.id.tv_std_address_add);
        stdPNumberAdd = findViewById(R.id.tv_std_pnumber_add);
        saveBtnC = findViewById(R.id.save_btn_C);
    }

    public void handleEvent() {
        saveBtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newName = stdNameAdd.getText().toString();
                String newAddr = stdAddrAdd.getText().toString();
                String newPNumber = stdPNumberAdd.getText().toString();

                if (!newName.equals("")
                        && !newAddr.equals("")
                        && !newPNumber.equals("")) {
                    String randomID = UUID.randomUUID().toString();
                    Student student = new Student(newName, newAddr, newPNumber, randomID);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("obj_std", student);
                    intent.putExtras(bundle);

                    setResult(RESULT_FIRST_USER, intent);

                    writetoDB(student);

                    onBackPressed();

                } else {
                    Toast.makeText(ActivityC.this, "Thông tin không được để trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writetoDB(Student student) {
        StudentDatabase studentDatabase = new StudentDatabase(this);
        studentDatabase.add(student);
    }
}
