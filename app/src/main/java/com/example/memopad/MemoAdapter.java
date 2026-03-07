package com.example.memopad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.memopad.databinding.ItemMemoBinding;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private static final int NO_SELECTION = -1;

    private ItemMemoBinding binding;
    private List<Memo> data;
    private int selectedPosition = NO_SELECTION;

    public MemoAdapter(List<Memo> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();

        holder.itemView.setSelected(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int clickedPos = holder.getBindingAdapterPosition();
            int previousPos = selectedPosition;

            if (selectedPosition == clickedPos) {
                selectedPosition = NO_SELECTION;
            } else {
                selectedPosition = clickedPos;
            }

            notifyItemChanged(clickedPos);
            if (previousPos != NO_SELECTION) {
                notifyItemChanged(previousPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Memo getSelectedMemo() {
        if (selectedPosition == NO_SELECTION || selectedPosition >= data.size()) {
            return null;
        }
        return data.get(selectedPosition);
    }

    public void updateData(List<Memo> newData) {
        this.data = newData;
        this.selectedPosition = NO_SELECTION;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Memo memo;
        private TextView memoLabel;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public Memo getMemo() {
            return memo;
        }

        public void setMemo(Memo memo) {
            this.memo = memo;
        }

        public void bindData() {
            if (memoLabel == null) {
                memoLabel = (TextView) itemView.findViewById(R.id.memoText);
            }
            memoLabel.setText("#" + memo.getId() + ": " + memo.getText());
        }

    }

}