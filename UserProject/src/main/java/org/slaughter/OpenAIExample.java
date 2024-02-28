package org.slaughter;

import java.io.IOException;
import okhttp3.*;
/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2024年02月28日 10:38
 */
public class OpenAIExample {

    public static final String API_KEY = "YOUR_API_KEY_HERE";
    public static final String API_URL = "https://api.openai.com/v1/completions";

    public static void main(String[] args) {
        String prompt = "Once upon a time";
        try {
            String completion = getCompletion(prompt);
            System.out.println("Completion: " + completion);
        } catch (IOException e) {
            System.err.println("Error making request: " + e.getMessage());
        }
    }

    public static String getCompletion(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // You can change the model if needed
        // Adjust the number of tokens for your needs
        RequestBody body = new FormBody.Builder()
                .add("model", "text-davinci-002")
                .add("prompt", prompt)
                .add("max_tokens", "50")
                .build();
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            return responseBody;
        }
    }

}
