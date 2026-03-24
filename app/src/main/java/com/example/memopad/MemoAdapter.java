package com.example.memopad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memopad.databinding.ItemMemoBinding;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private final MainActivity activity;
    private List<Memo> data;

    public MemoAdapter(MainActivity activity, List<Memo> data) {
        super();
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMemoBinding binding = ItemMemoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(activity.getItemClick()); // attach click handler
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Memo memo = data.get(position);
        holder.setMemo(memo);
        holder.bindData();

        holder.itemView.setSelected(position == activity.getSelectedPosition());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Memo getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<Memo> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Memo memo;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setMemo(Memo memo) {
            this.memo = memo;
        }

        public void bindData() {
            TextView memoLabel = itemView.findViewById(R.id.memoText);
            memoLabel.setText("#" + memo.getId() + ": " + memo.getText());
        }
    }
}