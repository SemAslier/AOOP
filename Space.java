public class Space {
    private Hotel hotel;

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
