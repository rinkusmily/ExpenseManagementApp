package com.shrinkcom.expensemanagementapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.facebook.FacebookSdk;
import com.shrinkcom.expensemanagementapp.BuildConfig;
import com.shrinkcom.expensemanagementapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Async Task to download file from URL
 */
class DownloadFile extends AsyncTask<String, String, String> {

	private ProgressDialog progressDialog;
	private String fileName;
	private String folder;
	private boolean isDownloaded;
	Context context;
	Activity activity;
	String file_url = "";

	DownloadFile()
	{}

	void executeNetworkCall(Context context, String url)
	{
		this.file_url = url;
		this.context = context;
		//  FacebookSdk.sdkInitialize(this.context);
		this.execute();
	}

	/**
	 * Before starting background thread
	 * Show Progress Bar Dialog
	 */

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		this.progressDialog = new ProgressDialog(context);
		this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		this.progressDialog.setCancelable(false);
		this.progressDialog.show();
	}

	/**
	 * Downloading file in background thread
	 */
	@Override
	protected String doInBackground(String... f_url1) {
		int count;
		try {
			Log.e("DATAURLLLL",">>>>> "+file_url);
			URL url = new URL(file_url);
			URLConnection connection = url.openConnection();
			connection.connect();
			// getting file length
			int lengthOfFile = connection.getContentLength();


			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

			//Extract file name from URL
			fileName = file_url.substring(file_url.lastIndexOf('/') + 1, file_url.length());

			//Append timestamp to file name
			fileName = timestamp + "_" + fileName;

			//External directory path to save file
			folder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Hail Note/";

			Log.e("FOLGDERPATH",">>>"+folder);


			//Create androiddeft folder if it does not exist

			File directory = new File(folder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Output stream to write file
			OutputStream output = new FileOutputStream(folder + fileName);

			byte data[] = new byte[1024];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				// After this onProgressUpdate will be called
				publishProgress("" + (int) ((total * 100) / lengthOfFile));
				Log.v("DIKSHA", "Progress : " + (int) ((total * 100) / lengthOfFile));

				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();
			//    return "Downloaded at: " + folder + fileName;
			return  folder + fileName;

		} catch (Exception e) {
			Log.e("Error: ", e.getMessage());
		}

		return "no data found on this date";
	}

	/**
	 * Updating progress bar
	 */
	protected void onProgressUpdate(String... progress) {
		// setting progress percentage
		progressDialog.setProgress(Integer.parseInt(progress[0]));
	}


	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onPostExecute(String message) {
		// dismiss the dialog after the file was downloaded
		this.progressDialog.dismiss();

      //   Toast.makeText(context , message, Toast.LENGTH_SHORT).show();

		if(message.equals("no data found on this date")){

			Toast.makeText(context, message, Toast.LENGTH_LONG).show();

		}else {

			// Display File path after downloading

			Toast.makeText(context, R.string.export_successfully, Toast.LENGTH_LONG).show();

			Log.v("Dikshaaaa","Storage_data1>>"+message);
			// Display File path after downloading



			Intent intentShareFile = new Intent(Intent.ACTION_SEND);
			File fileWithinMyDir = new File(message);

			if (fileWithinMyDir.exists()) {
				                                                         //Uri.parse("content://"+message)
				Log.v("Dikshaaaa","Storage_data2>>"+Environment.getExternalStorageDirectory().getAbsolutePath());
				Log.v("Dikshaaaa","Storage_data3>>"+Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Hail Note" + "/" + fileWithinMyDir);

				File pdfFile = new File(message);
				Uri data = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", pdfFile);


				//  intent.setDataAndType(data, "application/pdf/csv");
				intentShareFile.setDataAndType(data, "application/pdf");


				intentShareFile.putExtra(Intent.EXTRA_STREAM, data);
				intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				context.startActivity(Intent.createChooser(intentShareFile, "Share File"));
				intentShareFile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);









		/*		MimeTypeMap mime = MimeTypeMap.getSingleton();
				String ext = fileWithinMyDir.getName().substring(fileWithinMyDir.getName().lastIndexOf(".") + 1);
				String type = mime.getMimeTypeFromExtension(ext);
				try {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_SEND);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
						intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
						Uri contentUri = FileProvider.getUriForFile(context, "com.shrinkcom.expensemanagementapp", fileWithinMyDir);
						intent.setDataAndType(contentUri, type);
					} else {
						intent.setDataAndType(Uri.fromFile(fileWithinMyDir), type);
					}

					context.startActivity(Intent.createChooser(intent, "Share File"));
				} catch (ActivityNotFoundException anfe) {
					Toast.makeText(context, "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
				}

*/




			}
		}
	}
}