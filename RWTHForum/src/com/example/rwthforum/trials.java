package com.example.rwthforum;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class trials extends
AsyncTask<String,Void,Void> {

public final static String TAG = "trials";
private ProgressDialog progDialog;
private String access_token;

// SharedPreferences sp= getSharedPreferences("RWTH_File", 0);

public trials() {

Log.d(TAG, "In task's constructor");
//trials a=new trials();
//a.execute(access_token);
//finish();
}


protected Void doInBackground(String... arg) {
// TODO Auto-generated method stub
Log.d(TAG, "doInBackground");
access_token=arg[0];
Log.d(TAG, "user_token passed= " + access_token);

String SOAP_ACTION = "http://cil.rwth-aachen.de/l2p/services/GetThreads";
String NAMESPACE = "http://cil.rwth-aachen.de/l2p/services";
String METHOD_NAME = "GetThreads";
String URL = "http://demo.elearning.rwth-aachen.de/l2pwebservices/l2pshareddomainservice.asmx?WSDL";

SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
request.addProperty("userToken", access_token);
request.addProperty("courseId", "12ss-34918");
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
			
			String i1=(temp[0].split("=")[1]);
			String j=(temp[1].split("=")[1]);
			String k=(temp[2].split("=")[1]);
			// rowData.setMasterId(fragment.getProperty(4).toString());
			// rowData.setSemester(fragment.getProperty(5).toString());
			// rowData.setStatus(fragment.getProperty(6).toString());
		String l=(temp[3].split("=")[1]);
		}
		
	}
} catch (Exception e) {
	e.printStackTrace();
}
return null;

}



}

