package com.example.rwthforum;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class GetPostsTask extends
		AsyncTask<String, Integer, ArrayList<PostRowData>> {

	public final static String TAG = "GetPostsTask";
	private ProgressDialog progDialog;
	private String access_token, courseId;
	private int threadId;
	private GetPostsAct activity;
	private ArrayList<PostRowData> list;

	// SharedPreferences sp= getSharedPreferences("RWTH_File", 0);

	public GetPostsTask(GetPostsAct activity) {
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
	protected ArrayList<PostRowData> doInBackground(String... arg) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		access_token = arg[0];
		courseId = arg[1];
		threadId=Integer.parseInt(arg[2]);
		Log.d(TAG, "user_token passed= " + access_token);
		Log.d(TAG, "courseId passed= " + courseId);
		Log.d(TAG, "threadId passed= " + threadId);
		ArrayList<PostRowData> listData = new ArrayList<PostRowData>();
		
		String SOAP_ACTION = "http://cil.rwth-aachen.de/l2p/services/GetPosts";
		String NAMESPACE = "http://cil.rwth-aachen.de/l2p/services";
		String METHOD_NAME = "GetPosts";
		String URL = "http://demo.elearning.rwth-aachen.de/l2pwebservices/l2pshareddomainservice.asmx?WSDL";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userToken", access_token);
		request.addProperty("courseId", courseId);
		request.addProperty("threadId", threadId);
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
					PostRowData rowData = new PostRowData(); 
					rowData.setBody(temp[0].split("=")[1]);
					rowData.setID(Integer.parseInt(temp[1].split("=")[1]));
					rowData.setParentThreadId(Integer.parseInt(temp[2].split("=")[1]));
					rowData.setThreadIndex(temp[3].split("=")[1]);
					listData.add(rowData);
				}
				return listData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	protected void onPostExecute(ArrayList<PostRowData> list) {
		// TODO Auto-generated method stub
		super.onPostExecute(list);
		progDialog.dismiss();
		Log.d(TAG, "onPostExecute");
		this.list = list;
		this.activity.setList(list);

	}

}
