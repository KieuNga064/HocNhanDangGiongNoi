package kieunga.com.hocnhandanggiongnoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import kieunga.com.hocnhandanggiongnoi.databinding.ActivityGoogleReconizationWithoutDialogBinding;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class GoogleReconizationWithoutDialogActivity extends AppCompatActivity implements RecognitionListener {

    ActivityGoogleReconizationWithoutDialogBinding binding;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGoogleReconizationWithoutDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        addEvents();
        
    }

    private void addEvents() {
        binding.btnNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnNoi.setText("Tui đang lắng nghe.....");
                speech.stopListening();
                speech.startListening(recognizerIntent);


            }
        });
    }


    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");

    }

    @Override
    public void onRmsChanged(float rmsdB) {

            Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    private String getErrorText(int error) {
        String message;
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        binding.txtKetQua.setText(text);
        nhanDangLenh(text);
        binding.btnNoi.setText("Nhấn vào đây để nhận dạng Tiếng Việt");

    }

    public String[]mau={"xanh", "do","tim","vang", "lam","den","trang"};
    private void nhanDangLenh(String text) {
        String textBoDau=VNCharacters.removeAccent(text);
        String[]c=textBoDau.split(" ");

            if (textBoDau.contains("tat") || textBoDau.contains("thoat")) {
                finish();
            } else {
                for (String m : mau) {
                    if (textBoDau.contains(m.toString()))
                        doiMau(m);
                }
            }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");

    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");

    }
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }
    private void doiMau(String m) {
        if(m=="do")
            binding.txtColor.setBackgroundColor(RED);
        else if(m=="xanh")
            binding.txtColor.setBackgroundColor(GREEN);
        else if(m=="tim")
            binding.txtColor.setBackgroundColor(Color.CYAN);
        else if(m=="vang")
            binding.txtColor.setBackgroundColor(YELLOW);
        else if(m=="lam")
            binding.txtColor.setBackgroundColor(BLUE);
        else if(m=="den")
            binding.txtColor.setBackgroundColor(BLACK);
        else if(m=="TRANG")
            binding.txtColor.setBackgroundColor(WHITE);

    }

}

