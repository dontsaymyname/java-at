package br.com.infnet.atjava;

import br.com.infnet.atjava.model.Episodio;
import br.com.infnet.atjava.model.PersonagemNaoOficial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonagemNaoOficialServiceTests {

    @Test
    @DisplayName("Deve retornar a lista completa dos personagens")
    public void listarPersonagensSemFiltro() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        List<PersonagemNaoOficial> lista = personagemNaoOficialService.personagens;
        assertEquals(lista, personagemNaoOficialService.listarPersonagens(null, null));
    }

    @Test
    @DisplayName("Deve retornar a lista somentos com os elementos de acordo com os filtros")
    public void listarPersonagensComFiltros() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();

        List<PersonagemNaoOficial> listaStatus = personagemNaoOficialService.personagens.stream()
                .filter(personagem ->
                        (personagem.getStatus().equals("alive"))).toList();

        List<PersonagemNaoOficial> listaGender =  personagemNaoOficialService.personagens.stream()
                .filter(personagem ->
                        (personagem.getGender().equals("male"))).toList();


        assertEquals(listaStatus, personagemNaoOficialService.listarPersonagens("alive", null));
        assertEquals(listaGender, personagemNaoOficialService.listarPersonagens(null, "male"));
    }

    @Test
    @DisplayName("Deve lançar IllegarArgumentException para personagem não existente")
    public void validaExistenciaDoPersonagem() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        assertThrows(IllegalArgumentException.class, () -> {
            personagemNaoOficialService.buscarPorId(-7000);
        });
    }

    @Test
    @DisplayName("Deve buscar um personagem pelo id")
    public void buscaPersonagemPorId() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        PersonagemNaoOficial personagem = personagemNaoOficialService.buscarPorId(3);
        assertEquals(3, personagem.getId());
        assertEquals("anna", personagem.getName());

    }

    @Test
    @DisplayName("Deve adicionar um personagem na lista")
    public void testaAdicionarPersonagem() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        PersonagemNaoOficial novoPersonagem = new PersonagemNaoOficial(4, "alicia", "unknown", "human", "female", List.of("https://rickandmortyapi.com/api/episode/18", "https://rickandmortyapi.com/api/episode/14", "https://rickandmortyapi.com/api/episode/23"));
        personagemNaoOficialService.adicionar(novoPersonagem);

        assertEquals(personagemNaoOficialService.personagens.get(personagemNaoOficialService.personagens.size() -1), novoPersonagem);

    }

    @Test
    @DisplayName("Deve deletar um personagem da lista")
    public void testaDeletarPersongaem() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        PersonagemNaoOficial personagemDeletado = personagemNaoOficialService.buscarPorId(1);

        ResponseEntity<String> resposta = personagemNaoOficialService.deletar(1);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertFalse(personagemNaoOficialService.personagens.contains(personagemDeletado));

    }

    @Test
    @DisplayName("Deve atualizar as informações de um personagem da lista")
    public void testaAtualizarPersongaem() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        PersonagemNaoOficial personagemAntes = personagemNaoOficialService.buscarPorId(1);
        PersonagemNaoOficial personagemAtualizado = new PersonagemNaoOficial(4, "alicia", "unknown", "human", "female", List.of("https://rickandmortyapi.com/api/episode/18", "https://rickandmortyapi.com/api/episode/14", "https://rickandmortyapi.com/api/episode/23"));

        ResponseEntity<String> resposta = personagemNaoOficialService.atualizar(1, personagemAtualizado);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(personagemNaoOficialService.personagens.get(1).getId(), personagemAntes.getId());
        assertEquals(personagemNaoOficialService.personagens.get(1).getName(), "alicia");
        assertEquals(personagemNaoOficialService.personagens.get(1).getStatus(), "unknown");
        assertEquals(personagemNaoOficialService.personagens.get(1).getGender(), "female");

    }

    @Test
    @DisplayName("Deve buscar a lista de episodios pelo id do personagem")
    public void buscaListaDeEpisodios() {
        PersonagemNaoOficialService personagemNaoOficialService = new PersonagemNaoOficialService();
        List<Episodio> episodios = personagemNaoOficialService.buscarEpisodios(1);
        Episodio episodio = episodios.get(2);

        assertEquals(episodio.getName(), "Meeseeks and Destroy");
    }
}
