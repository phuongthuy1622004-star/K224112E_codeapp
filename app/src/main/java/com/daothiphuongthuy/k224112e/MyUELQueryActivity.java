package com.daothiphuongthuy.k224112e;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MyUELQueryActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    EditText editTextText;
    Button buttonVoice, buttonGetData;
    TextView textViewResult;

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
        textViewResult = findViewById(R.id.textView23);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
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
            textViewResult.setText("Không tìm thấy kết quả phù hợp. Các link tham khảo:\n" +
                    "1. Thương mại điện tử: https://myuel.uel.edu.vn/...\n" +
                    "2. Hệ thống thông tin quản lý: https://myuel.uel.edu.vn/...\n" +
                    "3. Kinh doanh số: https://myuel.uel.edu.vn/...");
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
            double eucDist = euclideanDistance(queryVector, majorVector);

            scoreLog.append(m.name).append(":\n")
                    .append("- Cosine Similarity: ").append(String.format("%.4f", cosSim))
                    .append("\n- Euclidean Distance: ").append(String.format("%.4f", eucDist))
                    .append("\n\n");

            if (cosSim > maxSimilarity) {
                maxSimilarity = cosSim;
                bestMajor = m;
            }
        }

        if (maxSimilarity <= 0) return null;

        textViewResult.setText(scoreLog.toString() + "=> Chọn: " + bestMajor.name + "\n\nĐang tải dữ liệu...");
        return bestMajor;
    }

    private double[] buildVector(String[] words, List<String> vocabulary) {
        double[] vector = new double[vocabulary.size()];
        Map<String, Integer> counts = new HashMap<>();
        for (String w : words) {
            counts.put(w, counts.getOrDefault(w, 0) + 1);
        }
        for (int i = 0; i < vocabulary.size(); i++) {
            String word = vocabulary.get(i);
            vector[i] = counts.getOrDefault(word, 0);
        }
        return vector;
    }

    private double cosineSimilarity(double[] v1, double[] v2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dotProduct += v1[i] * v2[i];
            norm1 += v1[i] * v1[i];
            norm2 += v2[i] * v2[i];
        }
        if (norm1 == 0 || norm2 == 0) return 0;
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private double euclideanDistance(double[] v1, double[] v2) {
        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(sum);
    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {
        Major major;
        String scoreLog;

        FetchDataTask(Major major) {
            this.major = major;
            this.scoreLog = textViewResult.getText().toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(major.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder html = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    html.append(line);
                }
                reader.close();

                Document doc = Jsoup.parse(html.toString());
                Elements rows = doc.select("table.gridview tr");
                
                if (rows.isEmpty()) {
                    // Try alternative selector if gridview class is not used
                    rows = doc.select("table tr");
                }

                StringBuilder result = new StringBuilder();
                result.append("CHƯƠNG TRÌNH ĐÀO TẠO: ").append(major.name).append("\n\n");
                
                for (Element row : rows) {
                    Elements cols = row.select("td");
                    if (cols.size() >= 5) {
                        String stt = cols.get(0).text().trim();
                        String maMon = cols.get(1).text().trim();
                        String tenMon = cols.get(2).text().trim();
                        String tinChi = cols.get(3).text().trim();
                        String hocKy = cols.get(4).text().trim();
                        
                        if (!stt.isEmpty() && !maMon.isEmpty()) {
                            result.append(stt).append(". ").append(maMon)
                                    .append(" - ").append(tenMon)
                                    .append(" (").append(tinChi).append(" TC) - HK: ").append(hocKy).append("\n");
                        }
                    }
                }
                
                if (result.length() < 100) {
                    return scoreLog + "\nKhông tìm thấy bảng dữ liệu chi tiết trên trang này.";
                }

                return scoreLog + "\n" + result.toString();

            } catch (Exception e) {
                return "Lỗi khi nạp dữ liệu: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            textViewResult.setText(s);
        }
    }
}
