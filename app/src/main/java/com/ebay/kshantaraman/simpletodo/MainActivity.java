package com.ebay.kshantaraman.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ArrayList<String> items;
    ArrayList<Item> items;
    ArrayAdapter<Item> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            lvItems=(ListView) findViewById(R.id.lvItems);

            items=new ArrayList<>();

            readItems();
            itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
            lvItems.setAdapter(itemsAdapter);
            //items.add("First Item");
            //items.add("SecondItem");
            setUpViewListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readItems(){
        MySQLiteHelper mySQLiteHelper=MySQLiteHelper.getInstance(this);
        List<Item> itemList=mySQLiteHelper.getAllItems();
        if(itemList!=null){
        items.addAll(itemList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent editActivityIntent)
    {
        if(resultCode == RESULT_OK && requestCode == 20){
            final MySQLiteHelper myhelper=MySQLiteHelper.getInstance(this);
            String editedTask = editActivityIntent.getExtras().getString("editedTask");
            int pos = editActivityIntent.getExtras().getInt("pos");
            if(pos > -1) {
                Item editedItem=items.get(pos);
                editedItem.setDescription(editedTask);
                items.set(pos, editedItem);
                myhelper.updateItem(editedItem);
                itemsAdapter.notifyDataSetChanged();
            }
            else{
                //itemsAdapter.add(editedTask);
            }

        }
    }

    public void onAddItem(View v){
        MySQLiteHelper myhelper=MySQLiteHelper.getInstance(this);
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText=etNewItem.getText().toString();
        int nextInt=items.size();
        Item item=myhelper.createItem(itemText);
        itemsAdapter.add(item);
        etNewItem.setText("");
    }

    public void setUpViewListener() {
        final MySQLiteHelper myhelper=MySQLiteHelper.getInstance(this);
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Item currentItem=items.get(pos);
                        myhelper.deleteItem(currentItem);
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {

                        //String currentTask = items.get(pos);
                        String currentTask = items.get(pos).toString();
                        Intent editActivityIntent=new Intent(MainActivity.this,EditItemActivity.class);
                        editActivityIntent.putExtra("currentTask",currentTask);
                        editActivityIntent.putExtra("pos",pos);
                        startActivityForResult(editActivityIntent, 20);
                    }
                }
        );
    }
}

