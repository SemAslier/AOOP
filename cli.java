import java.util.Scanner;
import java.util.InputMismatchException;

public class cli {
    public static void main(String[] args){
        cli game = new cli();
        game.run();
        

    }

    private void run(){
        Scanner input = new Scanner(System.in);
        Model game = new Model(0);
        boolean running = true;
        while(running){
            int choice = -1;
            System.out.println("Turn " + game.getTurn() + " Player " + game.getCurrentPlayer() + " is the current player ");
            

            //try{
                System.out.println("Select 0 for exit");
                System.out.println("Select 1 for gamemode");
                System.out.println("Select 2 to roll dice and play");
                System.out.println("Select 3 to see the gamestate");


                
                
                // voor hotel if can buy hotel etc.
                if(game.getModeCheat()){
                    System.out.println("Select 99 to cheat");
                }
                choice = input.nextInt();
                input.nextLine();
                int dice = -1;

                switch(choice){
                    case 0:
                        System.out.println("Exiting game");
                        running = false;
                    break;

                    case 1:
                        System.out.println("Select game mode");
                        System.out.println("Select 0 for default, select 1 for cheatmode");
                        int mode = input.nextInt();
                        input.nextLine();
                        switch(mode){
                            case 0:
                                game.setModeNormal();
                            break;

                            case 1:
                                game.setModeCheat();
                            break;

                            default:
                                System.out.println("This is not an option");
                            break;
                        }
                    break;

                    case 2:
                        System.out.println("Rolling dice");
                        dice = game.rollDice();
                    case 99:
                            
                        while (dice < 1 || dice >= 13){
                            System.out.println("Choose 1-12 to roll");
                            dice = input.nextInt();
                        } 
                        
                        
                        game.movePlayer(dice);
                        Player currentPlayer = game.getCurrentPlayer();
                        Player passivePlayer = game.getPassivePlayer();
                        int currentSpace = currentPlayer.getPosition();
                        Hotel currentHotel = game.getSpace(currentSpace).getHotel();
                        Space hasHotel = game.getSpace(currentSpace);

                        if (currentHotel == null) {
                            System.out.println("Player " + currentPlayer + " landed on " + hasHotel);
                            System.out.println("");
                            game.startNextTurn();
                        }
                        else if (currentHotel != null && currentHotel.getOwner() == null) {
                            System.out.println("Player " + currentPlayer + " landed on " + currentHotel);
                            System.out.println("Select 3 to buy Hotel");
                            System.out.println("Select 4 for next turn");
                            
                            int hotelAction = input.nextInt();
                            input.nextLine();

                            if (hotelAction == 3) {
                                game.buyHotel();
                                System.out.println("Hotel purchased!");
                                int upgradeAction;
                                do {
                                    

                                    System.out.println("Select 5 to upgrade Hotel");
                                    System.out.println("Select 4 for next turn");
                    
                                    upgradeAction = input.nextInt();
                                    if (upgradeAction == 5) {
                                        game.upgradeHotel();
                                    } else if (upgradeAction != 4) {
                                        System.out.println("Invalid choice. Please try again.");
                                    }
                                } while (upgradeAction != 4);
                                game.startNextTurn();
                            } else if (hotelAction == 4) {
                                game.startNextTurn();
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                            }
                        } else if(currentHotel != null && currentHotel.getOwner() == currentPlayer) {
                            System.out.println("Player " + currentPlayer + " landed on " + currentHotel);
                            System.out.println("Select 5 to upgrade Hotel");
                            System.out.println("Select 4 for next turn");
                            
                            int hotelAction = input.nextInt();
                            input.nextLine();

                            if (hotelAction == 5) {
                                game.upgradeHotel();
                                System.out.println("Hotel upgraded!");
                                game.startNextTurn();
                            } else if (hotelAction == 4) {
                                game.startNextTurn();
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                            }
                        } else if(currentHotel != null && currentHotel.getOwner() == passivePlayer) {
                            System.out.println("Player " + currentPlayer + " landed on " + currentHotel);
                            System.out.println("You have to pay " + game.calculateRent() + " to Player " + currentHotel.getOwner());
                            game.payRent();
                            System.out.println("Player " + currentPlayer + " now has " + currentPlayer.getMoney() + " money");
                            System.out.println("Player " + currentHotel.getOwner() + " now has " + currentHotel.getOwner().getMoney() + " money");
                            game.startNextTurn();
                        
                        }
                    break;

                    case 3:
                        System.out.println(game);
                    break;

                        

                        
                             
                    

                    default:
                        System.out.println("This is not an option");
                        choice = -1;
                    break;
                }
            // } catch(InputMismatchException e){
            //     System.out.println("Wrong input, try a number");
            //     choice = -1;
            // } 
            
            
        }
    }
}
