package com.example.kapil.test.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kapil.test.model.Task;

import java.util.ArrayList;

import static com.example.kapil.test.db.Consts.*;

/**
 * Created by KAPIL on 07-01-2018.
 */

public class TodoTable {
    private TodoTable () {}

    public static final String TABLE_NAME = "todos";

    public interface Columns {
        String ID = "id";
        String TASK = "data";
        String DONE = "isChecked";
    }
    public static final String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE +  TABLE_NAME
                    + LBR
                    + Columns.ID + TYPE_INT + TYPE_PK_AI + COMMA
                    + Columns.TASK + TYPE_TEXT + COMMA
                    + Columns.DONE + TYPE_BOOLEAN
                    + RBR + SEMI;

    public static long insertTodo (Task todo, SQLiteDatabase db) {
        ContentValues newTodo = new ContentValues();
        newTodo.put(Columns.TASK, todo.getData());
        newTodo.put(Columns.DONE, todo.getChecked());

        return db.insert(TABLE_NAME, null, newTodo);
    }

    public static void updateTodo (SQLiteDatabase db, boolean check, int id){
        ContentValues newTodo = new ContentValues();
        newTodo.put(Columns.DONE,check);
        db.update(TABLE_NAME,newTodo,"ID="+id,null);
    }

    public static void deleteTask (SQLiteDatabase db, int id){
        db.delete(TABLE_NAME,"ID="+id,null);
    }

    public static ArrayList<Task> getAllTodos (SQLiteDatabase db) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID, Columns.TASK, Columns.DONE},
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<Task> todos = new ArrayList<>();
        c.moveToFirst();
        int taskIndex = c.getColumnIndex(Columns.TASK);
        int idIndex = c.getColumnIndex(Columns.ID);
        int doneIndex = c.getColumnIndex(Columns.DONE);

        while (!c.isAfterLast()) {
            todos.add(new Task(
                    c.getString(taskIndex),
                    c.getInt(doneIndex) == 1,
                    c.getInt(idIndex)
            ));

            c.moveToNext();
        }
        return todos;
    }
}
