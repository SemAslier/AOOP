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

        // rent tests:
        mod = new Model();

        // - test 1 no one owns the hotel
        Player testGuest1 = mod.getCurrentPlayer();
        Player testOwner1 = mod.getPassivePlayer();


        Hotel testHotel = mod.getHotels().get(3);
        testGuest1.setPosition(6);
        System.out.println(testGuest1);
        testHotel.setOwner(testOwner1);
        testHotel.setStars(1);
        System.out.println(testHotel);

        System.out.println(mod.calculateRent());





        // - test 2 guest owns hotel


        // - test 3 opponent owns only this hotel in group

        testHotel.setOwner(testOwner1);

        // - test 4 opponent own all hotels in group

        // - test 5 opponent and guest both own hotels in group




    }
}
