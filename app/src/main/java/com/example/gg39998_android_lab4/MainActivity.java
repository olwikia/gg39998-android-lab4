package com.example.gg39998_android_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //private ArrayList<String> target;
    //private ArrayAdapter adapter;
    private SimpleCursorAdapter adapter;
    MySQLite db = new MySQLite(this);
    //TODO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String[] values = new String[] {"Crucial", "SanDisc", "Kingstone", "WD", "Samsung", "Seagate", "Toshiba", "Hitachi"};
        //this.target = new ArrayList<String>();
        //this.target.addAll(Arrays.asList(values));
        this.adapter = new SimpleCursorAdapter( this, android.R.layout.simple_list_item_2, db.lista(), new String[] {"_id", "gatunek"}, new int[] {android.R.id.text1, android.R.id.text2}, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE );
        //this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.target);
        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(this.adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
               // Toast.makeText(getApplicationContext(), "test "+ pos, Toast.LENGTH_SHORT).show();
                TextView name = (TextView) view.findViewById(android.R.id.text1);
                Animal zwierz = db.pobierz(Integer.parseInt (name.getText().toString()));
                Intent intencja = new Intent(getApplicationContext(), DodajWpis.class);
                intencja.putExtra("element", zwierz);
                startActivityForResult(intencja, 2);
        } });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Jesteś pewien że chcesz usunąć numer " +id, Toast.LENGTH_LONG).show();

                showAlertDialogButtonClicked(view, String.valueOf(id));

                //db.usun((String.valueOf(id)));
                //adapter.changeCursor(db.lista());
                return true;

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void nowyWpis(MenuItem mi){
        Intent intencja = new Intent(this, DodajWpis.class);
        startActivityForResult(intencja, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == 1 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            //String nowy =  (String) extras.get("wpis");
            //target.add(nowy);
            Animal nowy = (Animal) extras.getSerializable("nowy");


            if (requestCode == 2){
                this.db.aktualizuj(nowy);
                Toast.makeText(this, "wpis zaaktualizowano ", Toast.LENGTH_LONG).show();
            }
            else if (requestCode == 1){
                this.db.dodaj(nowy);
                Toast.makeText(getApplicationContext(), "dodano wpis ", Toast.LENGTH_LONG).show();

            }

            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();
       // }
    }


    public void showAlertDialogButtonClicked(View view, final String id) {
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ostrzeżenie");
        builder.setMessage("Czy na pewno chcesz usunąć tę pozycję?");
        // add the buttons
        builder.setPositiveButton("Potwierdzam", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                db.usun((String.valueOf(id)));
                adapter.changeCursor(db.lista());
                dialog.dismiss();
            } });
        builder.setNegativeButton("Rezygnuje", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
