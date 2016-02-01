package ca.ualberta.cs.jlooFuelTrack;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by jloo on 1/28/16.
 */
public class FuelDataListTest extends ActivityInstrumentationTestCase2{
    public FuelDataListTest(){
        super(FuelLogActivity.class);
    }


    public void testAddFuelData(){
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Test FuelData");

        FuelData.add(fuelData);
        assertTrue(FuelData.hasFuelData(fuelData));

        //IllegalArgumentException when tries to add a duplicate fuelData
        try {
            FuelData.add(fuelData);
            fail();
        } catch (IllegalArgumentException exception) {

        }
    }

    public void testHasFuelData() {
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Another Test FuelData");

        assertFalse(FuelData.hasFuelData(fuelData));
        FuelData.add(fuelData);
        assertTrue(FuelData.hasFuelData(fuelData));

        //should return true if there is a fuelData that equals() another fuelData
        FuelData.add(fuelData);
        FuelData returnedFuelData = FuelData.getFuelData(0);
        FuelData returnedFuelData2 = FuelData.getFuelData(1);
        assertEquals(returnedFuelData.getMessage(), returnedFuelData2.getMessage());
    }

    public void testGetFuelData() {
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Another Test FuelData");

        FuelData.add(fuelData);
        FuelData returnedFuelData = FuelData.getFuelData(0);

        assertEquals(returnedFuelData.getMessage(), fuelData.getMessage());
        assertEquals(returnedFuelData.getDate(), fuelData.getDate());

        //return a list of tweets in chronological order
        FuelData.add(fuelData);
        FuelData returnedFuelData2 = FuelData.getFuelData(1);

        assertTrue(returnedFuelData.getDate().before(returnedFuelData2.getDate()));
    }

    public void testDeleteFuelData() {
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Another Test FuelData");

        FuelData.add(fuelData);
        FuelData.delete(fuelData);

        assertFalse(FuelData.hasFuelData(fuelData));
    }

    public void testRemoveFuelData(){
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Test FuelData");

        FuelData.add(fuelData);
        FuelData.removeFuelData(fuelData);

        assertFalse(FuelData.hasFuelData(fuelData));
    }

    public void testGetCount(){
        FuelLogList FuelData = new FuelLogList();
        FuelData fuelData = new NormalFuelData("Another Test FuelData");

        FuelData.add(fuelData);
        FuelData.add(fuelData);

        assertEquals(FuelData.getCount(),2);
    }

}
