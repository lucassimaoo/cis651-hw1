package com.example.lab4;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private MyRecyclerAdapter adapter;

    public ImageDownloadTask(ImageView imageView, MyRecyclerAdapter adapter) {
        this.imageView = imageView;
        this.adapter = adapter;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        Bitmap image = ImageDownload.downloadImageusingHTTPGetRequest(url[0]);
        adapter.getImageCache().put(url[0], image);
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
