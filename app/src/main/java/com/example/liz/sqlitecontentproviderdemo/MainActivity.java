package com.example.liz.sqlitecontentproviderdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> mContact;
    private RecyclerView mRecyclerView;
    private ContactAdapter mContactAdapter;
    private static final String TITLE_TEL = "tel:";

    private static final int REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mContact = new ArrayList<>();
        //check permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        } else {
            readContacts();
        }
        mContactAdapter = new ContactAdapter(mContact);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mContactAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mContactAdapter.OnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(List<Contact> contacts, int position) {
                String phone = contacts.get(position).getmPhoneNumber();
                String uri = TITLE_TEL + phone.trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                if (ActivityCompat.checkSelfPermission(getApplicationContext()
                        , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
    }



    private void readContacts(){
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null
        );
        if (c != null) {
            while (c.moveToNext()){
                String name = c.getString(
                        c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = c.getString(
                        c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mContact.add(new Contact(name,phone));
            }
        }
        if (c != null) {
            c.close();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            readContacts();
        }else{
            finish();
        }
    }
}
