package com.example.l2plabv0;

import com.example.l2plabv0.CommonData.TypeOfHttpOperation;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressSandbox extends Activity {
	private TextView textView;
	private ProgressBar progressBar;
	private DownloadWebPageTask mTask = null;
	private Button oAuthManageButton;
	private boolean showManageUrlButton = false;

	@Override
	protected void onResume() {
		super.onResume();
		CommonData.writeLog("onResume", "This block was called");

		textView = (TextView) findViewById(R.id.TextView01);
		textView.setText("Checking...");
		textView.setVisibility(View.VISIBLE);
		
		oAuthManageButton = (Button) findViewById(R.id.openOAuthManageUrl);
		oAuthManageButton.setVisibility(View.INVISIBLE);
		oAuthManageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Uri uri = Uri.parse(CommonData.GetManageDeviceCodeUrl()
						+ CommonData.getSingleValueFromKey("USER_CODE",
								ProgressSandbox.this));
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});		
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		CommonData.currentOperation = TypeOfHttpOperation.OAUTH_NONE;
		// CommonData.deleteAllSharedPreferences(getApplicationContext());
		downloadOAuthData();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//CommonData.deleteAllSharedPreferences(ProgressSandbox.this);
		CommonData.printAllSharedPreference(ProgressSandbox.this);

		CommonData.writeLog("onCreate", "This block was called");
	}
	

	// AsyncTask <TypeOfVarArgParams , ProgressValue , ResultValue> .
	private class DownloadWebPageTask extends AsyncTask<String, String, String> {

		private boolean failureToastNecessary = false;

		@Override
		protected void onPreExecute() {
			// textView.setText("Hello !!!");
			progressBar = (ProgressBar) findViewById(R.id.progressBar1);
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			textView.setText(values[0]);
			if (showManageUrlButton) {
				String msg = "Please authorize with this code: ";
				msg = msg
						+ CommonData.getSingleValueFromKey("USER_CODE",
								ProgressSandbox.this);
				textView.setText(msg + "\n" + values[0]);
				oAuthManageButton.setVisibility(View.VISIBLE);
			} else
				oAuthManageButton.setVisibility(View.INVISIBLE);
		}

		@Override
		protected String doInBackground(String... urls) {
			String response = "", oAuthParam = "", oAuthUrl = "";
			boolean httpOperationShouldContinue = true;
			int noOfAttempt = 0;

			while (httpOperationShouldContinue == true) {
				++noOfAttempt;
				try {
					switch (CommonData.currentOperation) {
					case OAUTH_CALL:
						oAuthUrl = CommonData.GetOAuthLoginUrl();
						oAuthParam = "scope=l2p.rwth&client_id="
								+ ProgressSandbox.this
										.getString(R.string.APPLICATION_SECRET_KEY);
						response = CommonData.POST(oAuthUrl, oAuthParam);
						CommonData.saveAllCodesIntoSharedPreference(response,
								ProgressSandbox.this);
						String userCode = CommonData.getSingleValueFromKey(
								"USER_CODE", ProgressSandbox.this);
						if (userCode.length() == 0) {
							throw new Exception("Server or Communication error");
						}
						response = "Please authorize with this code: "
								+ userCode;
						showManageUrlButton = true;
						httpOperationShouldContinue = false;
						break;
					case OAUTH_POLL:
						oAuthUrl = CommonData.GetOAuthLoginUrl();
						oAuthParam = "scope=l2p.rwth&client_id="
								+ ProgressSandbox.this
										.getString(R.string.APPLICATION_SECRET_KEY);
						response = CommonData.POST(oAuthUrl, oAuthParam);

						CommonData.saveAllCodesIntoSharedPreference(response,
								ProgressSandbox.this);
						String temp = CommonData.getSingleValueFromKey(
								"ACCESS_TOKEN", ProgressSandbox.this);
						if (temp.length() == 0) {
							showManageUrlButton = true;
							this.publishProgress("Attempt: " + noOfAttempt);
							Thread.sleep(5000);
						} else {
							showManageUrlButton = false;
							this.publishProgress("Successfully got token.");
							response = "Access token found: " + temp;
							httpOperationShouldContinue = false;
						}
						break;
					default:
						break;
					}
				} catch (Exception ex) {
					// Log.e(ex.getClass().getName(), ex.getMessage());
					failureToastNecessary = true;
					response = ex.getMessage();
					CommonData.writeLog("OAUTH of "+CommonData.currentOperation.toString(), ex);
					httpOperationShouldContinue = false;
				}
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			progressBar.setVisibility(View.GONE);
			textView.setText(result);

			if (failureToastNecessary) {
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(ProgressSandbox.this, result,
						duration);
				toast.show();
				return;
			}
			if (showManageUrlButton) {
				oAuthManageButton.setVisibility(View.VISIBLE);
			} else
				oAuthManageButton.setVisibility(View.INVISIBLE);

			CommonData.printAllSharedPreference(ProgressSandbox.this);
		}
	}

	private void downloadOAuthData() {
		if (mTask != null
				&& mTask.getStatus() != DownloadWebPageTask.Status.FINISHED) {
			mTask.cancel(true);
		}
		// Logic for next calling function
		String accessToken = CommonData.getSingleValueFromKey("ACCESS_TOKEN",
				ProgressSandbox.this);
		String deviceCode = CommonData.getSingleValueFromKey("DEVICE_CODE",
				ProgressSandbox.this);

		oAuthManageButton.setVisibility(View.INVISIBLE);

		if (accessToken.length() > 0) {
			CommonData.currentOperation = TypeOfHttpOperation.OAUTH_APICALL_POSSIBLE;
			textView.setText("API Token: "
					+ CommonData.getSingleValueFromKey("ACCESS_TOKEN",
							ProgressSandbox.this));
			progressBar.setVisibility(View.GONE);
			return;
		} else if (deviceCode.length() > 0) {
			CommonData.currentOperation = TypeOfHttpOperation.OAUTH_POLL;
			textView.setText("Checking authorization of: "
					+ CommonData.getSingleValueFromKey("DEVICE_CODE",
							ProgressSandbox.this));
		} else {
			CommonData.currentOperation = TypeOfHttpOperation.OAUTH_CALL;
		}
		// execute(String[]) you can put array of links to web pages, or array
		// of Integer[]
		// if first param is Integer[] etc.
		mTask = (DownloadWebPageTask) new DownloadWebPageTask()
				.execute(new String[] { "" });
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mTask != null
				&& mTask.getStatus() != DownloadWebPageTask.Status.FINISHED) {
			mTask.cancel(true);
			mTask = null;
		}
	}

}