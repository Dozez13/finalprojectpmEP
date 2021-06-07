package com.example.finalprojectpm.db.util;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

public class DistanceUtil {
    private DistanceUtil(){ }
    public static final double R = 6372.8; // In kilometers
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public static double getDistance(String clientLocation, String destinationLocation){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("5f4c8e3760644cc58af1d9492c17cdec");
        JOpenCageForwardRequest clientLocationRequest = new JOpenCageForwardRequest(clientLocation);
        JOpenCageForwardRequest destinationLocationRequest = new JOpenCageForwardRequest(destinationLocation);
        clientLocationRequest.setRestrictToCountryCode("ua"); // restrict results to a specific country
        clientLocationRequest.setBounds(22.19238, 43.61222, 40.25391, 52.42922); // restrict results to a geographic bounding box (southWestLng, southWestLat, northEastLng, northEastLat)
        destinationLocationRequest.setRestrictToCountryCode("ua"); // restrict results to a specific country
        destinationLocationRequest.setBounds(22.19238, 43.61222, 40.25391, 52.42922);
        JOpenCageResponse clientL = jOpenCageGeocoder.forward(clientLocationRequest);
        JOpenCageResponse clientD = jOpenCageGeocoder.forward(destinationLocationRequest);
        JOpenCageLatLng firstResultLatLng = clientL.getFirstPosition();
        JOpenCageLatLng secondResultLatLng = clientD.getFirstPosition();
        return haversine(firstResultLatLng.getLat(), firstResultLatLng.getLng(), secondResultLatLng.getLat(), secondResultLatLng.getLng());
    }
    public static String takenTime(double distanceKilo){
        int kmsPerHour = 60;
        double hourNotFormatted = distanceKilo / kmsPerHour;
        int minutes =(int)((hourNotFormatted-(int)hourNotFormatted)*60);
        int hour = (int)hourNotFormatted;
        return String.format("%d %d",hour,minutes);
    }
}