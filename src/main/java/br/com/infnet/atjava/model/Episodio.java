package br.com.infnet.atjava.model;

import lombok.Data;

import java.util.List;

@Data
public class Episodio {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    private List<String> characters;
    private String url;
    private String created;
}
