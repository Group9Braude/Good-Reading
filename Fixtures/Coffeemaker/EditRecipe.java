package Fixtures.Coffeemaker;

import Coffeemaker.CoffeeMaker;
import Coffeemaker.Recipe;
import fit.ActionFixture;

public class EditRecipe extends ActionFixture {
	Recipe oldRecipe = new Recipe();
	Recipe newRecipe = new Recipe();
	CoffeeMaker cm = new CoffeeMaker();
	
	public void startRecipe() {
		newRecipe = new Recipe();
	}

	public void newRecipeName(String name) {
		newRecipe.setName(name);
	}

	public void newRecipePrice(int price) {
		newRecipe.setPrice(price);
	}

	public void newRecipeCoffeeUnits(int coffee) {
		newRecipe.setAmtCoffee(coffee);
	}

	public void newRecipeMilkUnits(int milk) {
		newRecipe.setAmtMilk(milk);
	}

	public void newRecipeSugarUnits(int sugar) {
		newRecipe.setAmtSugar(sugar);
	}

	public void newRecipeChocolateUnits(int chocolate)  {
		newRecipe.setAmtChocolate(chocolate);
	}
	
	public void oldRecipeName(String name) {
		oldRecipe.setName(name);
	}

	public void oldRecipePrice(int price) {
		oldRecipe.setPrice(price);
	}

	public void oldRecipeCoffeeUnits(int coffee) {
		oldRecipe.setAmtCoffee(coffee);
	}

	public void oldRecipeMilkUnits(int milk) {
		oldRecipe.setAmtMilk(milk);
	}

	public void oldRecipeSugarUnits(int sugar) {
		oldRecipe.setAmtSugar(sugar);
	}

	public void oldRecipeChocolateUnits(int chocolate)  {
		oldRecipe.setAmtChocolate(chocolate);
	}
	
	public boolean addRecipe() {
		return cm.addRecipe(oldRecipe);
	}
	
	public void recipeToEdit(String recipeName) {
		oldRecipe = cm.getRecipeForName(recipeName);
	}
	
	public boolean editRecipe() {
		if(oldRecipe == null) oldRecipe = new Recipe();
		return cm.editRecipe(oldRecipe, newRecipe);
	}
}
