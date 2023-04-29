public class Hotel {
    private String name;
    private int value;
    //int ownerId;
    private Player owner;
    private int stars;
    private String group;

    public Hotel(String name, int value, int stars, String group){
        this.name = name;
        this.value = value;
        this.stars = stars;
        this.group = group;
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


    public String getGroup(){
        //get the group of the hotel
        return group;
    }

    public void setGroup(){
        //set the group of the hotel
        this.group = group;
        
        
    }

    public String toString(){
        return name + " Value: " + value + " Owner: " + owner + " Stars: " + stars + " Group: " + group;
    }

    
}
