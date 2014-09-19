package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends Activity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SubmitAnswerActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        Parse.initialize(this,"PYP","CK");

        ParseObject testObject = new ParseObject("testObject");
        testObject.put("Item1",1991);
        testObject.put("Item2","A String");
        testObject.saveEventually();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("testObject");
        query.getInBackground("What is this for",new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject testObject, ParseException e) {
                if(e == null){
                    testObject.put("Item1",1990);
                    testObject.put("Item2",1000);
                    testObject.saveInBackground();
                }
            }
        });
        try {
            System.out.println(query.get("Item1"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(query.get("Item2"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
