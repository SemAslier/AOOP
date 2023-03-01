import java.util.ArrayList;
import java.util.List;

public class GameState {
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

    

    public GameState(Mode mode, int turn, ArrayList<Space> spaces, ArrayList<Player> players, ArrayList<Hotel> hotels){
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

}
