package com.ebay.kshantaraman.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    Button saveButton;
    EditText edt;

    int itemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String currentTask = getIntent().getStringExtra("currentTask");
        itemPosition = getIntent().getIntExtra("pos",-1);
        edt = (EditText) findViewById(R.id.editText);
        edt.setText(currentTask);


        saveButton = (Button) findViewById(R.id.editSaveButton);

    }

    public void onEditSaveClick(View v){
        String editedTask=edt.getText().toString();
        Intent returnToMainIntent=new Intent(EditItemActivity.this,MainActivity.class);

        returnToMainIntent.putExtra("editedTask",editedTask);
        returnToMainIntent.putExtra("pos",itemPosition);
        returnToMainIntent.putExtra("code",200);
        setResult(RESULT_OK,returnToMainIntent);
        finish();
    }
}
