import java.util.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model extends Observable {
    public enum Mode{
        NORMAL("normal"), CHEAT("cheat");
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
    private Random randomGenerator;

    // Class Invariants:
    // 1. The number of players should always be 2.
    // 2. The number of spaces on the board should always be 40.
    // 3. There should be 24 hotels in total.
    // 4. The turn should never be less than 1.

    public Model(long seed){
        this.randomGenerator = new Random(seed);
        // Init game standard
        this.setModeNormal();
        this.turn = 1;

        // init players
        this.players = new ArrayList<Player>();
        Player alice = new Player(2000);
        Player bob = new Player(2000);
        alice.setName("Alice");
        bob.setName("Bob");
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
        setChanged();
        notifyObservers();

        // Postcondition: The mode should be set to NORMAL.
        assert this.mode == Mode.NORMAL;
    }

    public void setModeCheat(){
        this.mode = Mode.CHEAT;
        setChanged();
        notifyObservers();

        // Postcondition: The mode should be set to CHEAT.
        assert this.mode == Mode.CHEAT;
    }

    public boolean getModeCheat(){
        return this.mode == Mode.CHEAT;
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

    public static String[] getHotelNames(){
        return hotelNames;
    }

    public static int[] getHotelValues(){
        return hotelValues;
    }

    public static String[] getHotelGroup(){
        return hotelGroup;
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

    public void setPlayerThrow(int position){
        this.players.get(this.turn % 2).setThrow(position);
    }

    public ArrayList<Hotel> getHotels(){
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels){
        this.hotels = hotels;
    }
	
    public void setTurn(int turn){
        this.turn = turn;
    }

    public void startNextTurn(){
        this.turn ++;
        setChanged();
        notifyObservers();

        // Postcondition: The turn should be incremented by 1.
        assert this.turn > 0;
    }

    public int rollDice(){
        // roll the 12 sided dice
        int dice = (int)(this.randomGenerator.nextDouble() * 12) + 1;
        setChanged();
        notifyObservers();
        return dice;
    }

    public double getRandomDouble(){
        return this.randomGenerator.nextDouble();
    }

    public void movePlayer(int dice){
        // move the player
        // Precondition: dice should be between 1 and 12.
        assert dice >= 1 && dice <= 12;
        Player currentPlayer = this.getCurrentPlayer();
        int playerPosition = currentPlayer.getPosition();
        int newPosition = playerPosition + dice;
        if(newPosition > 39){
            newPosition = newPosition - 40;
        }
        currentPlayer.setPosition(newPosition); 
        setChanged();
        notifyObservers();

        // Postcondition: The current player's position should be updated.
        assert currentPlayer.getPosition() == newPosition;
    }

    public void buyHotel(){
        // buy the hotel
        Player currentPlayer = this.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        int playerPosition = currentPlayer.getPosition();
        int hotelValue = spaces.get(playerPosition).getHotel().getValue();
        int playerMoney = currentPlayer.getMoney();
        Hotel hotel = spaces.get(playerPosition).getHotel();
        if(playerMoney >= hotelValue){
            currentPlayer.setMoney(playerMoney - hotelValue);
            hotel.setOwner(currentPlayer);
            System.out.println(currentPlayerName + " bought " + hotel);
            setChanged();
            notifyObservers();
        }

        // Postcondition: The hotel's owner should be the current player if they had enough money.
    }

    public void upgradeHotel(){
        // upgrade the hotel
        // Precondition: The current player must have enough money
        // Precondition: The hotel can't have 5 stars already
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
            int newStars = hotelStars + 1;
            
            System.out.println("Hotel " + hotelName + " has been upgraded to " + newStars + " stars.");
            System.out.println("Player " + currentPlayer.getName() + " now has " + currentPlayer.getMoney() + " pounds.");
            setChanged();
            notifyObservers();
        }
    }

    public int calculateRent(){
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
            return 0;
        }

        if(hotel.getOwner() == guest){
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
            return baseRent / 2;
        }
        return baseRent;
    }


    public void payRent(){
        // pay the rent
        Player guest = this.getCurrentPlayer();
        Player owner = this.getPassivePlayer();
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
        // Postcondition: The players' money has been updated
    }

    public boolean isRunning(){
        return this.getModeName() == "gameover";

    }

    public void endGame(){
        // end the game and display winner'
        Player winner = this.getPassivePlayer();
        System.out.println("Game Over");
        System.out.println("The winner is " + winner.getName());
        System.exit(0);

        // Postcondition: The game should be terminated after announcing the winner.
    }
    
    public String toString(){
        String out = "Mode: " + this.mode + "\n" + "Turn: " + this.turn + "\n";

        int s = spaces.size();
        int i = 0;
        while(++i <= s){
            out += "space " + i + ": " + this.spaces.get(i-1) + "\n";
        }
        return out;

    }

    // Setup test scenarios
    /*
     * This sets the turn to 0 and the current player gets moved to postition 3
     */
    public void setupBuyHotelScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.setPosition(3);
    }
    
    /*
     * This sets the turn to 0, moves the current player to position 3 
     * and sets the current player as owner of the hotel with index 1
     */
    public void setupUpgradeHotelScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.setPosition(3);
        Hotel testHotel = getHotels().get(1);
        testHotel.setOwner(currentPlayer);
    }
    
    /*
     * This sets the turn to 0
     * sets the passive player as owner of the hotel with index 1
     * and moves the current player to position 3
     */

    public void setupPayRentScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        Player passivePlayer = getPassivePlayer();
        Hotel testHotel = getHotels().get(1);
        testHotel.setOwner(passivePlayer);
        testHotel.setStars(1);
        currentPlayer.setPosition(3);
    }

    /*
     * This sets the turn to 0
     * sets the passive player as owner of the hotel with index 1
     * sets the stars of the hotel to 1
     * and sets the current player's money to 0
     */
    public void setupBankruptScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        Player passivePlayer = getPassivePlayer();
        Hotel testHotel = getHotels().get(1);
        testHotel.setOwner(passivePlayer);
        testHotel.setStars(1);
        currentPlayer.setPosition(3);
        currentPlayer.setMoney(0);
    }

    /*
     * This sets the turn to 0
     * sets the passive player as owner of the hotels with index 0, 1 and 2
     * sets the stars of the hotels to 1
     * and moves the current player to position 3
     */
    public void setupDoubleRentScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        Player passivePlayer = getPassivePlayer();

        Hotel testHotel = getHotels().get(0);
        testHotel.setOwner(passivePlayer);
        testHotel.setStars(1);
        Hotel testHotel2 = getHotels().get(1);
        testHotel2.setOwner(passivePlayer);
        testHotel2.setStars(1);
        Hotel testHotel3 = getHotels().get(2);
        testHotel3.setOwner(passivePlayer);
        testHotel3.setStars(1);

        currentPlayer.setPosition(3);
    }

    /*
     * This sets the turn to 0
     * sets the current player as owner of the hotel with index 1
     * sets the stars of the hotels to 0
     * and moves the current player to position 3
     */
    public void setupUpgradeToFiveStarsScenario() {
        setTurn(0);
        Player currentPlayer = getCurrentPlayer();
        Hotel testHotel = getHotels().get(1);
        testHotel.setOwner(currentPlayer);
        testHotel.setStars(0);
        currentPlayer.setPosition(3);
    }
    
}
