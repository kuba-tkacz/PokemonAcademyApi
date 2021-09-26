package pl.academy.pokemonacademyapi.pokemonList;

import java.util.List;

public class PokemonListResult {
    private String next;
    private List<PokemonItem> results;

    public List<PokemonItem> getResults() {
        return results;
    }

    public void setResults(List<PokemonItem> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
