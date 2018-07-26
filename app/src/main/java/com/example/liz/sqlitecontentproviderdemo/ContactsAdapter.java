package com.example.liz.sqlitecontentproviderdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private List<Contacts> mArrContacts;
    private static OnItemClickListener listener;

    public ContactsAdapter(List<Contacts> mArrContacts) {
     this.mArrContacts = mArrContacts;
    }


    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_layout,parent,false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.mTextViewName.setText(mArrContacts.get(position).getName());
        holder.mTextViewPhone.setText(mArrContacts.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mArrContacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewName;
        private TextView mTextViewPhone;
        private ImageButton mImageButton;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.text_name);
            mTextViewPhone = itemView.findViewById(R.id.text_phone);
            mImageButton= itemView.findViewById(R.id.button_call);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        Log.e("onClick","OK");
                        listener.OnItemClick(mArrContacts,getLayoutPosition());
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(List<Contacts> contacts,int position);
    }

    public void OnItemClickListener(OnItemClickListener listener){
        ContactsAdapter.listener = listener;
    }
}
