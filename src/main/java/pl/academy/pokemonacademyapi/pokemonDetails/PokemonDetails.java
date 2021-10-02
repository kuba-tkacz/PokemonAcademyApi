package pl.academy.pokemonacademyapi.pokemonDetails;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class PokemonDetails {
    @Id
    private String name;
    @Convert(converter = StringListConverter.class)
    private List<String> abilities;
    private int height;
    private int weight;
    private String imageUrl;
    @Convert(converter = StringListConverter.class)
    private List<String> types;

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}