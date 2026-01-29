package sg.gov.tech.restaurant.utils;

public class GeoValidator {

    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }

    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }

    public static boolean isValidCoordinate(double latitude, double longitude) {
        return isValidLatitude(latitude) && isValidLongitude(longitude);
    }
}
