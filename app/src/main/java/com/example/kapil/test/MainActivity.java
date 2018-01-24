package com.example.kapil.test;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapil.test.adapter.TaskAdapter;
import com.example.kapil.test.db.TodoDatabaseHelper;
import com.example.kapil.test.db.TodoTable;
import com.example.kapil.test.model.Task;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "s";
    //ArrayList<Task> taskList ;
    ListView lvTask;
    Button b1;
    SQLiteDatabase readDb;
    SQLiteDatabase writeDb;
    ArrayList<Task> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todos = new ArrayList<>();

        TodoDatabaseHelper myDbHelper = new TodoDatabaseHelper(this);
        writeDb = myDbHelper.getWritableDatabase();
        readDb = myDbHelper.getReadableDatabase();
        lvTask = findViewById(R.id.lvTask);
        //final TaskAdapter taskAdapter = new TaskAdapter(this,todos,writeDb);
        final TaskAdapter taskAdapter = new TaskAdapter();
        lvTask.setAdapter(taskAdapter);

        if(todos != null) {
            todos = TodoTable.getAllTodos(readDb);
            taskAdapter.notifyDataSetChanged();
        }


        b1 = findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = findViewById(R.id.ed);
                String data = ed.getText().toString();
               if(!data.isEmpty()){
                   Task t = new Task(data,false,1);
                   TodoTable.insertTodo(t,writeDb);
                   todos.add(t);
                   taskAdapter.notifyDataSetChanged();
                   ed.setText("");
                   Toast.makeText(MainActivity.this, "Task Added" , Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(MainActivity.this, "Enter the Data" , Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    class TaskAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return todos.size();
        }


        @Override
        public Task getItem(int i) {
            return todos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater li = getLayoutInflater();
            View taskView = li.inflate(R.layout.task_layout,parent,false);
            TextView tv = taskView.findViewById(R.id.tv1);
            TextView sNo = taskView.findViewById(R.id.sNo);
            ImageView del = taskView.findViewById(R.id.delete);
            CheckBox cb = taskView.findViewById(R.id.ch);

            final Task thisTask = todos.get(i);
            i++;
            tv.setText(thisTask.getData());
            sNo.setText(i + ".  ");
            cb.setChecked(thisTask.getChecked());

            final int finalI1 = i;
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int thisID = thisTask.getId();
                    todos.remove(thisTask);
                    todos.trimToSize();
                    Log.d(TAG, "onClick: " + thisID);
                    TodoTable.deleteTask(writeDb, thisID);
                    notifyDataSetChanged();
                }
            });

            final int finalI = i;
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        thisTask.setChecked(true);
                        TodoTable.updateTodo(writeDb,true, finalI);
                    }
                    else if(!b){
                        thisTask.setChecked(false);
                        TodoTable.updateTodo(writeDb,false, finalI);
                    }
                }
            });
            return taskView;
        }
    }
}
