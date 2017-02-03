package Fixtures.Coffeemaker;

import Coffeemaker.CoffeeMaker;
import Coffeemaker.Inventory;
import fit.ActionFixture;

public class CheckInventory extends ActionFixture {
	private CoffeeMaker cm = new CoffeeMaker();
	private Inventory i = cm.checkInventory();
	
	public int coffeeInventory() {
		return i.getCoffee();
	}

	public int milkInventory() {
		return i.getMilk();
	}

	public int sugarInventory() {
		return i.getSugar();
	}

	public int chocolateInventory() {
		return i.getChocolate();
	}
}
