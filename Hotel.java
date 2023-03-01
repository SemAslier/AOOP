public class Hotel {
    String name;
    int value;
    int ownerId;
    int stars;

    public Hotel(String name, int value, int ownerId, int stars){
        this.name = name;
        this.value = value;
        this.ownerId = ownerId;
        this.stars = stars;
    }

    public String getName(){
        return name;
    }

    public int getValue(){
        return value;
    }

    public int getOwnerId(){
        return ownerId;
    }

    public int getStars(){
        return stars;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setOwnerId(int ownerId){
        this.ownerId = ownerId;
    }

    public void setStars(int stars){
        this.stars = stars;
    }

    public String toString(){
        return "Hotel: " + name + " Value: " + value + " Owner: " + ownerId + " Stars: " + stars;
    }

    
}
