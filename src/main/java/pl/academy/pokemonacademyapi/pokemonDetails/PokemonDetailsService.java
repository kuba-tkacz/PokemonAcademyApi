package pl.academy.pokemonacademyapi.pokemonDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.academy.pokemonacademyapi.pokemonList.Pokemon;
import pl.academy.pokemonacademyapi.pokemonList.PokemonRepository;

@Service
public class PokemonDetailsService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonDetailsService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon getPokemonDetails(String pokemonName) {
        Pokemon pokemon = pokemonRepository.findByName(pokemonName).orElseThrow(()->{
            return new NoPokemonFoundException(pokemonName);
        });
        return pokemon;
    }

}
