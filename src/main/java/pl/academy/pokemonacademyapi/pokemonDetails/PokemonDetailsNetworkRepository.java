package pl.academy.pokemonacademyapi.pokemonDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
class PokemonDetailsNetworkRepository {
    private final static String ENDPOINT = "pokemon/%d";
    private final RestTemplate restTemplate;
    private final String endpointUrl;

    public PokemonDetailsNetworkRepository(RestTemplate restTemplate, @Value("${pokeapi.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.endpointUrl = baseUrl + ENDPOINT;
    }

    PokemonDetailsResponse fetchPokemonDetails(int pokemonId) {
        String url = String.format(endpointUrl, pokemonId);
        return restTemplate.getForObject(url, PokemonDetailsResponse.class);
    }
}


