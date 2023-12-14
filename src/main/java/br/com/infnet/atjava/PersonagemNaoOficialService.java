package br.com.infnet.atjava;

import br.com.infnet.atjava.exception.ElementoNaoEncontradoException;
import br.com.infnet.atjava.model.Episodio;
import br.com.infnet.atjava.model.PersonagemNaoOficial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonagemNaoOficialService {
    Logger logger = LoggerFactory.getLogger(PersonagemNaoOficialService.class);
    List<PersonagemNaoOficial> personagens = carregarPersonagens();

    EpisodioUtil episodioUtil = new EpisodioUtil();

    private List<PersonagemNaoOficial> carregarPersonagens() {
        logger.info("Inicializando todos personagens");

        ArrayList<PersonagemNaoOficial> personagensNaoOficial = new ArrayList<>();
        PersonagemNaoOficial personagemNaoOficial1 = new PersonagemNaoOficial(0, "terry", "alive", "human", "male", List.of("https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/20"));
        PersonagemNaoOficial personagemNaoOficial2 = new PersonagemNaoOficial(1, "lindsay", "alive", "human", "female", List.of("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/14", "https://rickandmortyapi.com/api/episode/5"));
        PersonagemNaoOficial personagemNaoOficial3 = new PersonagemNaoOficial(2, "lorry", "unknown", "human", "male", List.of("https://rickandmortyapi.com/api/episode/6", "https://rickandmortyapi.com/api/episode/2", "https://rickandmortyapi.com/api/episode/8"));
        PersonagemNaoOficial personagemNaoOficial4 = new PersonagemNaoOficial(3, "anna", "alive", "human", "female", List.of("https://rickandmortyapi.com/api/episode/16", "https://rickandmortyapi.com/api/episode/21", "https://rickandmortyapi.com/api/episode/9"));
        PersonagemNaoOficial personagemNaoOficial5 = new PersonagemNaoOficial(4, "hill", "alive", "human", "male", List.of("https://rickandmortyapi.com/api/episode/17", "https://rickandmortyapi.com/api/episode/13", "https://rickandmortyapi.com/api/episode/22"));

        personagensNaoOficial.add(personagemNaoOficial1);
        personagensNaoOficial.add(personagemNaoOficial2);
        personagensNaoOficial.add(personagemNaoOficial3);
        personagensNaoOficial.add(personagemNaoOficial4);
        personagensNaoOficial.add(personagemNaoOficial5);

        return personagensNaoOficial;
    }

    public List<PersonagemNaoOficial> listarPersonagens(String status, String gender) {
        return personagens.stream()
                .filter(personagem ->
                        (status == null || personagem.getStatus().equalsIgnoreCase(status)) &&
                                (gender == null || personagem.getGender().equalsIgnoreCase(gender)))
                .collect(Collectors.toList());
    }

    public PersonagemNaoOficial buscarPorId(int id) {
        for (PersonagemNaoOficial personagem : personagens) {
            if (personagem.getId() == id) {
                return personagem;
            }
        }
        throw new IllegalArgumentException("Personagem não encontrado para o ID: " + id);
    }

    public void adicionar(PersonagemNaoOficial personagemNaoOficial) {
        int ultimoId = 0;
        for (PersonagemNaoOficial personagem : personagens) {
            if (personagem.getId() > ultimoId) {
                ultimoId = personagem.getId();
            }
        }
        personagemNaoOficial.setId(ultimoId + 1);
        personagens.add(personagemNaoOficial);
    }

    public ResponseEntity<String> deletar(int id) {
        for (PersonagemNaoOficial personagem : personagens) {
            if (personagem.getId() == id) {
                personagens.remove(personagem);
                return new ResponseEntity<>("Personagem removido com sucesso", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Personagem não encontrado", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> atualizar(int id, PersonagemNaoOficial personagemNaoOficial) {
        for (PersonagemNaoOficial personagem : personagens) {
            int position = personagens.indexOf(personagem);
            if (personagem.getId() == id) {
                personagens.remove(personagem);
                personagemNaoOficial.setId(id);
                personagens.add(position, personagemNaoOficial);
                return new ResponseEntity<>("Personagem atualizado com sucesso", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Personagem não encontrado", HttpStatus.NOT_FOUND);
    }

    public List<Episodio> buscarEpisodios(int id) {
        PersonagemNaoOficial personagem = buscarPorId(id);

        ArrayList<String> episodioIds = new ArrayList<>();

        for (String episodio : personagem.getEpisodes()) {
            String[] url = episodio.split("/");
            episodioIds.add(url[url.length - 1]);
        }

        ArrayList<Episodio> episodios = new ArrayList<>();

        for (String episodioId : episodioIds) {
            episodios.add(episodioUtil.buscarEpisodioPorId(Integer.parseInt(episodioId)));
        }
        return episodios;
    }
}
