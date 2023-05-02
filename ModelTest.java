
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;




public class ModelTest {


    @Test // Test game board initialization
    public void testGameBoardInitialization() {
        Model game = new Model(0);

            String[] hotelNames = Model.getHotelNames();
            int[] hotelValues = Model.getHotelValues();
            String[] hotelGroup = Model.getHotelGroup();

            // Check if the hotels are placed correctly on the board
            assertNull(game.getSpace(0).getHotel()); // No hotel on position 0

            int[] hotelPositions = new int[]{1, 3, 4, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 28, 29, 31, 33, 34, 36, 38, 39};
            int hotelIndex = 0;

            for (int i = 1; i <= 39; i++) {
                Space space = game.getSpace(i);

                if (i == hotelPositions[hotelIndex]) {
                    // Hotel should be on this position
                    assertNotNull(space.getHotel());

                    Hotel hotel = space.getHotel();
                    assertEquals(hotelNames[hotelIndex], hotel.getName());
                    assertEquals(hotelValues[hotelIndex], hotel.getValue());
                    assertEquals(hotelGroup[hotelIndex], hotel.getGroup());

                    hotelIndex++;
                } else {
                    // No hotel should be on this position
                    assertNull(space.getHotel());
                }
            }
    }

    @Test // Test player creation mechanics
    public void testPlayerCreation() {
    Model game = new Model(0);
    List<Player> players = game.getPlayers();
    assertEquals(2, players.size());
    Player alice = players.get(0);
    assertEquals("Alice", alice.getName());
    assertEquals(2000, alice.getMoney());
    assertEquals(0, alice.getPosition());
    Player bob = players.get(1);
    assertEquals("Bob", bob.getName());
    assertEquals(2000, bob.getMoney());
    assertEquals(0, bob.getPosition());
    }

    @Test // Test dice rolling mechanics
    public void testDiceRolling() {
    Model game = new Model(0);
        for (int i = 0; i < 100; i++) {
            int dice = game.rollDice();
            assertTrue(dice >= 1 && dice <= 12);
        }
    }

    @Test // Test player movement mechanics
    public void testPlayerMovement() {
    Model game = new Model(0);
    Player player = game.getCurrentPlayer();
    game.setPlayerThrow(5);
    assertEquals(5, player.getPosition());
    game.setPlayerThrow(12);
    assertEquals(17, player.getPosition());
    }

    @Test // Test next turn mechanics
    public void testNextTurn() {
    Model game = new Model(0);
    Player alice = game.getCurrentPlayer();
    Player bob = game.getPassivePlayer();
    assertEquals(alice, game.getCurrentPlayer());
    game.startNextTurn();
    assertEquals(bob, game.getCurrentPlayer());
    game.startNextTurn();
    assertEquals(alice, game.getCurrentPlayer());
    }

    @Test // Test game loop simple, chance for buying and upgrading hotels
    public void testGameLoop() {
    Model mod = new Model(0);
    while(mod.isRunning() == false){
        System.out.println("Turn " + mod.getTurn() + " Player " + mod.getCurrentPlayer() + " is the current player ");
        int dice = mod.rollDice();
        mod.movePlayer(dice);
        Player currentPlayer = mod.getCurrentPlayer();
        int currentSpace = currentPlayer.getPosition();
        Hotel currentHotel = mod.getSpace(currentSpace).getHotel();
        Space hasHotel = mod.getSpace(currentSpace);
        
        
       
        if (currentHotel == null) {
            System.out.println("Player " + currentPlayer + " landed on " + hasHotel);
            mod.startNextTurn();
        } 

        else if (currentHotel != null && currentHotel.getOwner() == null) {
            System.out.println("Player " + currentPlayer + " landed on " + currentHotel);
            if (mod.getRandomDouble() >= 0.3){
                mod.buyHotel();
                if (mod.getRandomDouble() >= 0.7){
                    mod.upgradeHotel();
                    mod.startNextTurn();
                }else{
                    mod.startNextTurn();
                }

            } else{
                mod.startNextTurn();
            }
            
        } else if(currentHotel != null && currentHotel.getOwner() == currentPlayer) {
            if(mod.getRandomDouble() >= 0.3){
            mod.upgradeHotel();
            mod.startNextTurn();
            } else{
                mod.startNextTurn();
            }
            
            
        } else if(currentHotel != null && currentHotel.getOwner() != currentPlayer) {
            mod.payRent();
            
            mod.startNextTurn();
            
        } else{
             mod.startNextTurn();
        }    
            
        }
        
    }

    // Scenario 1: Test when a player lands on an unowned hotel and buys it
    @Test
    public void testBuyHotel() {
        Model game = new Model(0);
        game.setupBuyHotelScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Hotel testHotel = game.getHotels().get(1);

        // Check if the hotel is unowned
        assertNull(testHotel.getOwner());

        // Buy Hotel
        game.buyHotel();

        assertEquals(currentPlayer, testHotel.getOwner());
        assertEquals(0, testHotel.getStars());
        assertEquals(2000 - testHotel.getValue(), currentPlayer.getMoney());
    }

    // Scenario 2: Test when a player lands on an owned hotel and upgrades it
    @Test
    public void testUpgradeHotel() {
        Model game = new Model(0);
        game.setupUpgradeHotelScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Hotel testHotel = game.getHotels().get(1);

        // Check if the hotel is owned by the current player
        assertEquals(currentPlayer, testHotel.getOwner());

        // Upgrade Hotel
        game.upgradeHotel();

        assertEquals(1, testHotel.getStars());
        assertEquals(2000 - (int)(0.5 * testHotel.getValue()), currentPlayer.getMoney());
    }

    // Scenario 3: Test when a player lands on a hotel owned by another player and pays rent
    @Test
    public void testPayRent() {
        Model game = new Model(0);
        game.setupPayRentScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Player passivePlayer = game.getPassivePlayer();
        Hotel testHotel = game.getHotels().get(1);

        // Check if the hotel is owned by passive player
        assertEquals(passivePlayer, testHotel.getOwner());

        // Pay rent
        game.payRent();

        assertEquals(2000 - game.calculateRent(), currentPlayer.getMoney());
        assertEquals(2000 + game.calculateRent(), passivePlayer.getMoney());
    }

    // Scenario 4: Test when a player lands on a hotel owned by another player and goes bankrupt
    @Test
    public void testBankrupt() {
        Model game = new Model(0);
        game.setupBankruptScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Player passivePlayer = game.getPassivePlayer();
        Hotel testHotel = game.getHotels().get(1);

        // Check if the hotel is owned by passive player
        assertEquals(passivePlayer, testHotel.getOwner());

        // Pay rent
        game.payRent();

        assertEquals(0, currentPlayer.getMoney());
        assertEquals(2000, passivePlayer.getMoney());
    }

    // Scenario 5: Test when a player lands on a hotel owned by another player and the rent gets doubled (because the owner owns the whole group)
    @Test
    public void testDoubleRent() {
        Model game = new Model(0);
        game.setupDoubleRentScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Player passivePlayer = game.getPassivePlayer();
        Hotel testHotel = game.getHotels().get(1);
        int baseRent = (int) (0.1 * testHotel.getValue());

        // Check if the hotel is owned by passive player
        assertEquals(passivePlayer, testHotel.getOwner());

        // Pay rent
        game.payRent();

        assertEquals(game.calculateRent(), baseRent * 2);

        assertEquals(2000 - game.calculateRent(), currentPlayer.getMoney());
        assertEquals(2000 + game.calculateRent(), passivePlayer.getMoney());
    }

    // Scenario 6: Test when a player lands on their own hotel and upgrades the hotel to 5 stars
    // and then tries to upgrade it again
    @Test
    public void testUpgradeToFiveStars() {
        Model game = new Model(0);
        game.setupUpgradeToFiveStarsScenario();

        Player currentPlayer = game.getCurrentPlayer();
        Hotel testHotel = game.getHotels().get(1);

        // Check if the hotel is owned by the current player
        assertEquals(currentPlayer, testHotel.getOwner());

        // Upgrade Hotel to 1 star
        game.upgradeHotel();
        assertEquals(1, testHotel.getStars());

        // Upgrade Hotel to 2 stars
        game.upgradeHotel();
        assertEquals(2, testHotel.getStars());

        // Upgrade Hotel to 3 stars
        game.upgradeHotel();
        assertEquals(3, testHotel.getStars());

        // Upgrade Hotel to 4 stars
        game.upgradeHotel();
        assertEquals(4, testHotel.getStars());

        // Upgrade Hotel to 5 stars
        game.upgradeHotel();
        assertEquals(5, testHotel.getStars());

        // Try to upgrade the hotel to 6 stars
        game.upgradeHotel();
        assertEquals(5, testHotel.getStars());
    }

}

