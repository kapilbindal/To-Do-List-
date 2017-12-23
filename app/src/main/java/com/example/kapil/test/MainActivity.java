package com.example.kapil.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.kapil.test.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> taskList ;
    ListView lvTask;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = new ArrayList<>();

        lvTask = findViewById(R.id.lvTask);
//        final ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                Tasks
//        );
        final TaskAdapter taskAdapter = new TaskAdapter();
        lvTask.setAdapter(taskAdapter);

        b1 = findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = findViewById(R.id.ed);
                String data = ed.getText().toString();
               if(!data.isEmpty()){
                   Task t = new Task();
                   t.setData(data);
                   t.setChecked(false);
                   taskList.add(t);
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
            return taskList.size();
        }

        @Override
        public Task getItem(int i) {
            return taskList.get(i);
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

            final Task thisTask = getItem(i);

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskList.remove(thisTask);
                    taskList.trimToSize();
                    notifyDataSetChanged();
                }
            });
            i++;
            tv.setText(thisTask.getData());
            sNo.setText(i + ".  ");
            cb.setChecked(thisTask.getChecked());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        thisTask.setChecked(true);
                    }
                    else if(!b){
                        thisTask.setChecked(false);
                    }
                }
            });
            return taskView;
        }
    }
}
