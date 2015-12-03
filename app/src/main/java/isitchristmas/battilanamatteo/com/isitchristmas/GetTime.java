package isitchristmas.battilanamatteo.com.isitchristmas;

/**
 * Created by sunil. http://stackoverflow.com/questions/13064750/how-to-get-current-time-from-internet-in-android
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;

public final class GetTime {

    public static Date getNTPDate() {

        String[] hosts = new String[]{
                "ntp02.oal.ul.pt", "ntp04.oal.ul.pt",
                "ntp.xs4all.nl"};

        NTPUDPClient client = new NTPUDPClient();
        // We want to timeout if a response takes longer than 5 seconds
        client.setDefaultTimeout(5000);

        for (String host : hosts) {
            try {
                InetAddress hostAddr = InetAddress.getByName(host);
                System.out.println("> " + hostAddr.getHostName() + "/" + hostAddr.getHostAddress());
                TimeInfo info = client.getTime(hostAddr);
                Date date = new Date(info.getReturnTime());
                return date;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.close();
        return null;
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}