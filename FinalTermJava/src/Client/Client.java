package Client;

//public class Client {
//    public static void main(String[] args) {
//        ServerCon serverCon = new ServerCon();
//        ClientView clientView = ClientView.getInstance();
//        clientView.serverCon = serverCon;
//    }
//}
//

class Animal {
    public void move() {
        System.out.println("Moving");
    }
}

class Dog extends Animal{
    @Override
    public void move() {
        System.out.println("Running");
    }

    public void move(int legs) {
        System.out.println("Running with " + legs + " legs");
    }
}


public class Client {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.move();
        dog.move(4);
    }
}
