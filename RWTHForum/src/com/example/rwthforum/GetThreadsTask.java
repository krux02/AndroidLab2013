package com.example.rwthforum;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class GetThreadsTask extends
		AsyncTask<String, Integer, ArrayList<ThreadRowData>> {

	public final static String TAG = "GetThreadsTask";
	private ProgressDialog progDialog;
	private String access_token, courseId;
	private GetThreadsAct activity;
	private ArrayList<ThreadRowData> list;

	// SharedPreferences sp= getSharedPreferences("RWTH_File", 0);

	public GetThreadsTask(GetThreadsAct activity) {
		this.activity = activity;
		Log.d(TAG, "In task's constructor");
	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Loading",
				"Connecting the server", true, false);
		Log.d(TAG, "onPreExecute");

	}

	@Override
	protected ArrayList<ThreadRowData> doInBackground(String... arg) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		access_token = arg[0];
		courseId = arg[1];
		Log.d(TAG, "user_token passed= " + access_token);
		Log.d(TAG, "courseId passed= " + courseId);
		ArrayList<ThreadRowData> listData = new ArrayList<ThreadRowData>();
		
		String SOAP_ACTION = "http://cil.rwth-aachen.de/l2p/services/GetThreads";
		String NAMESPACE = "http://cil.rwth-aachen.de/l2p/services";
		String METHOD_NAME = "GetThreads";
		String URL = "http://demo.elearning.rwth-aachen.de/l2pwebservices/l2pshareddomainservice.asmx?WSDL";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userToken", access_token);
		request.addProperty("courseId", courseId);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			Log.d(TAG, "result from envelope= " + result.toString());
			if (result != null) {
				for (int i = 0; i < result.getPropertyCount(); i++) {
					Log.d(TAG, "result prop " + i + "= "
							+ result.getProperty(i).toString());
					String thread= result.getProperty(i).toString();
					thread = thread.replace("anyType", "");
					thread= thread.replace("{", "");
					thread= thread.replace("}", "");
					Log.d(TAG, "course string= " + thread);
					String[] temp =thread.split(";");
					ThreadRowData rowData = new ThreadRowData(); 
					rowData.setBody(temp[0].split("=")[1]);
					rowData.setID(Integer.parseInt(temp[1].split("=")[1]));
					rowData.setTitle(temp[2].split("=")[1]);
					listData.add(rowData);
				}
				return listData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	protected void onPostExecute(ArrayList<ThreadRowData> list) {
		// TODO Auto-generated method stub
		super.onPostExecute(list);
		progDialog.dismiss();
		Log.d(TAG, "onPostExecute");
		this.list = list;
		this.activity.setList(list);

	}

}
