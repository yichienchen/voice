package com.example.yichienchen.voice;

import android.content.Intent;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private Button btnend;
    private TextView textView2;
    private SpeechRecognizer recognizer;
    private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        btnStart = (Button) this.findViewById(R.id.button);//開啟語音模式
        textView2 = (TextView) this.findViewById(R.id.textView);
        btnend=(Button)this.findViewById(R.id.button3);//結束語音模式

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(new MyRecognizerListener());
        recognizer.startListening(intent);

        //按 Button 時，呼叫 SpeechRecognizer 的 startListening()
        //Intent 為傳遞給 SpeechRecognizer 的參數

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.startListening(intent);
            }
        });

        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.stopListening();
            }
        });
    }

    private class MyRecognizerListener implements RecognitionListener {

        @Override
        public void onResults(Bundle results) {
            List <String>resList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            textView2.setText( resList.get(0));
            if(resList.get(0).equals("開啟地圖")) {
                //TODO
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }

        @Override
        public void onError(int error) {

        }

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
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recognizer.startListening(intent);
                }
            }, 1000);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

