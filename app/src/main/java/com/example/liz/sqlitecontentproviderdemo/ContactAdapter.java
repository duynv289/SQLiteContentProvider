package com.example.liz.sqlitecontentproviderdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactsViewHolder> {
    private List<Contact> mContact;
    private static OnItemClickListener mListener;

    public ContactAdapter(List<Contact> mContact) {
     this.mContact = mContact;
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
        holder.setView(mContact.get(position));
    }

    @Override
    public int getItemCount() {
        if (mContact != null){
            return mContact.size();
        }
        return 0;
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextViewName;
        private TextView mTextViewPhone;
        private ImageButton mImageButton;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.text_name);
            mTextViewPhone = itemView.findViewById(R.id.text_phone);
            mImageButton= itemView.findViewById(R.id.button_call);
            mImageButton.setOnClickListener(this);
        }
        public void setView(Contact contact){
            mTextViewName.setText(contact.getmName());
            mTextViewPhone.setText(contact.getmPhoneNumber());

        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.OnItemClick(mContact,getLayoutPosition());
            }
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(List<Contact> contacts, int position);
    }

    public void OnItemClickListener(OnItemClickListener listener){
        ContactAdapter.mListener = listener;
    }
}
