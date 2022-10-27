package com.example.w7_q34;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteActivity extends AppCompatActivity {

    TextView thing;
    Button delete;
    String id, todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        thing = findViewById(R.id.thing);
        delete = findViewById(R.id.delete);
        getInfo();
        delete.setOnClickListener(view -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(DeleteActivity.this);
            dataBaseHelper.deleteData(id);
            finish();
        });
    }

    private void getInfo() {
        if (getIntent().hasExtra("todo")) {
            id = getIntent().getStringExtra("id");
            todo = getIntent().getStringExtra("todo");
            thing.setText(todo);
        } else {
            Toast.makeText(this, "No intent data", Toast.LENGTH_SHORT).show();
        }
    }
}