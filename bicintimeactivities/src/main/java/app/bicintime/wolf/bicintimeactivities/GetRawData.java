package app.bicintime.wolf.bicintimeactivities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wolf on 2/2/2016.
 */

//these are to have more reliable download process, so we can debug and find out what went wrong in case of download failure.
enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK
};

//This class will get the data from every file that it receives as input
public class GetRawData {

    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    //constructor
    public GetRawData(String url) {

        this.mRawUrl = url;
        this.mDownloadStatus = DownloadStatus.IDLE;

    }

    //in case of we need resetting the process.
    public void reset() {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;
    }

    //this is intended to be called when we get all the data
    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    //This method will prepare and execute our asynctask or "thread" to run in background and download the file data.
    public void execute() {

        this.mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }


    //Here's our thread that will run in background to read every line of the uri file and then will store it in a variable.
    public class DownloadRawData extends AsyncTask<String, Void, String> {

        //this method is called when doInBackground finishes, alaways.
        @Override
        protected void onPostExecute(String result) {
            mData = result;
            //Log.d(LOG_DLOAD, "Data returned was: " + mData);
            if (mData == null) {
                if (mRawUrl == null) {
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                } else {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                // Success
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        //this method is called when execute of this class is called.
        //Here we implement the logic of making a http connection to get the data
        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            if (params == null) {
                return null;
            }

            try {
                //this means it will get the first parameter we enter as input when we call execute.
                URL url = new URL(params[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();


            } catch (IOException e) {

                Log.e(LOG_DLOAD, "Error", e);
                return null;

            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_DLOAD, "Error closing stream", e);
                    }
                }
            }


        }
    }

}