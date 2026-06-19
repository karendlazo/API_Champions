package com.champions.Champions_League;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importante

@SpringBootTest
@ActiveProfiles("test") // Asegura que se use el perfil de prueba
class ChampionsLeagueApplicationTests {

	@Test
	void contextLoads() {
	}

}
