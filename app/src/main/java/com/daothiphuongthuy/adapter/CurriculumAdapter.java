package com.daothiphuongthuy.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daothiphuongthuy.k224112e.R;
import com.daothiphuongthuy.models.CurriculumSubject;

import java.util.List;

public class CurriculumAdapter extends ArrayAdapter<CurriculumSubject> {
    private Activity context;
    private int resource;

    public CurriculumAdapter(@NonNull Activity context, int resource, @NonNull List<CurriculumSubject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        CurriculumSubject subject = getItem(position);
        if (subject != null) {
            TextView txtSTT = row.findViewById(R.id.txtSubjectSTT);
            TextView txtName = row.findViewById(R.id.txtSubjectName);
            TextView txtCode = row.findViewById(R.id.txtSubjectCode);
            TextView txtCredits = row.findViewById(R.id.txtSubjectCredits);
            TextView txtSemester = row.findViewById(R.id.txtSubjectSemester);

            txtSTT.setText(subject.getStt());
            txtName.setText(subject.getName());
            txtCode.setText("Mã: " + subject.getCode());
            txtCredits.setText(subject.getCredits() + " Tín chỉ");
            txtSemester.setText("Học kỳ: " + subject.getSemester());
        }

        return row;
    }
}
