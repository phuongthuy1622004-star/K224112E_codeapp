package com.daothiphuongthuy.dals;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.daothiphuongthuy.models.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CategoryDAO {
    public static final String DATABASE_NAME = "K234112ESales.sqlite";
    public static final String TABLE_NAME = "Category";

    public static SQLiteDatabase database = null;

    public static ArrayList<Category> getCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            File dbFile = context.getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
                InputStream in = context.getAssets().open(DATABASE_NAME);
                OutputStream out = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
                out.close();
                in.close();
            }

            database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            while (cursor.moveToNext()) {
                String catId = cursor.getString(0);
                String catName = cursor.getString(1);
                String catDesc = cursor.getString(2);
                Category c = new Category(catId, catName, catDesc);
                categories.add(c);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("CategoryDAO", "Error getting categories. Make sure database is copied!", e);
        }
        return categories;
    }

    public static long saveCategory(Context context, Category category) {
        database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("CatID", category.getCatId()); //đặt tên cho giống sqlite copy paste qua
        values.put("CatName", category.getCatName());
        values.put("CatDesc", category.getCatDes());
        long result = database.insert(TABLE_NAME, null, values);
        return result;
    }

    public static long removeCategory(Context context, Category category) {
        database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        long result = database.delete(TABLE_NAME, "CatID=?", new String[]{category.getCatId()}); //1 ? là 1 phần tu 2?? là 2 phần tử => có thể tái sử dung hàm này
        return result;
    }
}