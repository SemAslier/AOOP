
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
        System.out.println(mod.getCurrentPlayer());
        mod.startNextTurn();
        System.out.println(mod.getCurrentPlayer());
        
        mod.startNextTurn();
        System.out.println(mod.getCurrentPlayer());

        // rent tests:

        // - test 1 no one owns the hotel

        // - test 2 guest owns hotel

        // - test 3 opponent owns only this hotel in group

        // - test 4 opponent own all hotels in groupt

        // - test 5 opponent and guest both own hotels in group




    }
}
