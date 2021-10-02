package pl.academy.pokemonacademyapi.pokemonDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.academy.pokemonacademyapi.pokemonList.Pokemon;
import pl.academy.pokemonacademyapi.pokemonList.PokemonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonDetailsService {
    private final PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonDetailsRepository pokemonDetailsRepository;
    private final PokemonDetailsTransformer pokemonDetailsTransformer;

    @Autowired
    public PokemonDetailsService(PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository,
                                 PokemonRepository pokemonRepository,
                                 PokemonDetailsRepository pokemonDetailsRepository,
                                 PokemonDetailsTransformer pokemonDetailsTransformer) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonDetailsRepository = pokemonDetailsRepository;
        this.pokemonDetailsNetworkRepository = pokemonDetailsNetworkRepository;
        this.pokemonDetailsTransformer = pokemonDetailsTransformer;
    }

    public List<PokemonDetails> getListOfPokemonDetails(String pokemonNames) {
        String[] names = pokemonNames.split(",");
        return Arrays.stream(names).map(name -> {
            try {
                return getPokemonDetails(name);
            } catch (NoPokemonFoundException e) {
                return PokemonDetails.EMPTY;
            }
        }).filter(pokemonDetails -> {
            return pokemonDetails != PokemonDetails.EMPTY;
        }).collect(Collectors.toList());
    }


    public PokemonDetails getPokemonDetails(String pokemonName) {
        return pokemonDetailsRepository.findById(pokemonName).orElseGet(() -> {
            Pokemon pokemon = pokemonRepository.findByName(pokemonName)
                    .orElseThrow(() -> new NoPokemonFoundException(pokemonName));
            PokemonDetailsResponse pokemonDetailsResponse = pokemonDetailsNetworkRepository.fetchPokemonDetails(pokemon.getId());
            PokemonDetails pokemonDetails = pokemonDetailsTransformer.toEntity(pokemonDetailsResponse);
            return pokemonDetailsRepository.save(pokemonDetails);
        });
    }

}
