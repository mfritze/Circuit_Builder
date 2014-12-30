package circuit.com.fritze.circuitbuilder;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.PopupWindow;


public class Create extends ActionBarActivity {
    final int SIZE = 10, SCREEN_DISPLACEMENT = 200;
    //ArrayList<Element> board;
    int board[][];
    Screen screen;

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
        board = new int[SIZE][SIZE];
        Display d = getWindowManager().getDefaultDisplay();
        screen = new Screen(d);
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


    public void elementPopup(final View buttonView){
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup_view = inflater.inflate(R.layout.element_popup, null);
        final PopupWindow window = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int top,left, screenW,screenH;
        View parent = (View) buttonView.getParent();
        top = parent.getTop();
        left = buttonView.getLeft();
        screenW = screen.getX();
        screenH = screen.getY();

        if((top > screenH - SCREEN_DISPLACEMENT) || (left > screenW - SCREEN_DISPLACEMENT)){
            top -= SCREEN_DISPLACEMENT;
            left -= SCREEN_DISPLACEMENT;
        }

        final int x = left, y = top;

        Button blankButton = (Button) popup_view.findViewById(R.id.selectBlank);
        blankButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonView.setBackground(getDrawable(R.drawable.blank_button));
                window.dismiss();
                //TODO change game board
            }
        });

        Button displayButton = (Button) popup_view.findViewById(R.id.selectDisplay);
        displayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonView.setBackground(getDrawable(R.drawable.display_button));
                window.dismiss();
                //TODO change game board
            }
        });

        Button wireButton = (Button) popup_view.findViewById(R.id.selectWires);
        wireButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                window.dismiss();
                LayoutInflater innerInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View innerPopupView = innerInflater.inflate(R.layout.wire_popup, null);
                final PopupWindow innerWindow = new PopupWindow(innerPopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                innerWindow.setBackgroundDrawable(new BitmapDrawable());
                innerWindow.setOutsideTouchable(true);
                innerWindow.showAtLocation(innerPopupView, Gravity.NO_GRAVITY, x, y);

                Button wire = (Button) innerPopupView.findViewById(R.id.selectWire_3_1);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.wire_3_to_1_button));
                        innerWindow.dismiss();

                    }
                });

                wire = (Button) innerPopupView.findViewById(R.id.selectWire_2_1);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.wire_2_to_1_button));
                        innerWindow.dismiss();

                    }
                });


                wire = (Button) innerPopupView.findViewById(R.id.selectWire_1_11);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.wire_1_11_button));
                        innerWindow.dismiss();

                    }
                });

                wire = (Button) innerPopupView.findViewById(R.id.selectWire_1_12);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.wire_1_to_12_button));
                        innerWindow.dismiss();

                    }
                });


            }
        });

        Button gatesButton = (Button) popup_view.findViewById(R.id.selectGates);
        gatesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                window.dismiss();
                LayoutInflater innerInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View innerPopupView = innerInflater.inflate(R.layout.gate_popup, null);
                final PopupWindow innerWindow = new PopupWindow(innerPopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                innerWindow.setBackgroundDrawable(new BitmapDrawable());
                innerWindow.setOutsideTouchable(true);
                innerWindow.showAtLocation(innerPopupView, Gravity.NO_GRAVITY, x, y);

                Button gate = (Button) innerPopupView.findViewById(R.id.selectAnd);
                gate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.and_button));
                        innerWindow.dismiss();

                    }
                });

                gate = (Button) innerPopupView.findViewById(R.id.selectXor);
                gate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.xor_button));
                        innerWindow.dismiss();

                    }
                });

                gate = (Button) innerPopupView.findViewById(R.id.selectOr);
                gate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO change game board
                        buttonView.setBackground(getDrawable(R.drawable.or_button));
                        innerWindow.dismiss();

                    }
                });

            }
        });

        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.showAtLocation(popup_view, Gravity.NO_GRAVITY, x, y);
    }


}

