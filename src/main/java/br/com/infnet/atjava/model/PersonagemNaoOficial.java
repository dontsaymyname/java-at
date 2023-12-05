package br.com.infnet.atjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class PersonagemNaoOficial {
    private int id;
    private String name;
    private String status;
    private String species;
    private String gender;
    private List<String> episode;

}
