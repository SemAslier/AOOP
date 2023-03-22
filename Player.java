public class Player {
    private int position;
    private int money;

    public int getPosition(){
        return position;
    }

    public int getMoney(){
        return money;
    }

    public void setPosition(int p){
        position = p;
    }

    public void setMoney(int m){
        money = m;
    }

    public void pay(int amount){
        money = money - amount;
    }

    public void receive(int amount){
        money = money + amount;
    }

    public String toString(){
        return "Position: " + position + " Money: " + money;
    }

    
}
