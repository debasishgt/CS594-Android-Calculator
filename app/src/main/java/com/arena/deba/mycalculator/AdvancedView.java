package com.arena.deba.mycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.math.BigDecimal;

/**
 * Created by debasish on 4/29/2015.
 */
public class AdvancedView extends ActionBarActivity implements View.OnClickListener {

    private Button signSin, signCos, signTan, signI, signLn, signLog, signPi, signE, signPer, signFac, signSqrt, signPow, signOp, signCl, signEq2, funcReset, funcDel;
    private String func="";
    private String outputStr = "", resultStr = "", ipReceivedStr="";
    private BigDecimal result, ipReceived;

    TextView outputTextView, advFuncTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_view);

        ipReceivedStr = getIntent().getExtras().getString("outputStr");
        if(ipReceivedStr != null && ipReceivedStr.length() > 0)
            ipReceived = new BigDecimal(ipReceivedStr);
        else
            ipReceivedStr = "";
        //Toast.makeText(this, "The op is: " + output, Toast.LENGTH_LONG).show();

        result = new BigDecimal(0);

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
        funcReset = (Button)findViewById(R.id.funcReset);
        funcDel = (Button)findViewById(R.id.funcDel);

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

        funcReset.setOnClickListener(this);
        funcDel.setOnClickListener(this);

        outputTextView = (TextView) findViewById(R.id.output);

        advFuncTextView = (TextView) findViewById(R.id.advFuncText);
        //advFuncTextView.setText(advFunc);
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
                resultIntent.putExtra("advFunc", resultStr);
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
        String tempFunc = ((Button) v).getText().toString();
        //Toast.makeText(this, "The func is: " +tempFunc, Toast.LENGTH_LONG).show();
        //outputStr = outputTextView.getText().toString();
        //resultStr = outputStr + func;
        if((tempFunc.compareTo("sin") == 0) || (tempFunc.compareTo("cos") == 0) || (tempFunc.compareTo("^") == 0) || (tempFunc.compareTo("tan") == 0)
                || (tempFunc.compareTo("log") == 0) || (tempFunc.compareTo("ln") == 0) || (tempFunc.compareTo("√") == 0)){
            advFuncContinue(tempFunc);
        }
        else if((tempFunc.compareTo("e") == 0) || (tempFunc.compareTo("∏") == 0)){
            //func = tempFunc;
            calculateOutput(tempFunc);
        }

        else if(tempFunc.compareTo("AC") == 0){
            //Toast.makeText(this, "The func is: " , Toast.LENGTH_LONG).show();
            resetAll();
        }

        else if((tempFunc.compareTo("!") == 0) || (tempFunc.compareTo("%") == 0)){
            resultStr = tempFunc;
            goBack();
        }
        else if(v.getId() == funcReset.getId() || v.getId() == funcDel.getId()){
            resetAll();
        }
    }
    public void resetAll(){
        func = outputStr = resultStr = ipReceivedStr = "";
        result = ipReceived = BigDecimal.ZERO;

        outputTextView.setText("");
        advFuncTextView.setText("");
    }
    public void advFuncContinue(String input){
        resultStr = func = input;
        if (outputStr != null){
            outputStr = func + "(";
            displayPrime(outputStr);
        }
    }

    public void displayPrime(String op){
        outputTextView.setText(op);
    }


    public void goBack(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("advFunc", resultStr);
        setResult(AdvancedView.RESULT_OK, resultIntent);
        finish();
    }

    public void calculateOutput(String ipStr) {
        double input;

        if(ipStr.compareTo("e") == 0){
            input = Math.E;
        }
        else{
            input = Math.PI;
        }

        if (func != null && func.length() > 0) {

            if(func.contains("sin")){
                input = Math.sin(input);
            }

            else if(func.contains("cos")){
                input = Math.cos(input);
            }
            else if(func.contains("tan")){
                input = Math.tan(input);
            }
            else if(func.contains("log")){
                input = Math.log(input);
            }
            else if(func.contains("ln")){
                input = Math.log10(input);
            }
            else if(func.contains("√")){
                input = Math.sqrt(input);
            }
            else if(func.contains("^")){
                input = Math.pow(ipReceived.doubleValue(), input);
            }

            result = BigDecimal.valueOf(input);
            resultStr = result.toString();
            displayPrime(resultStr);
            goBack();
        }
        else{
            resultStr = ipStr;
            //Toast.makeText(this, "The func is: " +resultStr, Toast.LENGTH_LONG).show();
            goBack();
        }
    }
}
