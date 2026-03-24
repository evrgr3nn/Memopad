package com.example.memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memopad.dao.DAOFactory;
import com.example.memopad.dao.MemoDAO;

public class MainActivity extends AppCompatActivity {
    private DAOFactory daoFactory;
    private MemoDAO memoDAO;
    private EditText memoInput;
    private RecyclerView memoList;
    private Button addButton;
    private Button deleteButton;
    private MemoAdapter adapter;

    private int selectedPosition = -1;

    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoFactory = new DAOFactory(this);
        memoDAO = daoFactory.getMemoDao();

        memoInput    = findViewById(R.id.memoInput);
        memoList     = findViewById(R.id.memoList);
        addButton    = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        memoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemoAdapter(this, memoDAO.list());
        memoList.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String memoText = memoInput.getText().toString().trim();
            if (!memoText.isEmpty()) {
                memoDAO.create(new Memo(0, memoText)); // id=0; DB assigns real id
                memoInput.setText("");
                refreshList();
            } else {
                Toast.makeText(this, "Please enter a memo.", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Tap a memo to select it first.", Toast.LENGTH_SHORT).show();
                return;
            }
            Memo selected = adapter.getItem(selectedPosition);
            memoDAO.delete(selected.getId());
            selectedPosition = -1;
            refreshList();
            Toast.makeText(this, "Memo #" + selected.getId() + " deleted.", Toast.LENGTH_SHORT).show();
        });
    }

    private void refreshList() {
        adapter.updateData(memoDAO.list());
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public MemoPadItemClickHandler getItemClick() {
        return itemClick;
    }

    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = memoList.getChildLayoutPosition(v);
            int previousPosition = selectedPosition;

            if (selectedPosition == position) {
                selectedPosition = -1; // tap again to deselect
            } else {
                selectedPosition = position;
            }

            adapter.notifyItemChanged(position);
            if (previousPosition != -1) {
                adapter.notifyItemChanged(previousPosition);
            }
        }
    }
}