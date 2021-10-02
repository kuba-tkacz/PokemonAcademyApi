package pl.academy.pokemonacademyapi.pokemonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonListService {
    private final PokemonRepository pokemonRepository;
    private final PokemonListNetworkRepository pokemonListNetworkRepository;
    private final PokemonTransformer pokemonTransformer;

    @Autowired
    PokemonListService(PokemonRepository pokemonRepository,
                       PokemonListNetworkRepository pokemonListNetworkRepository,
                       PokemonTransformer pokemonTransformer) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonListNetworkRepository = pokemonListNetworkRepository;
        this.pokemonTransformer = pokemonTransformer;
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

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}