package com.cosi153a.expenseLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewExpenseActivity extends Activity {

    private String title;
    private String details;
    private final static String TAG = "NewExpense";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);
        final EditText titleET = (EditText) findViewById(R.id.Title);
        final EditText detailsET = (EditText) findViewById(R.id.Details);

        final Button submitBtn = (Button) findViewById(R.id.btnSubmit);
        final Button cancelBtn = (Button) findViewById(R.id.btnCancel);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title  = titleET.getText().toString();
                details  = detailsET.getText().toString();
                setExpense();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }


    private void setExpense()
    {
        Intent data = new Intent();
        Bundle expense = new Bundle();

        expense.putString("TITLE", title);
        expense.putString("DETAILS", details);
        data.putExtras(expense);

        setResult(Activity.RESULT_OK,
                data);
        finish();
    }
}
