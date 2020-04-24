package kieunga.com.hocnhandanggiongnoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import kieunga.com.hocnhandanggiongnoi.databinding.ActivityGoogleReconizationWithoutDialogBinding;
import kieunga.com.hocnhandanggiongnoi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HashMap<String,String>dictionary=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeDictionary();
        addEvents();

    }

    private void makeDictionary() {
        dictionary.put("school", "trường học");
        dictionary.put("university", "đại học");
        dictionary.put("student","học sinh");
        dictionary.put("root","rễ");
        dictionary.put("beautiful","đẹp");
        dictionary.put("night", "buổi tối");
        dictionary.put("nice", "đẹp");
        dictionary.put("night","đêm");
    }

    private void addEvents() {
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoiDungGoogleAI();
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoTuNgauNhien();
            }
        });
        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, GoogleReconizationWithoutDialogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void taoTuNgauNhien() {
        Random random=new Random();
        int index=random.nextInt(dictionary.size());
        String word= (String) dictionary.keySet().toArray()[index];
        binding.txtEnglish.setText(word);
    }

    private void xuLyGiongNoiDungGoogleAI() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Please talk something");
        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Điện thoại không hỗ trợ",
                    Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 113: {
                if (requestCode==113 && resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    binding.txtSpeechToText.setText(result.get(0));
                }
                break;
            }

        }
    }



}
