package pl.academy.pokemonacademyapi.pokemonList;

import org.springframework.stereotype.Component;
import pl.academy.pokemonacademyapi.pokemonDetails.PokemonDetails;

@Component
class PokemonListItemTransformer {

    PokemonListItem toEntity(PokemonDetails pokemonDetails) {
        PokemonListItem pokemonListItem = new PokemonListItem();
        pokemonListItem.setName(pokemonDetails.getName());
        pokemonListItem.setImageUrl(pokemonDetails.getImageUrl());
        return pokemonListItem;
    }
}
