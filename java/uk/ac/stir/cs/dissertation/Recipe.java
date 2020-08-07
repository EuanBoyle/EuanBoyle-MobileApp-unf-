package uk.ac.stir.cs.dissertation;

public class Recipe {

    private String title;
    private String ingredients;
    private String instructions;
    private String servings;
    private String duration;

    public Recipe(String title, String ingredients, String instructions, String servings, String duration){
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}