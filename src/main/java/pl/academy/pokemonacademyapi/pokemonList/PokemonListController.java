package pl.academy.pokemonacademyapi.pokemonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
class PokemonListController {

    private final PokemonListService pokemonListService;

    @Autowired
    PokemonListController(PokemonListService pokemonListService) {
        this.pokemonListService = pokemonListService;
    }

    @GetMapping("/list")
    List<Pokemon> getPokemonList(){
        return pokemonListService.getPokemonList();
    }
}
