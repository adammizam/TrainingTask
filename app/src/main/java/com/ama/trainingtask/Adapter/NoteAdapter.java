package com.ama.trainingtask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ama.trainingtask.R;
import com.ama.trainingtask.model.NoteModel;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private final Context mContext;
    private final ArrayList<NoteModel> mNoteList;
    private NoteAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public NoteAdapter(Context context, ArrayList<NoteModel> noteList){
        mContext = context;
        mNoteList = noteList;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_note_list, parent, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteModel noteModel = mNoteList.get(position);

        String noteTitle = noteModel.getNoteTitle();
        String noteContent = noteModel.getNoteContent();

        if(noteContent.length() > 10) noteContent = noteContent.substring(0,10);

        holder.mNoteTitleText.setText("TITLE: " + noteTitle + "");
        holder.mNoteContentText.setText("~" + noteContent + "~");
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView mNoteTitleText, mNoteContentText;
        public CardView mCardViewLayout;

        public NoteViewHolder(View itemView) {
            super(itemView);

            mNoteTitleText = itemView.findViewById(R.id.noteTitleTxt);
            mNoteContentText = itemView.findViewById(R.id.noteContentTxt);
            mCardViewLayout = itemView.findViewById(R.id.layoutCardNoteActivityAdapter);

            mCardViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
