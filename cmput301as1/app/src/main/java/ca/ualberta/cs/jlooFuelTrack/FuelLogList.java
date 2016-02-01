package ca.ualberta.cs.jlooFuelTrack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jloo on 1/28/16.
 */

//This class about how the data entries are being stored as well as accessing and deleting when needed.
public class FuelLogList {
    private ArrayList<FuelData> fuelDatas = new ArrayList<FuelData>();

    public void add(FuelData fuelData) {
        fuelDatas.add(fuelData);
    }

    public boolean hasFuelData(FuelData fuelData) {
        return fuelDatas.contains(fuelData);
    }

    public void delete(FuelData fuelData){
        fuelDatas.remove(fuelData);
    }

    public FuelData getFuelData(int index){
        return fuelDatas.get(index);
    }

    public void removeFuelData(FuelData fuelData){
        fuelDatas.remove(fuelData);
    }

    public int getCount(){
        return fuelDatas.size();
    }

    public void addFuelData(FuelData fuelData) throws IllegalArgumentException {
        if (fuelDatas.contains(fuelData)) {
            throw new IllegalArgumentException();
        }
        fuelDatas.add(fuelData);
    }

}
