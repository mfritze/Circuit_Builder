package circuit.com.fritze.circuitbuilder;

/**
 * Created by fritze on 26/12/14.
 */
public class Element {
    private enum Type {BLANK, SOURCE, OR, XOR, AND, DISPLAY}
    private boolean input1, input2, output;
    Type type;

    public Element(){
        this.type = Type.BLANK;
        this.input1 = false;
        this.input2 = false;
        this.output = false;
    }

    public boolean getIn1(){
        return this.input1;
    }

    public boolean getIn2(){
        return this.input2;
    }

    public boolean getOutput(){
        return this.output;
    }

    public Type getType(){
        return this.type;
    }

    public void setIn1(){
        //if called, turns on. Always.
        this.input1 = true;
    }

    public void setIn2(){
        this.input2 = true;
    }

    public void setType(Type t){
        this.type = t;
    }

    public boolean setAndTestOut(){
        if(this.type == Type.OR){
            this.output = this.input1 || this.input2;
            return this.output;
        }
        if(this.type == Type.AND){
            this.output = this.input1 && this.input2;
            return this.output;
        }
        if(this.type == Type.XOR){
            this.output = this.input1 ^ this.input2;
            return this.output;
        }
        return this.output;
    }


}
