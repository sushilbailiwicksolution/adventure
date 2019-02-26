package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckConnectivity {

	public boolean isConnected(Context context){
		boolean flag;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connMgr != null) {
			networkInfo = connMgr.getActiveNetworkInfo();
		}
		flag = networkInfo != null && networkInfo.isConnected();
		return flag;
	}

}
