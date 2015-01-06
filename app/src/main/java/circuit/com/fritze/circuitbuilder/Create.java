package circuit.com.fritze.circuitbuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    final int BLANK = 0, DISPLAY = 1, AND = 2, OR = 3, XOR = 4, WIRE = 5;
    final int SIZE = 10, SCREEN_DISPLACEMENT = 200;
    final int FORWARD = 1, BACKWARD = -1;
    //ArrayList<Element> board;
    Element board[][];
    Screen screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        board = new Element[SIZE][SIZE];
        for(int i = 0; i < SIZE; i ++){
            for(int j= 0; j < SIZE; j++){
                board[i][j] = new Element();
            }

        }
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


    private Bitmap rotate(int drawable, int degrees){
        //drawable is the resource to be flipped
        //degrees is the NET ROTATION of degrees to be flipped
        Bitmap originalBitmap = BitmapFactory.decodeResource(this.getResources(), drawable);
        Bitmap result = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(result);
        tempCanvas.rotate(degrees, originalBitmap.getWidth()/2, originalBitmap.getHeight()/2);
        tempCanvas.drawBitmap(originalBitmap, 0, 0, null);
        return result;
    }


    public void elementPopup(final View buttonView){
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup_view = inflater.inflate(R.layout.element_popup, null);
        final PopupWindow window = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int top,left, screenW, screenH;
        View parent = (View) buttonView.getParent();
        top = parent.getTop();
        left = buttonView.getLeft();
        screenW = screen.getX();
        screenH = screen.getY();


        if(top > screenH - SCREEN_DISPLACEMENT){
            top -= SCREEN_DISPLACEMENT;
        }

        if(left > screenW - SCREEN_DISPLACEMENT){
            left -= SCREEN_DISPLACEMENT;
        }

        final int x_index = ((ViewGroup) parent).indexOfChild(buttonView),
            y_index = ((ViewGroup) parent.getParent()).indexOfChild(parent);
        final Element element =  board[x_index][y_index];
        final int x = left, y = top, drawable = getButtonDrawable(element.getType());

        Button blankButton = (Button) popup_view.findViewById(R.id.selectBlank);
        blankButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonView.setBackground(getDrawable(R.drawable.blank_button));
                window.dismiss();
                element.setInputs(new boolean[] {false, false, false, false});
                element.setType(BLANK);
                element.setRotations(0);
            }
        });

        Button displayButton = (Button) popup_view.findViewById(R.id.selectDisplay);
        displayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonView.setBackground(getDrawable(R.drawable.display_button));
                window.dismiss();
                element.setInputs(new boolean[] {true, true, true, true});
                element.setType(DISPLAY);
                element.setRotations(0);
            }
        });


        Button rotate = (Button) popup_view.findViewById(R.id.rotateLeft);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.rotateLeft();
                int degrees = element.getRotations()*90;
                element.rotateInputs(BACKWARD);

                Bitmap rotatedBitmap = rotate(drawable, degrees);
                Drawable rotatedImage = new BitmapDrawable(getResources(), rotatedBitmap);
                buttonView.setBackground(rotatedImage);
                window.dismiss();
            }
        });

        rotate = (Button) popup_view.findViewById(R.id.rotateRight);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.rotateRight();
                int degrees = element.getRotations()*90;
                element.rotateInputs(FORWARD);

                Bitmap rotatedBitmap = rotate(drawable, degrees);
                Drawable rotatedImage = new BitmapDrawable(getResources(), rotatedBitmap);
                buttonView.setBackground(rotatedImage);
                window.dismiss();
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
                        element.setInputs(new boolean[] {true, true, true, true});
                        buttonView.setBackground(getDrawable(R.drawable.wire_3_to_1_button));
                        innerWindow.dismiss();
                        element.setType(WIRE + 3);

                    }
                });

                wire = (Button) innerPopupView.findViewById(R.id.selectWire_2_1);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        element.setInputs(new boolean[] {false, true, true, true});
                        buttonView.setBackground(getDrawable(R.drawable.wire_2_to_1_button));
                        innerWindow.dismiss();
                        element.setType(WIRE + 2);
                    }
                });


                wire = (Button) innerPopupView.findViewById(R.id.selectWire_1_11);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        element.setInputs(new boolean[] {false, true, false, true});
                        buttonView.setBackground(getDrawable(R.drawable.wire_1_11_button));
                        innerWindow.dismiss();
                        element.setType(WIRE );
                    }
                });

                wire = (Button) innerPopupView.findViewById(R.id.selectWire_1_12);
                wire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        element.setInputs(new boolean[] {false, true, true, false});
                        buttonView.setBackground(getDrawable(R.drawable.wire_1_to_12_button));
                        innerWindow.dismiss();
                        element.setType(WIRE + 1);
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
                        element.setInputs(new boolean[] {true, true, true, true});
                        buttonView.setBackground(getDrawable(R.drawable.and_button));
                        innerWindow.dismiss();
                        element.setType(AND);
                    }
                });

                gate = (Button) innerPopupView.findViewById(R.id.selectXor);
                gate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        element.setInputs(new boolean[] {true, true, true, true});
                        buttonView.setBackground(getDrawable(R.drawable.xor_button));
                        innerWindow.dismiss();
                        element.setType(XOR);
                    }
                });

                gate = (Button) innerPopupView.findViewById(R.id.selectOr);
                gate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        element.setInputs(new boolean[] {true, true, true, true});
                        buttonView.setBackground(getDrawable(R.drawable.or_button));
                        innerWindow.dismiss();
                        element.setType(OR);
                    }
                });

            }
        });

        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.showAtLocation(popup_view, Gravity.NO_GRAVITY, x, y);
    }


    private int getButtonDrawable(int code){
        switch (code) {
            case BLANK:
                return R.drawable.blank;
            case DISPLAY:
                return R.drawable.display;
            case AND:
                return R.drawable.and;
            case OR:
                return R.drawable.or;
            case XOR:
                return R.drawable.xor;
            case WIRE:
                return R.drawable.wire_1_11;
            case (WIRE + 1):
                return R.drawable.wire_1_to_12;
            case (WIRE + 2):
                return R.drawable.wire_2_to_1;

            default:
                return R.drawable.wire_3_to_1;
        }
    }

}

