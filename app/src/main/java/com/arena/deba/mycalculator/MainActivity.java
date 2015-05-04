package com.arena.deba.mycalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button num1,num2,num3,num4,num5,num6,num7,num8,num9,num0, signPlus, signMinus, signMul, signDiv, numDot, funcEq, funcDel, funcReset;

    TextView outputTextView, advFuncTextView;

    private BigDecimal input1 = new BigDecimal(0);
    private BigDecimal input2 = new BigDecimal(0);
    static BigDecimal output = new BigDecimal(0);

    private int reqCode = 1;

    private String input1Str="", input2Str="", operationStr = "", input1SignStr = "+", outputSignStr = "+";
    static String advFunc="";
    static String outputStr ="";
    String currentExp="", currentButton="";
    boolean input1Entry = false, input2Entry = false;
    boolean operationEntry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (Button)findViewById(R.id.num1);
        num2 = (Button)findViewById(R.id.num2);
        num3 = (Button)findViewById(R.id.num3);
        num4 = (Button)findViewById(R.id.num4);
        num5 = (Button)findViewById(R.id.num5);
        num6 = (Button)findViewById(R.id.num6);
        num7 = (Button)findViewById(R.id.num7);
        num8 = (Button)findViewById(R.id.num8);
        num9 = (Button)findViewById(R.id.num9);
        num0 = (Button)findViewById(R.id.num0);
        numDot = (Button)findViewById(R.id.numDot);

        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        numDot.setOnClickListener(this);

        signPlus = (Button)findViewById(R.id.signPlus);
        signMinus = (Button)findViewById(R.id.signMinus);
        signMul = (Button)findViewById(R.id.signMul);
        signDiv = (Button)findViewById(R.id.signDiv);

        signPlus.setOnClickListener(this);
        signMinus.setOnClickListener(this);
        signMul.setOnClickListener(this);
        signDiv.setOnClickListener(this);

        funcEq = (Button)findViewById(R.id.funcEq);
        funcDel = (Button)findViewById(R.id.funcDel);
        funcReset = (Button)findViewById(R.id.funcReset);
        funcDel = (Button)findViewById(R.id.funcDel);

        funcEq.setOnClickListener(this);
        funcDel.setOnClickListener(this);
        funcReset.setOnClickListener(this);

        outputTextView = (TextView) findViewById(R.id.output);

        advFuncTextView = (TextView) findViewById(R.id.advFuncText);
        advFuncTextView.setText(advFunc);
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
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.menu_adv_view:
            {
                //Toast.makeText(this, "The 1 score is: " + "call", Toast.LENGTH_LONG).show();

                if(input1Str != null && input1Str.length() > 0){
                    outputStr = input1Str;
                    outputSignStr = input1SignStr;
                    //Toast.makeText(this, "The ipS is: " + input1Str, Toast.LENGTH_LONG).show();
                    output = new BigDecimal(outputStr);
                }
                Intent intent = new Intent(MainActivity.this, AdvancedView.class);
                intent.putExtra("outputStr", outputStr);

                resetAll();

                //this.startActivity(intent);
                startActivityForResult(intent, reqCode);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == reqCode && resultCode == RESULT_OK && data != null) {
            //scorePlayer2 = data.getLongExtra("scorePlayer2", 0l);
            //themTextView.setText("THEM: " +AdvancedView);
            advFunc = data.getStringExtra("advFunc");
            //Toast.makeText(this, "The advFunc is: " + advFunc, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "The oo is: " + output, Toast.LENGTH_LONG).show();
            if(advFunc.compareTo("^") == 0)
                advFuncTextView.setText(outputStr + advFunc);
            else if (advFunc.compareTo("%") == 0){
                calculateAdvanced(false);
                handleDecimalDisplayOutput();
                setupNextCycle();
            }
            else if((advFunc.compareTo("sin") == 0) || (advFunc.compareTo("cos") == 0)|| (advFunc.compareTo("tan") == 0)|| (advFunc.compareTo("ln") == 0)
                    || (advFunc.compareTo("log") == 0) || (advFunc.compareTo("!") == 0)
                    || (advFunc.compareTo("√") == 0)) {
                advFuncTextView.setText(advFunc);
            }
            else if(advFunc.compareTo("e") == 0){
                output = new BigDecimal(Math.E).setScale(12, BigDecimal.ROUND_HALF_UP);
                handleDecimalDisplayOutput();
                setupNextCycle();
            }
            else if(advFunc.compareTo("∏") == 0){
                output = new BigDecimal(Math.PI).setScale(12, BigDecimal.ROUND_HALF_UP);
                handleDecimalDisplayOutput();
                setupNextCycle();
            }
            else if(advFunc != null && advFunc.length() > 0){
                output = new BigDecimal(advFunc);
                handleDecimalDisplayOutput();
                setupNextCycle();
            }
            //Intent intentOrg = getIntent();
            //finish();
            //startActivity(intentOrg);
        }
    }

    @Override
    public void onClick(View v) {

        currentExp = outputTextView.getText().toString();
        currentButton = ((Button) v).getText().toString();
        //Toast.makeText(this, "Button: " + currentButton, Toast.LENGTH_LONG).show();
        if(currentButton.compareTo("C") != 0) {
            currentExp += currentButton;
        }

        if(( v.getId() == num1.getId()
               || v.getId() == num2.getId()
               || v.getId() == num3.getId()
               || v.getId() == num4.getId()
               || v.getId() == num5.getId()
               || v.getId() == num6.getId()
               || v.getId() == num7.getId()
               || v.getId() == num8.getId()
               || v.getId() == num9.getId()
               || v.getId() == num0.getId()
               || v.getId() == numDot.getId())) {

            if ((!input1Entry)) {

                if(input1Str != null && input1Str.length()> 0)
                    input1Str += currentButton;
                else {
                    input1Str = currentButton;
                    currentExp = currentButton;
                    //Toast.makeText(this, "1: " + input1Str, Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(this, "1: " + input1Str, Toast.LENGTH_LONG).show();
                if(input1SignStr.compareTo("-") == 0){
                    currentExp = "-" + currentExp;
                }
                outputTextView.setText(currentExp);

            }
            else if(operationEntry){
                if(input2Str != null)
                    input2Str += currentButton;
                else
                    input2Str = currentButton;
                //Toast.makeText(this, "2: " + input2Str, Toast.LENGTH_LONG).show();
                outputTextView.setText(currentExp);
            }
        }

        else if(v.getId() == signPlus.getId()
            || v.getId() == signMinus.getId()
            || v.getId() == signMul.getId()
            || v.getId() == signDiv.getId()) {

            if (input2Str.length() > 0) {
                calculate();
                operationEntry = true;
                operationStr = currentButton;
                currentExp = outputStr + operationStr;
            } else if (input1Str != null && input1Str.length() != 0) {
                input1Entry = true;
                calculateAdvanced(true);
                if (!operationEntry)
                    operationEntry = true;
                else {
                    currentExp = outputTextView.getText().toString();
                    currentExp = currentExp.substring(0, currentExp.length() - 1) + currentButton;
                }
                operationStr = currentButton;
            } else if (input1Str == null || input1Str.length() == 0) {
                if (currentButton.compareTo("-") == 0)
                    input1SignStr = currentButton;
            }
//            else if(input1Str.length() > 0 && input2Str.length() == 0){
//                if(currentButton.compareTo("-") == 0)
//                    input2SignStr = currentButton;
//            }



            outputTextView.setText(currentExp);

        }
        else if (v.getId() == funcDel.getId()) {
            if (input2Str != null && input2Str.length() > 0) {
                if (input2Str.length() == 1) {
                    input2Str = "";
                }
                else {
                    input2Str = input2Str.substring(0, input2Str.length() - 1);
                }
                currentExp = currentExp.substring(0, currentExp.length() - 1);
            }
            else if (operationEntry) {
                operationEntry = false;
                operationStr = "";
                currentExp = currentExp.substring(0, currentExp.length() - 1);
            }
            else if (input1Str != null && input1Str.length() > 0) {
                if ((input1Str.length() == 1)) {
                    input1Str = "";
                    currentExp = "";
                    if(input1SignStr != null){
                        input1SignStr = "+";
                    }
                    //Toast.makeText(this, "The 1 score is: " + input1SignStr, Toast.LENGTH_LONG).show();
                }
                else{
                    input1Str = input1Str.substring(0, input1Str.length() - 1);
                    currentExp = currentExp.substring(0, currentExp.length() - 1);
                }
            }
            else if (advFunc != null && advFunc.length() > 0) {
                advFunc = "";
                currentExp = "";
            }
            advFuncTextView.setText(advFunc);
            outputTextView.setText(currentExp);
        }

        else if(v.getId() == funcEq.getId()){
            if(advFunc != null && advFunc.length() > 0){
                calculateAdvanced(false);
                outputTextView.setText(currentExp);
                //Toast.makeText(this, "oo: " + output, Toast.LENGTH_LONG).show();
                setupNextCycle();
            }
            else if(operationEntry != false)
                calculate();

        }
        else if(v.getId() == funcReset.getId()){
            resetAll();
        }
    }

    public void resetAll(){
        input1Str = input2Str = operationStr = "";
        input1SignStr = outputSignStr = "+";
        input1Entry = false;
        input2Entry = false;
        operationEntry = false;
        //isContinue = false;
        input1SignStr = "+";
        outputTextView.setText("");
        advFuncTextView.setText("");
    }

    public void calculate(){
        input1 =  new BigDecimal(input1Str);
        //Toast.makeText(this, "The 1 score is: " + input1, Toast.LENGTH_LONG).show();
        input2 = new BigDecimal(input2Str);
        //.makeText(this, "The 2 score is: " + input2, Toast.LENGTH_LONG).show();

        if(operationStr.compareTo("+") == 0 || operationStr.compareTo("-") == 0) {
            if(input1SignStr.compareTo(operationStr) == 0) {
                output = input1.add(input2);
                if(input1SignStr.compareTo("-") == 0){
                    outputSignStr = "-";
                }
            }
            else {
                if(input1SignStr.compareTo("-") == 0) {
                    output = input2.subtract(input1);
                }
                else{
                    output = input1.subtract(input2);
                    //Toast.makeText(this, "The 1 score is: " + output, Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(operationStr.compareTo("*") == 0) {
            output = input1.multiply(input2);
            //Toast.makeText(this, "The 1 score is: " + input1, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "The 2 score is: " + input2, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "The oo score is: " + output, Toast.LENGTH_LONG).show();
            if(input1SignStr.compareTo("-") == 0) {
                outputSignStr = "-";
            }
        }
        else if(operationStr.compareTo("/") == 0) {
            output = input1.divide(input2, 8, RoundingMode.HALF_UP);
            if(input1SignStr.compareTo("-") == 0) {
                outputSignStr = "-";
            }
        }

        //Sign handling transfer output sign to input1.
        if(output.signum() == -1){
            BigDecimal negTemp = new BigDecimal("-1");
            output = output.multiply(negTemp);
            outputSignStr = "-";
            input1SignStr = "-";
            //Toast.makeText(this, "The oo score is: " + output, Toast.LENGTH_LONG).show();
        }

        handleDecimalDisplayOutput();


        setupNextCycle();
    }

    public void handleDecimalDisplayOutput(){
        // Show/Hide decimal only if decimal part present
        BigDecimal fcTemp = output.subtract(new BigDecimal(output.toBigInteger()));
        if(fcTemp.compareTo(BigDecimal.ZERO) == 0)
            outputStr = (new BigDecimal(output.toBigInteger())).toString();
        else
            outputStr = output.toString();

        //Showing signs
        if (outputSignStr.compareTo("-") == 0) {
            outputTextView.setText(outputSignStr + outputStr);
        } else {
            //Toast.makeText(this, "The 1 score is: " + outputStr, Toast.LENGTH_LONG).show();
            outputTextView.setText(outputStr);
        }

        //Toast.makeText(this, "oo: " + outputStr, Toast.LENGTH_LONG).show();
    }

    public void setupNextCycle(){
        //output to input1 transfer & Reset Accordingly.
        input1Str = outputStr;
        input1 = output;
        input1SignStr = outputSignStr;
        input2Str = "";
        //Toast.makeText(this, "oo: " + input1, Toast.LENGTH_LONG).show();
        input1Entry = true;
        input2Entry = false;
        operationEntry = false;
    }

    public void calculateAdvanced(boolean isContinue){

        double result = 1;
        if(advFunc != null && advFunc.length() > 0){
            if(input1Str != null && input1Str.length() > 0)
                input1 = new BigDecimal(input1Str);
            double temp = input1.doubleValue();
            //Toast.makeText(this, "0: " + temp, Toast.LENGTH_LONG).show();
            if(advFunc.compareTo("sin") == 0) {
                temp = Math.sin(temp);
            }
            else if(advFunc.compareTo("cos") == 0) {
                temp = Math.cos(temp);
            }
            else if(advFunc.compareTo("tan") == 0) {
                temp = Math.tan(temp);
            }
            else if(advFunc.compareTo("ln") == 0) {
                temp = Math.log10(temp);
            }
            else if(advFunc.compareTo("log") == 0) {
                temp = Math.log(temp);
            }
            else if(advFunc.compareTo("√") == 0) {
                temp = Math.sqrt(temp);
            }
            else if(advFunc.compareTo("%") == 0) {
                temp = output.doubleValue()/100;
            }
            else if(advFunc.compareTo("!") == 0) {

                if(temp > 0){
                    while(temp > 0){
                        result = result * temp;
                        temp--;
                    }
                    temp = result;
                }
            }
            else if(advFunc.compareTo("^") == 0) {
                //Toast.makeText(this, "1: " + output.doubleValue(), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "2: " + temp, Toast.LENGTH_LONG).show();
                temp = Math.pow(output.doubleValue(), temp);
                //Toast.makeText(this, "oo: " + temp, Toast.LENGTH_LONG).show();

            }

            //temp = Math.pow(output.doubleValue(), temp);
            //Toast.makeText(this, "1: " + temp, Toast.LENGTH_LONG).show();
            input1 = BigDecimal.valueOf(temp);
            output = input1;

            signHandleDisplay();

            //Toast.makeText(this, "oo: " + output, Toast.LENGTH_LONG).show();
            outputStr = output.toString();

            advOutputDisplay(isContinue);

        }
    }

    public void signHandleDisplay(){
        //Sign handling transfer output sign to input1.
        if(output.signum() == -1){
            BigDecimal negTemp = new BigDecimal("-1");
            output = output.multiply(negTemp);
            outputSignStr = "-";
            //Toast.makeText(this, "The oo score is: " + output, Toast.LENGTH_LONG).show();
        }

        handleDecimalDisplayOutput();
    }

    public void advOutputDisplay(boolean isContinue){
        if(outputSignStr.compareTo("-") == 0){
            currentExp = outputSignStr + outputStr;
        }
        else
            currentExp = outputStr;

        if (isContinue){
            currentExp += currentButton;
        }

        //outputTextView.setText(input1Str);

        //Toast.makeText(this, "3: " + input1Str, Toast.LENGTH_LONG).show();
        advFunc = "";
        advFuncTextView.setText(advFunc);
    }
}
