package circuit.com.fritze.circuitbuilder;

import android.graphics.Point;
import android.view.Display;


public class Screen{
    Display d;
    int x;
    int y;

    public Screen(Display d){
        this.d = d;
        getDimensions();
    }


    private void getDimensions(){
        int dimension[] = {0,0};
        Point size = new Point();
        d.getSize(size);
        x = size.x;
        y = size.y;
    }


    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
