package tranduythanh.com.hocnhandanggiongnoi;

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

import tranduythanh.com.hocnhandanggiongnoi.databinding.ActivityMainBinding;

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
        dictionary.put("school","Trường học");
        dictionary.put("university","Đại Học");
        dictionary.put("student","Sinh viên");
        dictionary.put("beautiful","Đẹp");
        dictionary.put("international","quốc tế");
        dictionary.put("root","gốc, rễ");
        dictionary.put("light","đèn, chiếu sáng");
        dictionary.put("like","thích");
        dictionary.put("night","buổi tối");
        dictionary.put("nice","đẹp");
        dictionary.put("why","tại sao?");
        dictionary.put("wife","vợ, bà xã, cảnh sát gia đình");
    }

    private void addEvents() {
        binding.btnGiongNoi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoiDungGoogleAI();
            }
        });
        binding.btnTaoTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoTuNgauNhien();
            }
        });
        binding.btnCach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GoogleRecognitionWithoutDialogActivity.class);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please talk something:");
        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "điện thoại cùi bắp này không có hỗ trợ Google AI Recognition",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113 && resultCode==RESULT_OK && data!=null)
        {
            //Google trả về 1 mảng các chuỗi âm thanh mà nó phân tích ra được
            //thường phần tử đầu tiên là đúng nhất
            //nó trả về bao nhiêu??? tùy vào ta cấu hình
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.txtSpeechToText.setText(result.get(0));
        }
    }
}