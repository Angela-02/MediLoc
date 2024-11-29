package com.example.mediloc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ai.picovoice.porcupine.Porcupine;
import ai.picovoice.porcupine.PorcupineManager;
import ai.picovoice.porcupine.PorcupineManagerCallback;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.Part;
import com.google.ai.client.generativeai.type.TextPart;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.os.Handler;
import android.os.Looper;

public class Chat_bot extends AppCompatActivity {
    private static final int SPEECH_REQUEST_CODE = 0;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private TextToSpeech textToSpeech;
    private TextView responseText;
    private Button speakButton;
    private GenerativeModelFutures model;
    private PorcupineManager porcupineManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        responseText = findViewById(R.id.responseText);
        speakButton = findViewById(R.id.speakButton);

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    responseText.setText("TTS language is not supported.");
                }
            } else {
                responseText.setText("TTS initialization failed.");
            }
        });

        // Initialize Gemini API
        String apiKey = "AIzaSyAzL1VBdXYPWO1CCWcx7-tDiEd2-zgJvlQ";  // Replace with your actual API key
        GenerativeModel gm = new GenerativeModel("gemini-pro", apiKey);
        model = GenerativeModelFutures.from(gm);

        speakButton.setOnClickListener(v -> checkPermissionAndStartListening());

        // Initialize Porcupine
        try {
            // Copy the model file from assets to a file in the app's data directory
            String keywordPath = copyAssetToFile("Hello-AI_en_android_v3_0_0.ppn");

            porcupineManager = new PorcupineManager.Builder()
                    .setAccessKey("fQzol6S7zECBrnYKZ2cEWEgSGLjZcnczlWroluN5S3zjowmoiF7HtQ==") // Replace with your actual access key
                    .setKeywordPath(keywordPath)
                    .setSensitivity(0.9f)
                    .build(getApplicationContext(), new PorcupineManagerCallback() {
                        @Override
                        public void invoke(int keywordIndex) {
                            runOnUiThread(() -> {
                                speak("Hello, How can I help you?");

                                // Set a 2-second delay before starting to listen
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startListening();
                                    }
                                }, 2000);

//                                startListening();
                            });
                        }
                    });

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
            } else {
                porcupineManager.start();
            }
        } catch (Exception e) {
            responseText.setText("Failed to initialize Porcupine: " + e.getMessage());
        }
    }

    private void checkPermissionAndStartListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        } else {
            startListening();
        }
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    porcupineManager.start();
                } catch (Exception e) {
                    responseText.setText("Failed to start Porcupine: " + e.getMessage());
                }
                startListening();
            } else {
                responseText.setText("Permission denied. Voice recognition is not available.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                getResponseFromGemini(spokenText);
            }
        }
    }

    private void getResponseFromGemini(String query) {
        // Create a list of Part objects
        List<Part> parts = new ArrayList<>();
        parts.add(new TextPart(query)); // Add a TextPart with the query text

        // Create the Content object with the list of Part objects
        Content content = new Content(parts);

        // Generate the response
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
//                String generatedText = result.getText();
                String generatedText = result.getText().replace("*", ""); // Remove asterisks

                runOnUiThread(() -> {
                    responseText.setText(generatedText);
                    speak(generatedText);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    responseText.setText("Error: " + t.getMessage());
                    speak("Sorry, I encountered an error.");
                });
            }
        }, MoreExecutors.directExecutor());
    }

    private void speak(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (porcupineManager != null) {
            try {
                porcupineManager.stop();
                porcupineManager.delete();
            } catch (Exception e) {
                // Handle exception
            }
        }
        super.onDestroy();
    }

    private String copyAssetToFile(String assetName) throws IOException {
        File file = new File(getFilesDir(), assetName);
        try (InputStream inputStream = getAssets().open(assetName);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        return file.getAbsolutePath();
    }
}
