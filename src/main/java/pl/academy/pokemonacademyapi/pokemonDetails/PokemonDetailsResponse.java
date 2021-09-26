package pl.academy.pokemonacademyapi.pokemonDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonDetailsResponse {
    private List<Abilities> abilities;
    private int height;
    private int weight;
    private String name;
    private Sprites sprites;
    private List<Types> types;

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }
}

class Sprites {
    @JsonProperty("front_default")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

class Types {
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

class Type {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Abilities {
    private AbilityItem ability;

    public AbilityItem getAbility() {
        return ability;
    }

    public void setAbility(AbilityItem ability) {
        this.ability = ability;
    }
}

class AbilityItem {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
