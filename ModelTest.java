
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
    assertEquals("alice", alice.getName());
    assertEquals(2000, alice.getMoney());
    assertEquals(0, alice.getPosition());
    Player bob = players.get(1);
    assertEquals("bob", bob.getName());
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
    Player player = game.getPlayers().get(0);
    game.setPlayerThrow(5);
    assertEquals(5, player.getPosition());
    game.setPlayerThrow(12);
    assertEquals(17, player.getPosition());
    }

    @Test // Test pay rent mechanics
    public void testPayRent(){
        Model mod = new Model(0);

        Player testGuest6 = mod.getCurrentPlayer();
        Player testOwner6 = mod.getPassivePlayer();

        Hotel testHotel10 = mod.getHotels().get(1);

        testHotel10.setOwner(testOwner6);
        testHotel10.setStars(1);

        testGuest6.setPosition(3);
        assertEquals(mod.calculateRent(), 5);

        assertEquals(testGuest6.getMoney(), 2000);
        mod.payRent();
        assertEquals(testGuest6.getMoney(), 1995);

    }

    @Test // Test next turn mechanics
    public void testNextTurn() {
    Model game = new Model(0);
    Player alice = game.getPlayers().get(0);
    Player bob = game.getPlayers().get(1);
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
            //System.out.println("Player " + currentPlayer + " upgraded " + currentHotel);
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



    




}

