package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckConnectivity {
	public boolean isConnected(Context context){

		boolean flag;
		
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {

			flag=true;
		}

		else{

			flag=false;
		}
		return flag;

	}

}
