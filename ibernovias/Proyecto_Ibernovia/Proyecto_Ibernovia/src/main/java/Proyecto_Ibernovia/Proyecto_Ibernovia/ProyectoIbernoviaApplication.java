package Proyecto_Ibernovia.Proyecto_Ibernovia;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoIbernoviaApplication {

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();
			dotenv.entries().forEach(entry -> {
				System.setProperty(entry.getKey(), entry.getValue());
			});
		} catch (Exception e) {
			System.err.println("Note: No .env file found or error loading it: " + e.getMessage());
		}
		
		SpringApplication.run(ProyectoIbernoviaApplication.class, args);
	}

}
