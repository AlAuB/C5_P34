package com.example.w7_q34;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> id, things, dateTime;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        id = new ArrayList<>();
        things = new ArrayList<>();
        dateTime = new ArrayList<>();
        recyclerView = findViewById(R.id.lists);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.no_data);
        floatingActionButton = findViewById(R.id.add);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
        getAllData();
        customAdapter = new CustomAdapter(MainActivity.this, this, id, things, dateTime);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private void getAllData() {
        Cursor cursor = dataBaseHelper.readAllData();
        if (cursor.getCount() == 0) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                things.add(cursor.getString(1));
                dateTime.add(cursor.getString(2));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure to delete all data?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.deleteAll();
            Toast.makeText(this, "Delete all data", Toast.LENGTH_SHORT).show();
            recreate();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            //Do nothing
        });
        builder.create().show();
    }
}