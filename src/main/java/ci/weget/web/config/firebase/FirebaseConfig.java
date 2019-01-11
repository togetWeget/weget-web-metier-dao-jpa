package ci.weget.web.config.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Configuration
public class FirebaseConfig {
	@Bean
	public DatabaseReference firebaseDatabse() {
		DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		return firebase;
	}

	@Value("${toget.firebase.database.url}")
	private String databaseUrl;

	@Value("${toget.firebase.config.path}")
	private String configPath;

	@PostConstruct
	public void init() throws IOException {

	
		FileInputStream serviceAccount = new FileInputStream(configPath);

		FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(databaseUrl).build();
		FirebaseApp.initializeApp(options);
		Firestore db = FirestoreClient.getFirestore();
		System.out.println("test initialisation de  firebase de toget.pro reussi  ");
		
	}
}
