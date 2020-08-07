package uk.ac.stir.cs.dissertation;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private String instructions;
    private String ingredients;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
