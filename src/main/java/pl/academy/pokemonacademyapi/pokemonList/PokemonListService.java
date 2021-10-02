package pl.academy.pokemonacademyapi.pokemonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.academy.pokemonacademyapi.pokemonDetails.PokemonDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonListService {
    private final PokemonDetailsService pokemonDetailsService;
    private final PokemonRepository pokemonRepository;
    private final PokemonListNetworkRepository pokemonListNetworkRepository;
    private final PokemonListItemTransformer pokemonListItemTransformer;
    private final PokemonTransformer pokemonTransformer;

    @Autowired
    PokemonListService(PokemonDetailsService pokemonDetailsService,
                       PokemonRepository pokemonRepository,
                       PokemonListNetworkRepository pokemonListNetworkRepository,
                       PokemonListItemTransformer pokemonListItemTransformer,
                       PokemonTransformer pokemonTransformer) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonListNetworkRepository = pokemonListNetworkRepository;
        this.pokemonTransformer = pokemonTransformer;
        this.pokemonListItemTransformer = pokemonListItemTransformer;
        this.pokemonDetailsService = pokemonDetailsService;
    }

    public List<Pokemon> getPokemonList() {
        if (pokemonRepository.count() != 0) {
            return pokemonRepository.findAll();
        }
        final List<Pokemon> pokemons = new ArrayList<>();
        int offset = 0;
        int limit = 100;
        PokemonListResult pokemonListResult;
        do {
            pokemonListResult = pokemonListNetworkRepository.fetchPokemonList(offset, limit);
            pokemons.addAll(pokemonListResult.getResults().stream()
                    .map(pokemonTransformer::toEntity)
                    .collect(Collectors.toList())
            );
            offset += limit;
        } while (pokemonListResult.getNext() != null);
        pokemonRepository.saveAll(pokemons);
        return pokemons;
    }

    public List<PokemonListItem> getPokemonListItems(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Pokemon> pokemons = pokemonRepository.findAll(pageable).getContent();
        return pokemons.stream()
                .map(pokemon ->
                        pokemonDetailsService.getPokemonDetails(pokemon.getName()))
                .map(pokemonDetails -> {
                    return pokemonListItemTransformer.toEntity(pokemonDetails);
                })
                .collect(Collectors.toList());
    }
}