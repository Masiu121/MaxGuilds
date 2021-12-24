package com.Oxology.MaxGuilds.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

    public List<Location> decodeString(String value) {
        List<Location> locations = new ArrayList<>();
        String[] encodedLocations = value.split(" ");
        for(String encodedLocation : encodedLocations) {
            String[] locationDetails = encodedLocation.split(",");

            locations.add(
                    new Location(
                            Bukkit.getWorld(locationDetails[0]),
                            Integer.parseInt(locationDetails[1]),
                            Integer.parseInt(locationDetails[2]),
                            Integer.parseInt(locationDetails[3])
                    )
            );

        }
        return locations;
    }

    public String encodeString(List<Location> value) {
        String encodedLocations = "";
        for(Location decodedLocation : value) {
            String encodedLocationDetails = decodedLocation.getWorld().getName() + "," +
                    decodedLocation.getBlockX() + "," +
                    decodedLocation.getBlockY() + "," +
                    decodedLocation.getBlockZ();

            encodedLocations = encodedLocations + encodedLocationDetails + " ";
        }
        return encodedLocations;
    }
}
