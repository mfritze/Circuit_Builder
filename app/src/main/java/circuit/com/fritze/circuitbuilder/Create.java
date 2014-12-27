package circuit.com.fritze.circuitbuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class Create extends ActionBarActivity {
    final int SIZE = 10;
    ArrayList<Element> board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout gameBoard = (LinearLayout) findViewById(R.id.gameBoard);
//        int index;
//        board = new ArrayList<>(SIZE*SIZE);
//        for(int y = 0; y < SIZE; y++){
//            LinearLayout row = new LinearLayout(this);
//            row.setOrientation(LinearLayout.HORIZONTAL);
//            for(int x = 0; x < SIZE; x++){
//                index = x + y*SIZE;
//                Button b = new Button(this);
//                b.setBackgroundResource(R.drawable.blank_button);
//                b.setId(index);
//                row.addView(b);
//            }
//            gameBoard.addView(row);
//        }
        setContentView(R.layout.activity_create);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
}
