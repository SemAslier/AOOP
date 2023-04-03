
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
    }
}
