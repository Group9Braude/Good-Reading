package Fixtures.Coffeemaker;

import Coffeemaker.CoffeeMaker;
import Coffeemaker.Recipe;
import fit.ActionFixture;

public class PurchaseBeverage extends ActionFixture {

	private Recipe recipe;
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private int money;
	private CoffeeMaker cm = new CoffeeMaker();
	
	public void startCoffeeMaker() {
		makeRecipes();
	}

	public void recipeName(String name) {
		this.recipe = cm.getRecipeForName(name);
	}

	public void userMoney(String money) {
		this.money = Integer.parseInt(money);
	}

	public int purchaseBeverage() {
		return cm.makeCoffee(recipe,money);
	}

	private void makeRecipes() {
		//Coffee,50,6,1,1,0; Latte, 75,6,6,0,0; and Hot Chocolate,100,0,2,0,2
		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setPrice(50);
		r1.setAmtCoffee(6);
		r1.setAmtMilk(1);
		r1.setAmtSugar(1);
		r1.setAmtChocolate(0);
		r2 = new Recipe();
		r2.setName("Latte");
		r2.setPrice(75);
		r2.setAmtCoffee(6);
		r2.setAmtMilk(6);
		r2.setAmtSugar(0);
		r2.setAmtChocolate(0);
		r3 = new Recipe();
		r3.setName("Hot Chocolate");
		r3.setPrice(100);
		r3.setAmtCoffee(0);
		r3.setAmtMilk(2);
		r3.setAmtSugar(0);
		r3.setAmtChocolate(2);
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
	}
}
