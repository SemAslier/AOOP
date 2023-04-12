public class Player {
    private int position;
    private int money;
    private String name;

    public Player(int m){
        
        this.position = 0;
        this.money = m;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        this.name = n;
    }

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
        return "Name: " + name + " Position: " + position + " Money: " + money;
    }

    
}
