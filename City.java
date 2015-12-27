import java.util.ArrayList;

public class City {
    int x;
    int y;
    int cityNum;
    
    // Cities in the tour
    private static ArrayList tourCities = new ArrayList<City>();
	
    // Constructs a city at chosen x, y location and the city number.
    public City(int x, int y, int cityNum){
        this.x = x;
        this.y = y;
        this.cityNum = cityNum;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getCityNumber(){
        return this.cityNum;
    }
    
    // Gets the distance to given city
    public double distanceTo(City city){
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }

    public static void addCity(City city) {
        tourCities.add(city);
    }
    
    public static City getCity(int index){
        return (City)tourCities.get(index);
    }
    
    public static int numberOfCities(){
        return tourCities.size();
    }
    
    @Override
    public String toString(){
    	return String.valueOf(getCityNumber());
    }
}