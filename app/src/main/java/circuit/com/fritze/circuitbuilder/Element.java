package circuit.com.fritze.circuitbuilder;

/**
 * Created by fritze on 26/12/14.
 */
public class Element {
    final int BLANK = 0, DISPLAY = 1, AND = 2, OR = 3, XOR = 4, WIRE_111 = 5, WIRE_112 = 6, WIRE_21 = 7, WIRE_31 = 8;
    private boolean output, input1, input2;
    private boolean inputSources[];
    int type, rotations; //Rotations will be within 0-2, and define the number of CLOCKWISE rotations undergone.

    public Element(){
        this.type = BLANK;
        this.inputSources = new boolean[4];
        this.output = false;
        this.rotations = 0;
    }


    public boolean getOutput(){
        return this.output;
    }

    public int getType(){
        return this.type;
    }

    public void setInputs(boolean inputArray[]){
        this.inputSources = inputArray;
    }

    public void rotateInputs(int direction){
        int i, bound, step, indexLow, indexHigh;
        boolean temp;

        if(direction == 1){
            i = 0;
            bound = 4;
            step = 1;
        }
        else{
            i = 3;
            bound = -1;
            step = -1;
        }
        for(;i != bound; i += step){
            indexLow = (i + step + 4) % 4;
            indexHigh = (i + 2*step + 4) % 4;
            temp = this.inputSources[indexHigh];
            this.inputSources[indexHigh] = this.inputSources[indexLow];
        }

    }

    public void setType(int t){
        this.type = t;
    }

    public void rotateRight(){
        this.rotations -=1;
    }

    public void rotateLeft(){
        this.rotations +=1;
    }

    public void setRotations(int i){
        this.rotations = i;
    }

    public int getRotations(){
        return this.rotations % 4;
    }

    public boolean setAndTestOut(){
        if(this.type == OR){
            this.output = this.input1 || this.input2;
            return this.output;
        }
        if(this.type == AND){
            this.output = this.input1 && this.input2;
            return this.output;
        }
        if(this.type == XOR){
            this.output = this.input1 ^ this.input2;
            return this.output;
        }
        return this.output;
    }


}
