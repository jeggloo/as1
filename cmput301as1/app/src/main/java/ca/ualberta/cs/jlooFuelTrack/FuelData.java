package ca.ualberta.cs.jlooFuelTrack;

import java.util.Date;

/**
 * Created by jloo on 1/14/16.
 */

//This class is about the data being entered and stored.
public abstract class FuelData implements Comparable<FuelData>{
    protected String message;
    private Date date;

    public String getMessage() {
        return this.message;
    }

    public FuelData(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public FuelData(String message) {
        this.message = message;
        this.date = new Date(System.currentTimeMillis());
        //TODO, set the date with a call to the date object.
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    abstract public boolean isImportant();

    public int compareTo(FuelData t) {
        return getDate().compareTo(t.getDate());
    }


    @Override
    public String toString(){
        return message;
    }

}
