package br.com.infnet.atjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtJavaApplication.class, args);
		System.out.println("Rodando");
		EpisodioUtil episodioUtil = new EpisodioUtil();
		System.out.println(episodioUtil.buscarEpisodioPorId(1));
//		PersonagemUtil personagemUtil = new PersonagemUtil();
//		PersonagemRickAndMorty personagemRickAndMorty = personagemUtil.getOfficialCharacterByName("Rick Sanchez");
//		System.out.printf(personagemRickAndMorty.getGender());
	}

}
