package pl.academy.pokemonacademyapi.pokemonList;

import org.springframework.stereotype.Component;

@Component
class PokemonTransformer {

    Pokemon toEntity(PokemonItem pokemonItem) {
        Pokemon pokemon = new Pokemon();
        String[] urlData = pokemonItem.getUrl().split("/");
        pokemon.setId(Integer.parseInt(urlData[urlData.length - 1]));
        pokemon.setName(pokemonItem.getName());
        pokemon.setUrl(pokemonItem.getUrl());
        return pokemon;
    }
}
