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

    private static String[] hotelNames = new String[] {
        "A1", "A2", "A3",
        "B1", "B2", "B3",
        "C1", "C2", "C3",
        "D1", "D2", "D3",
        "E1", "E2", "E3",
        "F1", "F2", "F3",
        "G1", "G2", "G3",
        "H1", "H2", "H3"
        
    };

    private static int[] hotelValues = new int[]{
        50, 50, 70, 100, 100, 120, 150, 150, 170, 200,
        200, 220, 250, 250, 270, 300, 300, 320, 350, 350,
        370, 400, 400, 420
    };

    private static String[] hotelGroup = new String[]{
        "A", "A", "A",
        "B", "B", "B",
        "C", "C", "C",
        "D", "D", "D",
        "E", "E", "E",
        "F", "F", "F",
        "G", "G", "G",
        "H", "H", "H"
    };

    private int turn;
    private ArrayList<Space> spaces;
    private ArrayList<Player> players;
    private ArrayList<Hotel> hotels;
    private Mode mode;
    private int dice;
    private int rent;

    

    public Model(){
        // Init game standard
        this.setModeNormal();
        this.turn = 0;
        this.players = players;
        // init players
        // init hotels
        this.spaces = new ArrayList<Space>();
        this.hotels = new ArrayList<Hotel>();
        int hi = 0;
        for (int i = 0; i < 40; i++){
            Space space = new Space();

            // add a hotel + value to each correct space
            if((i % 10 == 1) || (i % 10 == 3) || (i % 10 == 4) || (i % 10 == 6) || (i % 10 == 8) || (i % 10 == 9)){
                Hotel hotel = new Hotel(hotelNames[hi], hotelValues[hi], 0, hotelGroup[hi]);
                this.hotels.add(hotel);
                space.setHotel(hotel);
                hi ++;

            }
            this.spaces.add(space);
        }
    }
    

    public Mode getMode(){
        return mode;
    }

    public String getModeName(){
        return mode.getModeName();
    }

    public void setModeNormal(){
        this.mode = Mode.NORMAL;
    }

    public void setModeCheat(){
        this.mode = Mode.CHEAT;
    }

    public void setModeGameOver(){
        this.mode = Mode.GAMEOVER;
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
	

    public int rollDice(){
        // roll the 12 sided dice
        dice = (int)(Math.random() * 12) + 1;
        return dice;
    }

    public void movePlayer(){
        // move the player
        int dice = rollDice();
        int playerPosition = players.get(turn).getPosition();
        int newPosition = playerPosition + dice;
        if(newPosition > 39){
            newPosition = newPosition - 40;
        }
        players.get(turn).setPosition(newPosition);
        if(spaces.get(newPosition).getHotel() != null){
            calculateRent();
        }
        

    }

    public void buyHotel(){
        // buy the hotel

        int playerPosition = players.get(turn).getPosition();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int playerMoney = players.get(turn).getMoney();
        if(playerMoney >= hotelValue){
            players.get(turn).setMoney(playerMoney - hotelValue);
            spaces.get(playerPosition).getHotel().setOwner(players.get(turn));

        }


        
        

    }

    public void upgradeHotel(){
        // upgrade the hotel If a player lands on a hotel that they own or have just purchased a hotel, 
        // they should be given the opportunity to increase its star rating if they can afford it. 
        // A hotel has a star-rating of zero when purchased (so the overnight fee of a guest is £0). 
        // The cost of increasing the star rating by one is 50% of the purchase price. 
        // The player can increase the star rating as many times as they like on a turn but the maximum star rating is 
        // five. 

        int playerPosition = players.get(turn).getPosition();
        int hotelStars = spaces.get(playerPosition).getHotel().getStars();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int playerMoney = players.get(turn).getMoney();
        String hotelName = spaces.get(playerPosition).getHotel().getName();

        if(hotelStars >= 5){
            System.out.println("This hotel has already reached the maximum star rating.");
            return;
        }

        if(playerMoney < (0.5 * hotelValue)){
            System.out.println("You do not have enough money to upgrade this hotel.");
            return;
        }

        if(playerMoney >= (0.5 * hotelValue) && hotelStars < 5){
            players.get(turn).setMoney(playerMoney - (int)(0.5 * hotelValue));
            spaces.get(playerPosition).getHotel().setStars(hotelStars + 1);
            System.out.println("Hotel " + hotelName + " has been upgraded to " + hotelStars + " stars.");
        }



        
        

    }

    public int calculateRent(){
        // calculate the rent
        int playerPosition = players.get(turn).getPosition();
        int hotelStars = spaces.get(playerPosition).getHotel().getStars();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int baseRent = (int) (0.1 * hotelValue * Math.pow(hotelStars, 2));


        // if the owner owns the group, double the rent
        // if (Hotel.getOwner().ownsGroup(hotel)) {
        //     baseRent *= 2;
        // }


        // if the player owns a hotel from the group, half the rent
        // if (Player.ownsGroup(hotel)) {
        //     baseRent /= 2;
        // }

        return baseRent;





    }

    public void payRent(){
        // pay the rent





        
        

    }

    public void nextTurn(){
        // next turn



        
        

    }

    public void checkGameOver(){
        // check if the game is over
        if(players.get(turn).getMoney() <= 0){
            setModeGameOver();
        }

    }

    public void endGame(){
        // end the game and display winner



        
        

    }

    
	
    
    public String toString(){
        String out = "Mode: " + this.mode + "\n" + "Turn: " + this.turn + "\n";

        int s = spaces.size();
        int i = 0;
        while(++i <= s){
            out += "space " + i + ": " + this.spaces.get(i-1) + "\n";
        }
        return out;

        
        // this.spaces = spaces;
        // this.players = players;
        // this.hotels = hotels;
    }
}
