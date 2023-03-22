public class Hotel {
    private String name;
    private int value;
    //int ownerId;
    private Player owner;
    private int stars;

    public Hotel(String name, int value, Player owner, int stars){
        this.name = name;
        this.value = value;
        this.owner = owner;
        this.stars = stars;
    }

    public String getName(){
        return name;
    }

    public int getValue(){
        return value;
    }

    public Player getOwner(){
        return owner;
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

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public void setStars(int stars){
        this.stars = stars;
    }

    public void increaseStars(){
        stars++;
    }

    public void setGroup(){
        //set the group of the hotel
        
    }

    public String toString(){
        return "Hotel: " + name + " Value: " + value + " Owner: " + owner + " Stars: " + stars;
    }

    
}
