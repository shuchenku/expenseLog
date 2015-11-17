package com.cosi153a.expenseLog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.app.ListActivity;

import com.cosi153a.expenseLog.db.ExpenseContract;
import com.cosi153a.expenseLog.db.ExpenseDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ListActivity {

    private ExpenseDBHelper helper;
    public static final int REQUEST_CODE = 1111;
    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:

                Intent intent = new Intent(MainActivity.this,NewExpenseActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

                updateUI();
                return true;

            default:
                return false;
        }
    }

    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent iData)
    {
        if ( requestCode == REQUEST_CODE )
        {
            if (resultCode == ListActivity.RESULT_OK )
            {
                String title = iData.getExtras().getString("TITLE");
                String details = iData.getExtras().getString("DETAILS");

                Log.v(TAG,title+details);
                helper = new ExpenseDBHelper(MainActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();

                values.clear();
                values.put(ExpenseContract.Columns.TITLE, title);
                values.put(ExpenseContract.Columns.DETAILS, details);
                values.put(ExpenseContract.Columns.DATE, now.format(date));
                db.insertWithOnConflict(ExpenseContract.TABLE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);

                updateUI();

            }
        }
    }

    private void updateUI() {
        helper = new ExpenseDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(ExpenseContract.TABLE,
                new String[]{ExpenseContract.Columns._ID, ExpenseContract.Columns.TITLE, ExpenseContract.Columns.DETAILS, ExpenseContract.Columns.DATE},
                null,null,null,null,null);

        ListAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.expense_detail,
                cursor,
                new String[] { ExpenseContract.Columns.TITLE, ExpenseContract.Columns.DETAILS, ExpenseContract.Columns.DATE},
                new int[] { R.id.TitleView, R.id.DetailsView, R.id.DateView},
                0
        );

        this.setListAdapter(listAdapter);

    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView ExpenseView = (TextView) v.findViewById(R.id.TitleView);
        String task = ExpenseView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                ExpenseContract.TABLE,
                ExpenseContract.Columns.TITLE,
                task);

        helper = new ExpenseDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

}

