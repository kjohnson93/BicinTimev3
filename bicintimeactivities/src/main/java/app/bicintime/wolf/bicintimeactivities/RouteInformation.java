package app.bicintime.wolf.bicintimeactivities;

import java.util.ArrayList;

/**
 * Created by wolf on 3/29/2016.
 */
public class RouteInformation {


    private ArrayList<Waypoints> waypointsArrayList = new ArrayList<>();
    private ArrayList<String> sumTimeArrayList = new ArrayList<>();
    private ArrayList<Integer> distanceArrayList = new ArrayList<>();
    private ArrayList<String> streetUpArrayList = new ArrayList<>();
    private ArrayList<String> streetDownArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberUpArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberDownArrayList = new ArrayList<>();
    private ArrayList<String> stationIdUpArrayList = new ArrayList<>();
    private ArrayList<String> stationIdDownArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownEstArrayList = new ArrayList<>();
    private ArrayList<Double> stationRiskArrayList = new ArrayList<>();


    public RouteInformation(ArrayList<Waypoints> waypointsArrayList, ArrayList<String> sumTimeArrayList, ArrayList<Integer> distanceArrayList,
                            ArrayList<String> streetUpArrayList, ArrayList<String> streetDownArrayList, ArrayList<String> streetNumberUpArrayList,
                            ArrayList<String> streetNumberDownArrayList, ArrayList<String> stationIdUpArrayList, ArrayList<String> stationIdDownArrayList,
                            ArrayList<Integer> stationBikesUpNowArrayList, ArrayList<Integer> stationBikesDownNowArrayList, ArrayList<Integer> stationBikesUpEstArrayList,
                            ArrayList<Integer> stationBikesDownEstArrayList, ArrayList<Integer> stationSlotsUpNowArrayList, ArrayList<Integer> stationSlotsDownNowArrayList,
                            ArrayList<Integer> stationSlotsUpEstArrayList, ArrayList<Integer> stationSlotsDownEstArrayList, ArrayList<Double> stationRiskArrayList) {


        this.waypointsArrayList = waypointsArrayList;
        this.sumTimeArrayList = sumTimeArrayList;
        this.distanceArrayList = distanceArrayList;
        this.streetUpArrayList = streetUpArrayList;
        this.streetDownArrayList = streetDownArrayList;
        this.streetNumberUpArrayList = streetNumberUpArrayList;
        this.streetNumberDownArrayList = streetNumberDownArrayList;
        this.stationIdUpArrayList = stationIdUpArrayList;
        this.stationIdDownArrayList = stationIdDownArrayList;
        this.stationBikesUpNowArrayList = stationBikesUpNowArrayList;
        this.stationBikesDownNowArrayList = stationBikesDownNowArrayList;
        this.stationBikesUpEstArrayList = stationBikesUpEstArrayList;
        this.stationBikesDownEstArrayList = stationBikesDownEstArrayList;
        this.stationSlotsUpNowArrayList = stationSlotsUpNowArrayList;
        this.stationSlotsDownNowArrayList = stationSlotsDownNowArrayList;
        this.stationSlotsUpEstArrayList = stationSlotsUpEstArrayList;
        this.stationSlotsDownEstArrayList = stationSlotsDownEstArrayList;
        this.stationRiskArrayList = stationRiskArrayList;
    }

    public ArrayList<Waypoints> getWaypointsArrayList() {
        return waypointsArrayList;
    }

    public ArrayList<String> getSumTimeArrayList() {
        return sumTimeArrayList;
    }

    public ArrayList<Integer> getDistanceArrayList() {
        return distanceArrayList;
    }

    public ArrayList<String> getStreetUpArrayList() {
        return streetUpArrayList;
    }

    public ArrayList<String> getStreetDownArrayList() {
        return streetDownArrayList;
    }

    public ArrayList<String> getStreetNumberUpArrayList() {
        return streetNumberUpArrayList;
    }

    public ArrayList<String> getStreetNumberDownArrayList() {
        return streetNumberDownArrayList;
    }

    public ArrayList<String> getStationIdUpArrayList() {
        return stationIdUpArrayList;
    }

    public ArrayList<String> getStationIdDownArrayList() {
        return stationIdDownArrayList;
    }

    public ArrayList<Integer> getStationBikesUpNowArrayList() {
        return stationBikesUpNowArrayList;
    }

    public ArrayList<Integer> getStationBikesDownNowArrayList() {
        return stationBikesDownNowArrayList;
    }

    public ArrayList<Integer> getStationBikesUpEstArrayList() {
        return stationBikesUpEstArrayList;
    }

    public ArrayList<Integer> getStationBikesDownEstArrayList() {
        return stationBikesDownEstArrayList;
    }

    public ArrayList<Integer> getStationSlotsUpNowArrayList() {
        return stationSlotsUpNowArrayList;
    }

    public ArrayList<Integer> getStationSlotsDownNowArrayList() {
        return stationSlotsDownNowArrayList;
    }

    public ArrayList<Integer> getStationSlotsUpEstArrayList() {
        return stationSlotsUpEstArrayList;
    }

    public ArrayList<Integer> getStationSlotsDownEstArrayList() {
        return stationSlotsDownEstArrayList;
    }

    public ArrayList<Double> getStationRiskArrayList() {
        return stationRiskArrayList;
    }
}
