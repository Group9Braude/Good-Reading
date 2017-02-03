package Fixtures.Coffeemaker;

import Coffeemaker.CoffeeMaker;
import Coffeemaker.Recipe;
import fit.ActionFixture;

public class DeleteRecipe extends ActionFixture {
	Recipe recipe = new Recipe();
	CoffeeMaker cm = new CoffeeMaker();
	
	public void startRecipe() {
		recipe = new Recipe();
	}

	public void recipeName(String name) {
		recipe.setName(name);
	}

	public void recipePrice(int price) {
		recipe.setPrice(price);
	}

	public void recipeCoffeeUnits(int coffee) {
		recipe.setAmtCoffee(coffee);
	}

	public void recipeMilkUnits(int milk) {
		recipe.setAmtMilk(milk);
	}

	public void recipeSugarUnits(int sugar) {
		recipe.setAmtSugar(sugar);
	}

	public void recipeChocolateUnits(int chocolate)  {
		recipe.setAmtChocolate(chocolate);
	}
	
	public boolean addRecipe() {
		return cm.addRecipe(recipe);
	}
	
	public void recipeToDelete(String name) {
		recipe = cm.getRecipeForName(name);
	}
	
	public boolean deleteRecipe() {
		return cm.deleteRecipe(recipe);
	}
}
