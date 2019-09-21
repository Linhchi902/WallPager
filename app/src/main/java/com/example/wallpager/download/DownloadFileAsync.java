package com.example.wallpager.download;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileAsync extends AsyncTask<String, String, String> {
    private downloadInterface mDownloadInterface;
    private String name;

    public DownloadFileAsync(String name, downloadInterface mDownloadInterface) {
        this.name = name;
        this.mDownloadInterface = mDownloadInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mDownloadInterface != null) mDownloadInterface.onStart();
    }

    @Override
    protected String doInBackground(String... aurl) {

        int count;
        try {
            URL url = new URL(aurl[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            String path = Environment.getExternalStorageDirectory().toString() + "/Wallpaper/";
            File mFile = new File(path);
            mFile.mkdirs();

            File f = new File(path + name);
            if (!f.exists()){
                f.createNewFile();
            }
            // Output stream
            OutputStream output = new FileOutputStream(f);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return "";
        }

        return Environment.getExternalStorageDirectory().toString() +  "/Wallpaper/" + name;
    }

    protected void onProgressUpdate(String... progress) {
        if (mDownloadInterface != null)
            mDownloadInterface.onProgressUpdate(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String path) {
        if (mDownloadInterface != null)
            mDownloadInterface.onComplete(path);
    }

    public interface downloadInterface {
        void onProgressUpdate(int progress);

        void onComplete(String path);

        void onStart();
    }
}