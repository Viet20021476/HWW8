package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Student;

public class StudentDatabase extends SQLiteOpenHelper {

    private static final String STUDENT_TABLE = "Student_Table";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String P_NUMBER = "pNumber";

    public StudentDatabase(Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + "(" + ID + " TEXT PRIMARY KEY," +
                " " + NAME + " TEXT, " + ADDRESS + " TEXT, " + P_NUMBER + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(Student std) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String insertStatement = "INSERT INTO " + STUDENT_TABLE + "(" + ID + ","
                + NAME + "," + ADDRESS + "," + P_NUMBER + ") VALUES (?,?,?,?)";

        sqLiteDatabase.execSQL(insertStatement,
                new String[]{std.getId(), std.getName(), std.getAddress(), std.getPhoneNumber()});

    }

    public void update(Student std) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String updateStatement = "UPDATE " + STUDENT_TABLE + " SET "
                + NAME + " = ?,"
                + ADDRESS + " = ?,"
                + P_NUMBER + " = ? WHERE " + ID + " = ?";
        sqLiteDatabase.execSQL(updateStatement, new String[]{std.getName(), std.getAddress(),
                std.getPhoneNumber(), std.getId()});

    }

    public void delete(Student std) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String deleteStatement = "DELETE FROM " + STUDENT_TABLE + " WHERE " + ID + " = ?";
        sqLiteDatabase.execSQL(deleteStatement, new String[]{std.getId()});
    }

    public List<Student> getDataFromDB() {
        List<Student> returnData = new ArrayList<>();
        String queryString = "SELECT * FROM " + STUDENT_TABLE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String stdId = cursor.getString(0);
                String stdName = cursor.getString(1);
                String stdAddress = cursor.getString(2);
                String stdPNumber = cursor.getString(3);

                returnData.add(new Student(stdName, stdAddress, stdPNumber, stdId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return returnData;

    }
}
