package com.daothiphuongthuy.k224112e;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FontAndMusicActivity extends AppCompatActivity {
    Button btnPlayAudio1, btnPlayAudio2;
    TextView txtTitle;
    ListView lvFont;
    ArrayList<String> fonts;

    ArrayAdapter<String> adapterFonts;

    String LOG_TAG = FontAndMusicActivity.class.getSimpleName();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_font_and_music);
        addViews();
        addEvents();
        loadFonts();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadFonts() {
        try {
            AssetManager assetManager = getAssets();
            String[] files = assetManager.list("fonts");
            fonts.clear();
            for (String file : files) {
                fonts.add(file);
            }
            adapterFonts.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }

    private void addEvents() {
        btnPlayAudio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("music/audio1.mp3");
            }
        });
        btnPlayAudio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("music/audio2.mp3");
            }
        });
        lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeFont(i);
            }
        });
    }

    private void changeFont(int i) {
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fonts/" + adapterFonts.getItem(i));
        txtTitle.setTypeface(typeface);
    }

    private void playAudio(String audioPath) {
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(audioPath);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }

    private void addViews() {
        btnPlayAudio1 = findViewById(R.id.btnPlayAudio1);
        btnPlayAudio2 = findViewById(R.id.btnPlayAudio2);
        txtTitle = findViewById(R.id.txtTitle);
        lvFont = findViewById(R.id.lvFont);
        fonts = new ArrayList<>();
        adapterFonts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fonts);
        lvFont.setAdapter(adapterFonts);
    }
}