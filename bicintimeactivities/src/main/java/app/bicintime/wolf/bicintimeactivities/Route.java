package app.bicintime.wolf.bicintimeactivities;

/**
 * Created by wolf on 2/4/2016.
 */

//class to help storing data coming from BicinTime API.
public class Route {

    //start object
    String stationIdStart;
    String bikesStart;
    String slotsStart;
    String latitudeStart;
    String longitudeStart;
    String streetStart;
    String streetNumberStart;

    //estimation start object
    String bikesStartEstimation;
    String slotsStartEstimation;

    //risk start object
    String departureStartRisk;
    String destinationStartRisk;

    //arrive object
    String stationIdEnd;
    String bikesEnd;
    String slotsEnd;
    String latitudeEnd;
    String longitudeEnd;
    String streetEnd;
    String streetNumberEnd;

    //estimation arrive object
    String bikesEndEstimation;
    String slotsEndEstimation;

    //risk start object
    String departurEndRisk;
    String destinationEndRisk;

    //distance object
    String depToStation;
    String bicing;
    String stationToDest;
    String sum;

    //time object
    String depToStationTime;
    String bicingTime;
    String stationToDestTime;
    String sumTime;

    //print object
    String printSumTime;

    //risk object
    String riskDeparture;
    String riskDestination;
}
