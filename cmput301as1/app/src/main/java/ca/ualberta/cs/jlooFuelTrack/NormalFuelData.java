package ca.ualberta.cs.jlooFuelTrack;

/**
 * Created by jloo on 1/14/16.
 */

//This class is used to connect and store data entries with interface loggable.
public class NormalFuelData extends FuelData implements Loggable {
    public NormalFuelData(String message) {
        super(message);
    }

    public String getMessage() {
        return this.message;
    }

    @Override
     public boolean isImportant() {
        return true;
    }
}
