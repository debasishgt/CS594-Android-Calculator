package com.arena.deba.mycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by debasish on 4/29/2015.
 */
public class AdvancedView extends ActionBarActivity implements View.OnClickListener {

    private Button signSin, signCos, signTan, signI, signLn, signLog, signPi, signE, signPer, signFac, signSqrt, signPow, signOp, signCl, signEq2;
    private String func="";
    String output = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_view);

        output = getIntent().getExtras().getString("outputStr");
        //Toast.makeText(this, "The op is: " + output, Toast.LENGTH_LONG).show();

        signSin = (Button)findViewById(R.id.signSin);
        signCos = (Button)findViewById(R.id.signCos);
        signTan = (Button)findViewById(R.id.signTan);
        signI = (Button)findViewById(R.id.signI);
        signLn = (Button)findViewById(R.id.signLn);
        signLog = (Button)findViewById(R.id.signLog);
        signPi = (Button)findViewById(R.id.signPi);
        signE = (Button)findViewById(R.id.signE);
        signPer = (Button)findViewById(R.id.signPer);
        signFac = (Button)findViewById(R.id.signFac);
        signSqrt = (Button)findViewById(R.id.signSqrt);
        signPow = (Button)findViewById(R.id.signPow);
        signOp = (Button)findViewById(R.id.signOp);
        signCl = (Button)findViewById(R.id.signCl);
        signEq2 = (Button)findViewById(R.id.signEq2);

        signSin.setOnClickListener(this);
        signCos.setOnClickListener(this);
        signTan.setOnClickListener(this);
        signI.setOnClickListener(this);
        signLn.setOnClickListener(this);
        signLog.setOnClickListener(this);
        signPi.setOnClickListener(this);
        signE.setOnClickListener(this);
        signPer.setOnClickListener(this);
        signFac.setOnClickListener(this);
        signSqrt.setOnClickListener(this);
        signPow.setOnClickListener(this);
        signOp.setOnClickListener(this);
        signCl.setOnClickListener(this);
        signEq2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adv_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.menu_basic_view:
            {
                //Toast.makeText(this, "The 1 score is: " + "call", Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("advFunc", func);
                setResult(AdvancedView.RESULT_OK, resultIntent);
                finish();
                //this.startActivity(intent);
                //startActivity(intent, reqCode);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        func = (String) ((Button) v).getText().toString();
        //Toast.makeText(this, "The func is: " , Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("advFunc", func);
        setResult(AdvancedView.RESULT_OK, resultIntent);
        finish();
    }
}
