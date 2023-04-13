import java.util.ArrayList;

public class Sandbox {
    public static void main(String[] args) {
        tests();
        
    }

    private static void tests(){
        // init gamestate
        // build the model
        // 

        System.out.println("Gamestate Tests");
        Model mod = new Model();

        // dice test
        System.out.println("Dice test");
        
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;
        int i = 100;
        while(i-- > 0){
            int roll = mod.rollDice();
            lowest = Math.min(lowest, roll);
            highest = Math.max(highest, roll);

        }
        System.out.println("lowest and highest rolls 100 throws: " + lowest +" " + highest);

        // System.out.println("amount of spaces on the board: " + mod.getSpaces());
        
        System.out.print(mod);

        System.out.println("Test next turn mechanics");
        System.out.println(mod.getCurrentPlayer() + " is the current player ");
        mod.startNextTurn();
        System.out.println(mod.getCurrentPlayer() + " is the current player ");
        
        mod.startNextTurn();
        System.out.println(mod.getCurrentPlayer() + " is the current player ");

        // rent tests (Testing calculateRent() method and star ratings based on calculaterent):
        mod = new Model();
        System.out.println("Test rent mechanics");

        // - test 1 no one owns the hotel
        System.out.println(" Test 1: No one owns the hotel");
        Player testGuest1 = mod.getCurrentPlayer();
        Player testOwner1 = mod.getPassivePlayer();


        testGuest1.setPosition(3);

        System.out.println("rent should be 0");
        System.out.println("Rent is: " + mod.calculateRent());





        // - test 2 guest owns hotel
        System.out.println(" Test 2: guest owns the hotel");
        Player testGuest2 = mod.getCurrentPlayer();
        Player testOwner2 = mod.getPassivePlayer();

        Hotel testHotel2 = mod.getHotels().get(5);
        testGuest2.setPosition(8);
        testHotel2.setOwner(testGuest2);
        testHotel2.setStars(1);

        System.out.println("rent should be 0");
        System.out.println("Rent is: " + mod.calculateRent());


        // - test 3 opponent owns only this hotel in group
        System.out.println(" Test 3: opponent owns only this hotel in group");
        Player testGuest3 = mod.getCurrentPlayer();
        Player testOwner3 = mod.getPassivePlayer();


        Hotel testHotel3 = mod.getHotels().get(7);

        testHotel3.setOwner(testOwner3);
        testHotel3.setStars(5);
        testGuest3.setPosition(13);

        System.out.println("rent should be 375");
        System.out.println("Rent is: " + mod.calculateRent());

        // - test 4 opponent own all hotels in group
        System.out.println(" Test 4: opponent owns all hotels in group");
        Player testGuest4 = mod.getCurrentPlayer();
        Player testOwner4 = mod.getPassivePlayer();

        Hotel testHotel4 = mod.getHotels().get(12);
        Hotel testHotel5 = mod.getHotels().get(13);
        Hotel testHotel6 = mod.getHotels().get(14);

        testHotel4.setOwner(testOwner4);
        testHotel5.setOwner(testOwner4);
        testHotel6.setOwner(testOwner4);

        testHotel4.setStars(1);
        testHotel5.setStars(1);
        testHotel6.setStars(1);

        testGuest4.setPosition(24);

        System.out.println("rent should be 54");
        System.out.println("Rent is: " + mod.calculateRent());


        // - test 5 opponent and guest both own hotels in group
        System.out.println(" Test 5: opponent and guest both own hotels in group");
        Player testGuest5 = mod.getCurrentPlayer();
        Player testOwner5 = mod.getPassivePlayer();

        Hotel testHotel7 = mod.getHotels().get(15);
        Hotel testHotel8 = mod.getHotels().get(16);
        Hotel testHotel9 = mod.getHotels().get(17);

        testHotel7.setOwner(testGuest5);
        testHotel8.setOwner(testGuest5);
        testHotel9.setOwner(testOwner5);

        testHotel7.setStars(1);
        testHotel8.setStars(1);
        testHotel9.setStars(3);

        testGuest5.setPosition(29);

        System.out.println("rent should be 144");
        System.out.println("Rent is: " + mod.calculateRent());




    }
}
