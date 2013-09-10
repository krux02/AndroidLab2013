package com.example.rwthforum;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AddThreadTask extends
		AsyncTask<String, Integer, Integer> {

	public final static String TAG = "AddThreadTask";
	private ProgressDialog progDialog;
	private String access_token,courseId,title,body;
	private AddThreadAct activity;
	private int i;

	public AddThreadTask(AddThreadAct activity) {
		this.activity = activity;
		Log.d(TAG, "In task's constructor");
	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Posting",
				"Connecting the server", true, false);
		Log.d(TAG, "onPreExecute");

	}

	@Override
	protected Integer doInBackground(String... arg) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		access_token = arg[0];
		courseId=arg[1];
		title=arg[2];
		body=arg[3];
		Log.d(TAG, "userToken passed= " + access_token);
		Log.d(TAG, "courseId passed= " + courseId);
		Log.d(TAG, "title passed= " + title);
		Log.d(TAG, "body passed= " + body);
		
		String SOAP_ACTION = "http://cil.rwth-aachen.de/l2p/services/AddThread";
		String NAMESPACE = "http://cil.rwth-aachen.de/l2p/services";
		String METHOD_NAME = "AddThread";
		String URL = "http://demo.elearning.rwth-aachen.de/l2pwebservices/l2pshareddomainservice.asmx?WSDL";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userToken", access_token);
		request.addProperty("courseId", courseId);
		request.addProperty("title", title);
		request.addProperty("body",  body);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			Log.d(TAG, "inside try block ");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			Log.d(TAG, "result from envelope= " + result.toString());
				return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	protected void onPostExecute(Integer i) {
		// TODO Auto-generated method stub
		super.onPostExecute(i);
		progDialog.dismiss();
		Log.d(TAG, "onPostExecute");
		this.i=i;
		this.activity.finish();

	}

}
