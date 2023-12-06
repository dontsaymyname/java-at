package br.com.infnet.atjava;


import br.com.infnet.atjava.model.Episodio;
import br.com.infnet.atjava.model.PersonagemNaoOficial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {

    @Autowired
    PersonagemNaoOficialService personagemNaoOficialService;

    Logger logger = LoggerFactory.getLogger(PersonagemController.class);

    @GetMapping
    public List<PersonagemNaoOficial> listarTodos(@RequestParam(required = false) String status,
                                                  @RequestParam(required = false) String gender){
        logger.info("Listando todos os personagens não oficiais");
        return personagemNaoOficialService.listarPersonagens(status, gender);
    }

    @GetMapping("/{id}")
    public PersonagemNaoOficial buscarPorId(@PathVariable int id){
        logger.info("Buscando personagem com id: " + id);
        return personagemNaoOficialService.buscarPorId(id);

    }

    @GetMapping("/{id}/episodios")
    public List<Episodio> buscarEpisodiosDoPersongaem(@PathVariable int id){
        logger.info("Buscando episódios do personagem do id: " + id);
        return personagemNaoOficialService.buscarEpisodios(id);
    }

    @PostMapping
    public void adicionar(@RequestBody PersonagemNaoOficial personagemNaoOficial){
        logger.info("Adicionando novo personagem");
        personagemNaoOficialService.adicionar(personagemNaoOficial);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<String> atualizar(@PathVariable int id, @RequestBody  PersonagemNaoOficial personagemNaoOficial){
        return personagemNaoOficialService.atualizar(id,personagemNaoOficial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        logger.info("Deletando personagem com id: " + id);
        return personagemNaoOficialService.deletar(id);

    }



}
