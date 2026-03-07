package com.example.memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    EditText memoInput;
    RecyclerView memoList;
    Button addButton;
    Button deleteButton;
    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        memoInput  = findViewById(R.id.memoInput);
        memoList   = findViewById(R.id.memoList);
        addButton  = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        memoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemoAdapter(db.getAllMemos());
        memoList.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String memoText = memoInput.getText().toString().trim();
            if (!memoText.isEmpty()) {
                db.addMemo(memoText);
                memoInput.setText("");
                refreshList();
            } else {
                Toast.makeText(this, "Please enter a memo.", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            Memo selected = adapter.getSelectedMemo();
            if (selected != null) {
                db.deleteMemo(selected.getId());
                refreshList();
                Toast.makeText(this, "Memo #" + selected.getId() + " deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Tap a memo to select it first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        List<Memo> updatedMemos = db.getAllMemos();
        adapter.updateData(updatedMemos);
    }
}