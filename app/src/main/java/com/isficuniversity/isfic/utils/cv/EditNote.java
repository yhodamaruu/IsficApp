package com.isficuniversity.isfic.utils.cv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.isficuniversity.isfic.R;

import java.util.ArrayList;

public class EditNote extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.note_title);
        contentEditText = findViewById(R.id.note_content);

        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String title = getIntent().getStringExtra("title");
        if (title != null) {
            titleEditText.setText(title);
            contentEditText.setText(sharedPreferences.getString(title, ""));
        }

        findViewById(R.id.save_btn).setOnClickListener(v -> saveNote());


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

                Toast.makeText(EditNote.this, "Erreur de reconnaissance vocale", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    contentEditText.setText(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        ImageButton voiceRecordButton = findViewById(R.id.voice_record_btn);
        voiceRecordButton.setOnClickListener(v -> startVoiceRecognition());
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Dites quelque chose...");
        speechRecognizer.startListening(intent);
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        editor.putString(title, content);
        editor.apply();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}
