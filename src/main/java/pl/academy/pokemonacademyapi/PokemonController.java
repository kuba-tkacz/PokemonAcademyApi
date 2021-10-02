package pl.academy.pokemonacademyapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.academy.pokemonacademyapi.pokemonDetails.NoPokemonFoundException;
import pl.academy.pokemonacademyapi.pokemonDetails.PokemonDetails;
import pl.academy.pokemonacademyapi.pokemonDetails.PokemonDetailsService;
import pl.academy.pokemonacademyapi.pokemonList.Pokemon;
import pl.academy.pokemonacademyapi.pokemonList.PokemonListItem;
import pl.academy.pokemonacademyapi.pokemonList.PokemonListService;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    private final PokemonListService pokemonListService;
    private final PokemonDetailsService pokemonDetailsService;

    @Autowired
    PokemonController(PokemonListService pokemonListService,
                      PokemonDetailsService pokemonDetailsService) {
        this.pokemonListService = pokemonListService;
        this.pokemonDetailsService = pokemonDetailsService;
    }

    @GetMapping("/list")
    List<PokemonListItem> getPokemonItemList(@RequestParam(defaultValue = "0") int offset,
                                             @RequestParam(defaultValue = "20") int limit) {
        return pokemonListService.getPokemonListItems(offset, limit);
    }

    @GetMapping
    List<PokemonDetails> getPokemonDetails(@RequestParam String names) {
        return pokemonDetailsService.getListOfPokemonDetails(names);
    }

    @ExceptionHandler(value = NoPokemonFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoPokemonFoundException(NoPokemonFoundException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
