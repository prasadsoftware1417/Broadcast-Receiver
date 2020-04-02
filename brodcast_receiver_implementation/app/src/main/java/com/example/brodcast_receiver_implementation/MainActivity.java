package com.example.brodcast_receiver_implementation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int CONTACT_PERMISSION_CODE =1;
    private RecyclerView recyclerView;
    private TextView textView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contact> arrayList=new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)== getPackageManager().PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, "you have already granted permission", Toast.LENGTH_SHORT).show();

        }
        else {

            requestContactPermission();
        }
        recyclerView=findViewById(R.id.recyclerView);
        textView=findViewById(R.id.emptyView);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter= new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);



        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                readFromDb();
            }
        };
    }

    private void requestContactPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE))
        {

                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This Permission is needed becuase of this and that")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
        }else {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_PHONE_STATE}, CONTACT_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CONTACT_PERMISSION_CODE)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this, "granted permission", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, " permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readFromDb()
    {
        arrayList.clear();
        DbHelper dbHelper=new DbHelper(this);
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        Cursor cursor=dbHelper.readNumber(database);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String number =cursor.getString(cursor.getColumnIndex(DbContract.INCOMING_NUMBER));
                int id =cursor.getInt(cursor.getColumnIndex("ID"));
                arrayList.add(new Contact(id, number));
            }
            cursor.close();
            dbHelper.close();
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(DbContract.UPDATE_UI_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
