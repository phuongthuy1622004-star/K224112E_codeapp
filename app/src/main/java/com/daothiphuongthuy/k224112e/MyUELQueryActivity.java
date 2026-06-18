package com.daothiphuongthuy.k224112e;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.adapter.CurriculumAdapter;
import com.daothiphuongthuy.models.CurriculumSubject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyUELQueryActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    EditText editTextText;
    Button buttonVoice, buttonGetData;
    TextView txtStatus;
    ListView lvCurriculum;
    
    CurriculumAdapter adapter;
    ArrayList<CurriculumSubject> subjectList;

    private static class Major {
        String name;
        String url;
        String[] keywords;

        Major(String name, String url, String[] keywords) {
            this.name = name;
            this.url = url;
            this.keywords = keywords;
        }
    }

    private final Major[] majors = {
            new Major("Thương mại điện tử",
                    "https://myuel.uel.edu.vn/Default.aspx?ModuleId=f92f39b2-dea3-4185-8cbb-56c1c49c5226&OlogyID=411&DepartmentID=05&GraduateLevelID=DH&StudyTypeID=CQ",
                    new String[]{"thương", "mại", "điện", "tử", "thuong", "mai", "dien", "tu", "tmdt", "ecommerce", "bán", "hàng", "online", "ban", "hang"}),
            new Major("Hệ thống thông tin quản lý",
                    "https://myuel.uel.edu.vn/Default.aspx?ModuleId=f92f39b2-dea3-4185-8cbb-56c1c49c5226&OlogyID=7340405&DepartmentID=05&GraduateLevelID=DH&StudyTypeID=CQ",
                    new String[]{"hệ", "thống", "thông", "tin", "he", "thong", "thong", "tin", "httt", "quản", "lý", "quan", "ly", "information", "system"}),
            new Major("Kinh doanh số và trí tuệ nhân tạo",
                    "https://myuel.uel.edu.vn/Default.aspx?ModuleId=f92f39b2-dea3-4185-8cbb-56c1c49c5226&OlogyID=416&DepartmentID=05&GraduateLevelID=DH&StudyTypeID=CQ",
                    new String[]{"kinh", "doanh", "số", "trí", "tuệ", "nhân", "tạo", "so", "tri", "tue", "nhan", "tao", "ai", "digital", "business", "kds"})
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_uelquery);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        addViews();
        addEvents();
    }

    private void addViews() {
        editTextText = findViewById(R.id.editTextText);
        buttonVoice = findViewById(R.id.button9);
        buttonGetData = findViewById(R.id.button10);
        txtStatus = findViewById(R.id.txtStatus);
        lvCurriculum = findViewById(R.id.lvCurriculum);
        
        subjectList = new ArrayList<>();
        adapter = new CurriculumAdapter(this, R.layout.item_curriculum, subjectList);
        lvCurriculum.setAdapter(adapter);
    }

    private void addEvents() {
        buttonVoice.setOnClickListener(v -> speak());
        buttonGetData.setOnClickListener(v -> processQuery());
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Thiết bị không hỗ trợ nhận diện giọng nói", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                editTextText.setText(result.get(0));
            }
        }
    }

    private void processQuery() {
        String query = editTextText.getText().toString().toLowerCase().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
            return;
        }

        Major bestMatch = findBestMajor(query);
        if (bestMatch != null) {
            new FetchDataTask(bestMatch).execute();
        } else {
            txtStatus.setText("Không tìm thấy kết quả phù hợp.");
        }
    }

    private Major findBestMajor(String query) {
        String[] queryWords = query.split("\\s+");
        Set<String> vocabulary = new HashSet<>();
        for (String w : queryWords) vocabulary.add(w);
        for (Major m : majors) {
            for (String kw : m.keywords) vocabulary.add(kw);
        }

        List<String> vocabList = new ArrayList<>(vocabulary);
        double[] queryVector = buildVector(queryWords, vocabList);

        double maxSimilarity = -1;
        Major bestMajor = null;
        StringBuilder scoreLog = new StringBuilder();

        for (Major m : majors) {
            double[] majorVector = buildVector(m.keywords, vocabList);
            double cosSim = cosineSimilarity(queryVector, majorVector);
            
            if (cosSim > maxSimilarity) {
                maxSimilarity = cosSim;
                bestMajor = m;
            }
        }

        if (maxSimilarity <= 0) return null;

        txtStatus.setText("Ngành khớp nhất: " + bestMajor.name + " (Độ tương đồng: " + String.format("%.2f", maxSimilarity) + ")");
        return bestMajor;
    }

    private double[] buildVector(String[] words, List<String> vocabulary) {
        double[] vector = new double[vocabulary.size()];
        Map<String, Integer> counts = new HashMap<>();
        for (String w : words) counts.put(w, counts.getOrDefault(w, 0) + 1);
        for (int i = 0; i < vocabulary.size(); i++) {
            vector[i] = counts.getOrDefault(vocabulary.get(i), 0);
        }
        return vector;
    }

    private double cosineSimilarity(double[] v1, double[] v2) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dotProduct += v1[i] * v2[i];
            norm1 += v1[i] * v1[i];
            norm2 += v2[i] * v2[i];
        }
        if (norm1 == 0 || norm2 == 0) return 0;
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private class FetchDataTask extends AsyncTask<Void, Void, ArrayList<CurriculumSubject>> {
        Major major;

        FetchDataTask(Major major) { this.major = major; }

        @Override
        protected void onPreExecute() {
            txtStatus.setText("Đang tải chương trình đào tạo " + major.name + "...");
            subjectList.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<CurriculumSubject> doInBackground(Void... voids) {
            ArrayList<CurriculumSubject> results = new ArrayList<>();
            try {
                URL url = new URL(major.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder html = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) html.append(line);
                reader.close();

                Document doc = Jsoup.parse(html.toString());
                Elements rows = doc.select("table.gridview tr, table tr");
                
                for (Element row : rows) {
                    Elements cols = row.select("td");
                    if (cols.size() >= 5) {
                        String stt = cols.get(0).text().trim();
                        String code = cols.get(1).text().trim();
                        String name = cols.get(2).text().trim();
                        String credits = cols.get(3).text().trim();
                        String semester = cols.get(4).text().trim();
                        
                        if (!stt.isEmpty() && !code.isEmpty() && stt.matches("\\d+")) {
                            results.add(new CurriculumSubject(stt, code, name, credits, semester));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<CurriculumSubject> results) {
            if (results.isEmpty()) {
                txtStatus.setText("Không lấy được dữ liệu. Kiểm tra kết nối mạng.");
            } else {
                txtStatus.setText("Tìm thấy " + results.size() + " môn học.");
                subjectList.addAll(results);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
