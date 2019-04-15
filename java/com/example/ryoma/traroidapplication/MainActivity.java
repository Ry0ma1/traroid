
package com.example.ryoma.traroidapplication;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    SpeechRecognizer speechRec;
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.list_dynamic);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //タッチしたとき
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Log.v("OnTouch", "Touch Down");

                    Toast.makeText(MainActivity.this, "TouchDown", Toast.LENGTH_SHORT).show();

                    intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"話してください");

                    speechRec = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                    speechRec.setRecognitionListener(new RecognitionListener() {
                        @Override
                        public void onReadyForSpeech(Bundle params) {
                            Toast.makeText(getApplicationContext(),"ready", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onBeginningOfSpeech() {
                            Toast.makeText(getApplicationContext(),"start", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onRmsChanged(float rmsdB) {
                            Toast.makeText(getApplicationContext(),"voice changer", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onBufferReceived(byte[] buffer) {
                            Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onEndOfSpeech() {

                        }

                        @Override
                        public void onError(int error) {

                        }

                        @Override
                        public void onResults(Bundle results) {
                            ArrayList<String> recData = new ArrayList<String>();
                            recData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            //String key = SpeechRecognizer.RESULTS_RECOGNITION;
                            //ArrayList<String> arrayList = new ArrayList<String>();
                            //arrayList.add(key);

                            //Adapterの生成
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, recData);
                            //ListViewにセット
                            listView.setAdapter(adapter);
                            //List<String> recData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            Log.v("Result", "recData");
                            //Toast.makeText(getApplicationContext(), (String)recData, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPartialResults(Bundle partialResults) {

                        }


                        @Override
                        public void onEvent(int eventType, Bundle params) {

                        }
                    });
                    speechRec.startListening(intent);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) {
                    Log.v("OnTouch", "Touch UP");
                    Toast.makeText(MainActivity.this, "TouchUp", Toast.LENGTH_SHORT).show();
                    speechRec.stopListening();
                }
                return false;
            }
        });
    }
}
