package circuit.com.fritze.circuitbuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainMenu extends ActionBarActivity {

    Screen screen;
    Files file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Display d = getWindowManager().getDefaultDisplay();
        screen = new Screen(d);
        file = new Files();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void createPopup(View v){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popup_view= inflater.inflate(R.layout.create_popup_view, null, false);
        //shift up by keyboard
        final PopupWindow window = new PopupWindow(popup_view,(int) screen.getX()/2, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        Button startA = (Button) popup_view.findViewById(R.id.createActivity);
        startA.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EditText eName = (EditText) popup_view.findViewById(R.id.project_name_field);
                String name = eName.getText().toString();
//                EditText eDim = (EditText) popup_view.findViewById(R.id.dimension_field);
//                int width = Integer.parseInt(eDim.getText().toString());
                if(name.length() <= 0){
                    // TODO check if the name exists already
                    // TODO make a custom toast layout
                    Toast.makeText(getApplicationContext(), "Enter a name for the project", Toast.LENGTH_SHORT).show();
                }else{
                    if(file.fileExists(name)) { //TODO make this not require an object
                        Toast.makeText(getApplicationContext(), "That project already exists", Toast.LENGTH_SHORT);
                    }else{
                        file.createSaveDir();
                        window.dismiss();
                        //createActivity(); // TODO
                    }
                }
            }
        });

        Button close = (Button) popup_view.findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                window.dismiss();
            }
        });

        window.showAtLocation(this.findViewById(R.id.OG), Gravity.CENTER, 0,0 );
    }

    public void createActivity(View v){
        Intent createIntent = new Intent(this, Create.class);

        startActivity(createIntent);
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
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
