import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AuthenticationCaller {
    public static void call(ArrayList<String> parameters) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("./src/Auth/bin/Debug/net8.0-windows/Auth.exe", "login");

            // Redirect the standard output stream to capture the output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get the input stream of the process
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read the output line by line
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Read email and ID
            String email, id;
            if (lines.get(1).equalsIgnoreCase("Success")) {
                email = lines.get(3);
                id = lines.get(2);
            } else {
                email = null;
                id = null;
            }

            // You can optionally wait for the process to complete
            process.waitFor();

            if (id != null && email != null) {
                CosmosAsyncClient cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(serviceEndpoint)
                .key(key)
                .buildAsyncClient();
            }

            // Return id and email
            parameters.set(0, id);
            parameters.set(1, email);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}