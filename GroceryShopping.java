import java.util.*;
public class GroceryShopping {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String[] items = {"Pomegranate", "Mango", "Cherry", "Lettuce", "Apple", "Coconut", "Chicken", "Beef"};
		ArrayList<Food> kart = new ArrayList<>();
		System.out.println("Enter the amount of money");
		double money = input.nextDouble();
		input.nextLine();
		System.out.println("How many items do you want in the kart?");
		int numItems = input.nextInt();
		for (int i = 0; i < numItems; i++) {
			System.out.println("Enter the items in the cart: (Pomegranate, Mango, Cherry, Lettuce, Apple, Coconut, Chicken, Beef)");
			String item = input.next();
			while (!alreadyContains(items, item)) {
				System.out.println("Invalid item: Only these are available: (Pomegranate, Mango, Cherry, Lettuce, Apple, Coconut, Chicken, Beef) ");
				item = input.next();
			}
			itemChoice(kart, item, "add");
		}
		Customer Bob = new Customer(money, kart);
		boolean go = true;
		while (go) {
			System.out.println("Do you want to add, remove, or checkout? ");
			String response1 = input.next();
			while (!response1.equals("add") && !response1.equals("remove") && !response1.equals("checkout")) {
				System.out.println("Invalid input: use \"add\", \"remove\", \"checkout\"");
				response1 = input.next();
			}
			if (response1.equals("add")) {
				System.out.println("How many items do you want to add to the kart?");
				numItems = input.nextInt();
				for (int i = 0; i < numItems; i++) {
					System.out.println("Enter an item to add to the cart: (Pomegranate, Mango, Cherry, Lettuce, Apple, Coconut, Chicken, Beef)");
					String item = input.next();
					while (!alreadyContains(items, item)) {
						System.out.println("Invalid item: Only these are available: (Pomegranate, Mango, Cherry, Lettuce, Apple, Coconut, Chicken, Beef) ");
						item = input.next();
					}
					itemChoice(Bob, item, "add");
				}
			}else if (response1.equals("remove")) {
				System.out.println("How many items do you want to remove from the kart?");
				numItems = input.nextInt();
				for (int i = 0; i < numItems; i++) {
					String currentKart = Bob.getKart().toString();
					System.out.println("Enter an item to remove from the cart (CURRENT ITEMS:) " + currentKart);
					String[] currentKartArray = (currentKart.substring(1, currentKart.length() - 1)).split(", ");
					String item = input.next();
					while (!alreadyContains(currentKartArray, item)) {
						System.out.println("Invalid item: Only these are available: " + currentKart);
						item = input.next();
					}
					itemChoice(Bob, item, "remove");
				}
			}else if (response1.equals("checkout")) {
				System.out.printf("You have $%.2f", Bob.getMoney());
				System.out.println();
				System.out.printf("Your total cost is $%.2f", Bob.getTotalCost());
				System.out.println();
				if (Bob.getMoney() < Bob.getTotalCost()) {
					String currentKart = Bob.getKart().toString();
					System.out.println("You don't have enough money to buy these items. Which item do you want to remove?" + currentKart);
					String[] currentKartArray = (currentKart.substring(1, currentKart.length() - 1)).split(", ");
					String item = input.next();
					while (!alreadyContains(currentKartArray, item)) {
						System.out.println("Invalid item: Only these are available: " + currentKart);
						item = input.next();
					}
					itemChoice(Bob, item, "remove");
					while (Bob.getMoney() < Bob.getTotalCost()) {
						System.out.println("You need to remove more items. Which item do you want to remove?");
						itemChoice(Bob, item, "remove");
					}
					if (Bob.getMoney() == Bob.getTotalCost()) {
						System.out.println("You didn't pay extra so you get no change back. Have a nice day!");
					}else {
						double change = Bob.getMoney() - Bob.getTotalCost();
						System.out.printf("You have $%.2f in change", change);
						System.out.println();
						change(change);
						go = false;
					}
				}else if (Bob.getMoney() == Bob.getTotalCost()) {
					System.out.println("You didn't pay extra so you get no change back. Have a nice day!");
				}else {
					double change = Bob.getMoney() - Bob.getTotalCost();
					System.out.printf("You have $%.2f in change", change);
					System.out.println();
					change(change);
					go = false;
				}
			}
		}
		
		input.close();
	}
	public static void change(double money) {
		int twenties = (int)(money / 20);
		money %= 20;
		int fives = (int)(money / 5);
		money %= 5;
		int ones = (int)(money / 1);
		money %= 1;
		int quarters = (int)(money / 0.25);
		money %= 0.25;
		int dimes = (int)(money / 0.10);
		money %= 0.10;
		int nickels = (int)(money / 0.05);
		money %= 0.05;
		int pennies = (int)(money / 0.01);
		money %= 1;
		System.out.println("Your change is: " + twenties + " twenty dollar bills, " + fives + " five dollar bills, " + ones + " one dollar bills, "
				+ quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels, and " + pennies + " pennies.");
	}
	public static boolean alreadyContains(String[] arr, String word) {
		if (binarySearch(bubbleSort(arr), word) != -1) {
			return true;
		}
		return false;
	}
	public static int binarySearch(String[] arr, String word) {
		int low = 0;
		int high = arr.length - 1;
		int mid = (low + high)/2;
		while (low <= high) {
			mid = (low + high)/2;
			if (word.compareTo(arr[mid]) > 0) {
				low = mid + 1;
			}else if (word.compareTo(arr[mid]) < 0) {
				high = mid - 1;
			}else {
				return mid;
			}
		}
		return -1;
	}
	public static String[] bubbleSort(String[] arr) {
		String[] tempArr = Arrays.copyOf(arr, arr.length);
		for (int i = 0; i < tempArr.length; i++) {
			for (int j = 0; j < tempArr.length - 1 - i; j++) {
				if (tempArr[j].compareTo(tempArr[j + 1]) > 0) {
					String temp = tempArr[j];
					tempArr[j] = tempArr[j + 1];
					tempArr[j + 1] = temp;
				}
			}
		}
		return tempArr;
	}
	public static void itemChoice(ArrayList<Food> kart, String item, String choice) {
		if (choice.equals("add")) {
			if (item.equals("Pomegranate")) {
				kart.add(new Pomegranate());
			}else if (item.equals("Mango")) {
				kart.add(new Mango());
			}else if (item.equals("Cherry")) {
				kart.add(new Cherry());
			}else if (item.equals("Lettuce")) {
				kart.add(new Lettuce());
			}else if (item.equals("Apple")) {
				kart.add(new Apple());
			}else if (item.equals("Coconut")) {
				kart.add(new Coconut());
			}else if (item.equals("Chicken")) {
				kart.add(new Chicken());
			}else if (item.equals("Beef")) {
				kart.add(new Beef());
			}
		}else if (choice.equals("remove")) {
			if (item.equals("Pomegranate")) {
				kart.remove(new Pomegranate());
			}else if (item.equals("Mango")) {
				kart.remove(new Mango());
			}else if (item.equals("Cherry")) {
				kart.remove(new Cherry());
			}else if (item.equals("Lettuce")) {
				kart.remove(new Lettuce());
			}else if (item.equals("Apple")) {
				kart.remove(new Apple());
			}else if (item.equals("Coconut")) {
				kart.remove(new Coconut());
			}else if (item.equals("Chicken")) {
				kart.remove(new Chicken());
			}else if (item.equals("Beef")) {
				kart.remove(new Beef());
			}
		}
	}
	public static void itemChoice(Customer x, String item, String choice) {
		if (choice.equals("add")) {
			if (item.equals("Pomegranate")) {
				x.addItem(new Pomegranate());
			}else if (item.equals("Mango")) {
				x.addItem(new Mango());
			}else if (item.equals("Cherry")) {
				x.addItem(new Cherry());
			}else if (item.equals("Lettuce")) {
				x.addItem(new Lettuce());
			}else if (item.equals("Apple")) {
				x.addItem(new Apple());
			}else if (item.equals("Coconut")) {
				x.addItem(new Coconut());
			}else if (item.equals("Chicken")) {
				x.addItem(new Chicken());
			}else if (item.equals("Beef")) {
				x.addItem(new Beef());
			}
		}else if (choice.equals("remove")) {
			if (item.equals("Pomegranate")) {
				x.removeItem(new Pomegranate());
			}else if (item.equals("Mango")) {
				x.removeItem(new Mango());
			}else if (item.equals("Cherry")) {
				x.removeItem(new Cherry());
			}else if (item.equals("Lettuce")) {
				x.removeItem(new Lettuce());
			}else if (item.equals("Apple")) {
				x.removeItem(new Apple());
			}else if (item.equals("Coconut")) {
				x.removeItem(new Coconut());
			}else if (item.equals("Chicken")) {
				x.removeItem(new Chicken());
			}else if (item.equals("Beef")) {
				x.removeItem(new Beef());
			}
		}
	}
}
class Customer {
	private double money;
	private static int itemCapacity = 20;
	private ArrayList<Food> kart = new ArrayList<>();
	
	Customer() {
		this.money = 0;
	}
	Customer(double money, ArrayList<Food> kart) {
		this.money = money;
		for (Food item : kart) {
			this.kart.add(item);
		}
	}
	public double getMoney() {
		return money;
	}
	public static int getItemCapacity() {
		return itemCapacity;
	}
	public ArrayList<Food> getKart() {
		return kart;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public void setKart(ArrayList<Food> kart) {
		for (Food item : kart) {
			this.kart.add(item);
		}
	}
	public void addItem(Food item) {
		if (kart.size() < itemCapacity) {
			kart.add(item);
		}else {
			System.out.println("There isn't enough space for this item");
		}
	}
	public void removeItem(Food item) {
		if (kart.size() > 0) {
			kart.remove(item);
		}else {
			System.out.println("There isn't anything in the cart");
		}
	}
	public double getTotalCost() {
		double sum = 0;
		for (Food item : kart) {
			sum += item.getCost();
		}
		return sum;
	}
}
interface Edible {
	public abstract void howToEat();
}
abstract class Food implements Edible{
	Food(){
	}
	
	public abstract double getCost();
	public abstract boolean equals(Object obj);
}
class Pomegranate extends Food{
	private final double cost = 8.99;
	Pomegranate(){
	}
	public void howToEat() {
		System.out.println("Cut into quarters and dissect the seeds");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Pomegranate) {
			Pomegranate x = (Pomegranate)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Pomegranate";
	}
}
class Mango extends Food{
	private final double cost = 6.00;
	Mango(){
	}
	public void howToEat() {
		System.out.println("Cut into slices");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Mango) {
			Mango x = (Mango)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Mango";
	}
}
class Cherry extends Food{
	private final double cost = 3.00;
	Cherry(){
	}
	public void howToEat() {
		System.out.println("Chew and spit out seed");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Cherry) {
			Cherry x = (Cherry)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Cherry";
	}
}
class Lettuce extends Food{
	private final double cost = 6.99;
	Lettuce(){
	}
	public void howToEat() {
		System.out.println("Chop up");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Lettuce) {
			Lettuce x = (Lettuce)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Lettuce";
	}
}
class Apple extends Food{
	private final double cost = 5.00;
	Apple(){
	}
	public void howToEat() {
		System.out.println("Eat whole");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Apple) {
			Apple x = (Apple)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Apple";
	}
}
class Coconut extends Food{
	private final double cost = 10.00;
	Coconut(){
	}
	public void howToEat() {
		System.out.println("Break into halves and scoop out flesh");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Coconut) {
			Coconut x = (Coconut)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Coconut";
	}
}
class Chicken extends Food{
	private final double cost = 5.99;
	Chicken(){
	}
	public void howToEat() {
		System.out.println("Grill or bake");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Chicken) {
			Chicken x = (Chicken)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Chicken";
	}
}
class Beef extends Food{
	private final double cost = 5.99;
	Beef(){
	}
	public void howToEat() {
		System.out.println("Grill");
	}
	public double getCost() {
		return cost;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Beef) {
			Beef x = (Beef)obj;
			return this.cost == x.cost;
		}
		return false;
	}
	public String toString() {
		return "Beef";
	}
}