public class Space {
    private Hotel hotel;

    //returns 1 if it has an hotel
    public boolean hasHotel(){
        return hotel != null;
    }

    // returns null if theres no hotel
    public Hotel getHotel(){
        return hotel;
    }

    public void setHotel(Hotel hotel){
        this.hotel = hotel;
    }

    public String toString(){
        return "Hotel: " + hotel;
    }

}
