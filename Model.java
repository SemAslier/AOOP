import java.util.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Random randomgenerator;


    

    public Model(long seed){
        this.randomgenerator = new Random(seed);

        // Init game standard
        this.setModeNormal();
        this.turn = 0;

        // init players
        this.players = new ArrayList<Player>();
        Player alice = new Player(2000);
        Player bob = new Player(2000);
        alice.setName("alice");
        bob.setName("bob");
        this.players.add(alice);
        this.players.add(bob);

        // init hotels
        this.spaces = new ArrayList<Space>();
        this.hotels = new ArrayList<Hotel>();
        int hi = 0; // hotelindex
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
    
    // mode functions
    public Mode getMode(){
        return mode;
    }

    public String getModeName(){
        return mode.getModeName();
    }

    public void setModeNormal(){
        this.mode = Mode.NORMAL;
        notifyObservers();
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


    public ArrayList<Space> getSpaces(){
        return spaces;
    }

    public Space getSpace(int position){
        return spaces.get(position);
    }


    // player functions
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public Player getCurrentPlayer(){
        return this.players.get(this.turn % 2);
    }

    public Player getPassivePlayer(){
        return this.players.get((this.turn + 1) % 2);
    }  



    public ArrayList<Hotel> getHotels(){
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels){
        this.hotels = hotels;
    }
	
    public void setTurn(int turn){
        this.turn = turn;
        setChanged();
        notifyObservers(turn);
    }

    public void startNextTurn(){
        this.turn ++;
    }


    public int rollDice(){
        // roll the 12 sided dice
        dice = (int)(this.randomgenerator.nextDouble() * 12) + 1;
        return dice;
    }

    public void movePlayer(){
        // move the player
        Player currentPlayer = this.getCurrentPlayer();
        int dice = rollDice();
        int playerPosition = currentPlayer.getPosition();
        int newPosition = playerPosition + dice;
        if(newPosition > 39){
            newPosition = newPosition - 40;
        }
        currentPlayer.setPosition(newPosition);
        System.out.println("Player " + currentPlayer.getName() + " rolled a " + dice + " and moved to " + newPosition);
        // if(spaces.get(newPosition).getHotel() != null){
        //     calculateRent();
        // }
        

    }

    public void buyHotel(){
        // buy the hotel
        Player currentPlayer = this.getCurrentPlayer();
        int playerPosition = currentPlayer.getPosition();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int playerMoney = currentPlayer.getMoney();
        Hotel hotel = spaces.get(playerPosition).getHotel();
        if(playerMoney >= hotelValue){
            currentPlayer.setMoney(playerMoney - hotelValue);
            hotel.setOwner(currentPlayer);
            System.out.println("Player " + currentPlayer + " bought " + hotel);

        }

    }

    public void upgradeHotel(){
        // upgrade the hotel If a player lands on a hotel that they own or have just purchased a hotel, 
        // they should be given the opportunity to increase its star rating if they can afford it. 
        // A hotel has a star-rating of zero when purchased (so the overnight fee of a guest is £0). 
        // The cost of increasing the star rating by one is 50% of the purchase price. 
        // The player can increase the star rating as many times as they like on a turn but the maximum star rating is 
        // five. 


        Player currentPlayer = this.getCurrentPlayer();
        int playerPosition = currentPlayer.getPosition();
        int hotelStars = spaces.get(playerPosition).getHotel().getStars();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int playerMoney = currentPlayer.getMoney();
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
            currentPlayer.setMoney(playerMoney - (int)(0.5 * hotelValue));
            spaces.get(playerPosition).getHotel().setStars(hotelStars + 1);
            System.out.println("Hotel " + hotelName + " has been upgraded to " + hotelStars + " stars.");
        }



        
        

    }

    public int calculateRent(){
        /*
         * If the player lands on a hotel owned by the other player, an overnight fee should be deducted from the 
         * first player (guest) and given to the second player (owner). The overnight fee for a hotel is 10% of the 
         * purchase price multiplied by the square of the star-rating. However, the fee is doubled if the owner owns 
         * both of the other two hotels in the letter group eg A3 and A2 as well as A1 and is halved if the guest 
         * owns either or both of the other two hotels in the letter group
         * 
         * - Check the owner
         * - Get purchase price -> 0.1 * purchase price
         * - Get star rating -> stars^2
         * - Check if the owner own all hotels -> double the rent
         * - Check if the guest owns any hotels in the same group -> halve the rent
         */

        // calculate the rent
        Player guest = this.getCurrentPlayer();
        Player owner = this.getPassivePlayer(); 
        int playerPosition = guest.getPosition();
        Hotel hotel = spaces.get(playerPosition).getHotel();
        int hotelStars = hotel.getStars();
        int hotelValue = hotel.getValue();
        String hotelGroup = hotel.getGroup();
        int baseRent = (int) (0.1 * hotelValue * Math.pow(hotelStars, 2));



        //check the owner
        if(hotel.getOwner() == null){
            // System.out.println("owner is null");
            return 0;
        }

        if(hotel.getOwner() == guest){
            // System.out.println("owner is guest");
            return 0;

        }



        // check if the owner owns all hotels in the group
        boolean allGroupHotelsOwned = true;
        for (Hotel h : this.hotels) {
        if (h.getGroup().equals(hotelGroup) && h.getOwner() != owner) {
            allGroupHotelsOwned = false;
            break;
            }
        }


        if(allGroupHotelsOwned){
            // System.out.println("owner has all hotels in group");
            return baseRent * 2;
        }

        // check if the guest owns any hotel in the group
        boolean hasOtherGroupHotelOwned = false;
        for (Hotel h : hotels) {
        if (h.getGroup().equals(hotelGroup) && h.getOwner() == guest &&  h != hotel) {
            hasOtherGroupHotelOwned = true;
            break;
            }
        }
        if(hasOtherGroupHotelOwned){
            // System.out.println("guest own hotel in group");
            return baseRent / 2;
        }

        // System.out.println("owner owns only this hotel");
        return baseRent;
    }


    public void payRent(){
        // pay the rent
        Player guest = this.getCurrentPlayer();
        Player owner = this.getPassivePlayer();
        int playerPosition = guest.getPosition();
        int rent = calculateRent();
        int playerMoney = guest.getMoney();
        int ownerMoney = owner.getMoney();

        System.out.println("Rent: " + rent);
        System.out.println("Guest money before: " + playerMoney);

        if(rent == 0){
            return;
        }

        if(playerMoney >= rent){
            guest.setMoney(playerMoney - rent);
            owner.setMoney(ownerMoney + rent);
            System.out.println("Player " + guest + " paid rent to " + owner);
        }else{
            endGame();
        }
        
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

    public boolean isRunning(){
        return this.getModeName() == "gameover";

    }

    public void endGame(){
        // end the game and display winner'
        Player winner = this.getPassivePlayer();
        System.out.println("Game Over");
        System.out.println("The winner is " + winner.getName());
        setModeGameOver();
        //System.exit(0);
        //end program


        
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
