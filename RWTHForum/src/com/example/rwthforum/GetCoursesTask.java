package com.example.rwthforum;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class GetCoursesTask extends
		AsyncTask<String, Integer, ArrayList<CourseRowData>> {

	public final static String TAG = "GetCoursesTask";
	private ProgressDialog progDialog;
	private String access_token;
	private CourseRoomAct activity;
	private ArrayList<CourseRowData> list;

	// SharedPreferences sp= getSharedPreferences("RWTH_File", 0);

	public GetCoursesTask(CourseRoomAct activity) {
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
	protected ArrayList<CourseRowData> doInBackground(String... arg) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		access_token = arg[0];
		Log.d(TAG, "user_token passed= " + access_token);
		ArrayList<CourseRowData> listData = new ArrayList<CourseRowData>();
		
		String SOAP_ACTION = "http://cil.rwth-aachen.de/l2p/services/GetCourseRooms";
		String NAMESPACE = "http://cil.rwth-aachen.de/l2p/services";
		String METHOD_NAME = "GetCourseRooms";
		String URL = "http://demo.elearning.rwth-aachen.de/l2pwebservices/l2pfoyerservice.asmx?WSDL";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userToken", access_token);
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
					String course = result.getProperty(i).toString();
					course = course.replace("anyType", "");
					course = course.replace("{", "");
					course = course.replace("}", "");
					Log.d(TAG, "course string= " + course);
					String[] temp = course.split(";");
					CourseRowData rowData = new CourseRowData(); 
					rowData.setCourseType(temp[0].split("=")[1]);
					rowData.setFullUrl(temp[1].split("=")[1]);
					rowData.setID(temp[2].split("=")[1]);
					// rowData.setMasterId(fragment.getProperty(4).toString());
					// rowData.setSemester(fragment.getProperty(5).toString());
					// rowData.setStatus(fragment.getProperty(6).toString());
					rowData.setTitle(temp[3].split("=")[1]);
					listData.add(rowData);
				}
				return listData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	protected void onPostExecute(ArrayList<CourseRowData> list) {
		// TODO Auto-generated method stub
		super.onPostExecute(list);
		progDialog.dismiss();
		Log.d(TAG, "onPostExecute");
		this.list = list;
		this.activity.setList(list);

	}

}
