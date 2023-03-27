import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

public class Model extends Observable {
    public enum Mode{
        NORMAL("normal"), CHEAT("cheat"), GAMEOVER("gameover");
        private final String modeName;
        
        private Mode(String modeName){
            this.modeName = modeName;
        }
        
        public String getModeName(){
            return modeName;
        }
    }

    private int turn;
    private ArrayList<Space> spaces;
    private ArrayList<Player> players;
    private ArrayList<Hotel> hotels;
    private Mode mode;
    private int dice;
    private int rent;

    

    public Model(Mode mode, int turn, ArrayList<Space> spaces, ArrayList<Player> players, ArrayList<Hotel> hotels){
        this.mode = mode;
        this.turn = turn;
        this.spaces = spaces;
        this.players = players;
        this.hotels = hotels;
    }


    

    public Mode getMode(){
        return mode;
    }

    public String getModeName(){
        return mode.getModeName();
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public int getTurn(){
        return turn;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

    public ArrayList<Space> getSpaces(){
        return spaces;
    }

    public void setSpaces(ArrayList<Space> spaces){
        this.spaces = spaces;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public ArrayList<Hotel> getHotels(){
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels){
        this.hotels = hotels;
    }

    

    public Model() {
        // create the game
        
        

    }
    
	public void initGame(){
        // initialize the game
        
        

    }	

    public int rollDice(){
        // roll the 12 sided dice
        dice = (int)(Math.random() * 12) + 1;
        return dice;
    }

    public void buyHotel(){
        // buy the hotel
        
        

    }

    public void upgradeHotel(){
        // upgrade the hotel
        
        

    }

    public void calculateRent(){
        // calculate the rent
        rent = (int) (Hotel.getValue() * 0.1 * Hotel.getStars());
        

    }

    public void nextTurn(){
        // next turn
        
        

    }

    public void endGame(){
        // end the game
        
        

    }

    
	
    
    public String toString(){
        String out = "Mode: " + this.mode + "\n" + "Turn: " + this.turn + "\n";
        int s = this.spaces.size();
        int i = 0;
        while(i ++ < s){
            out += "space " + i + ": " + this.spaces.get(i) + "\n";
        }
        return out;

        
        // this.spaces = spaces;
        // this.players = players;
        // this.hotels = hotels;
    }
}
