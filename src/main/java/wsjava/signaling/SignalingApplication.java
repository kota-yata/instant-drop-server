package wsjava.signaling;

import java.util.Collection;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SignalingApplication {

	public static void main(String[] args) {
    SpringApplication app = new SpringApplication(SignalingApplication.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", System.getenv("PORT")));
    app.run(args);
		// SpringApplication.run(SignalingApplication.class, args);
	}

}
