package com.howard.www.plugin;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
/**
 * Created by mayijie on 2017/8/22.
 */
public class NetWorkInterfacePlugin extends CordovaPlugin {
	public static final String GET__WIFI_IP_ADDRESS="getWiFiIPAddress";
	public static final String GET_CARRIER_IP_ADDRESS="getCarrierIPAddress";
	private static final String TAG = "netWorkInterfacePlugin";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if (GET__WIFI_IP_ADDRESS.equals(action)) {
				String[] ipInfo = getWiFiIPAddress();
				String ip = ipInfo[0];
				String subnet = ipInfo[1];
				String fail = "0.0.0.0";
				if (ip == null || ip.equals(fail)) {
					callbackContext.error("No valid IP address identified");
					return false;
				}
				JSONObject r = new JSONObject();
				r.put("ip", ip);
				r.put("subnet", subnet);
				callbackContext.success(r);
				return true;
			} else if (GET_CARRIER_IP_ADDRESS.equals(action)) {
				String[] ipInfo = getCarrierIPAddress();
				String ip = ipInfo[0];
				String subnet = ipInfo[1];
				String fail = "0.0.0.0";
				if (ip == null || ip.equals(fail)) {
					callbackContext.error("No valid IP address identified");
					return false;
				}
				JSONObject r = new JSONObject();
				r.put("ip", ip);
				r.put("subnet", subnet);
				callbackContext.success(r);
				return true;
			}
			callbackContext.error("Error no such method '" + action + "'");
			return false;
		} catch(Exception e) {
			callbackContext.error("Error while retrieving the IP address. " + e.getMessage());
			return false;
		}
	}

	private String[] getWiFiIPAddress() {
		WifiManager wifiManager = (WifiManager) cordova.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();

		String ipString = String.format(
			"%d.%d.%d.%d",
			(ip & 0xff),
			(ip >> 8 & 0xff),
			(ip >> 16 & 0xff),
			(ip >> 24 & 0xff)
			);

		String subnet = "";
		try {
			InetAddress inetAddress = InetAddress.getByName(ipString);
			subnet = getIPv4Subnet(inetAddress);
		} catch (Exception e) {
		}

		return new String[]{ ipString, subnet };
	}

	private String[] getCarrierIPAddress() {
	  try {
	    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	       NetworkInterface intf = (NetworkInterface) en.nextElement();
	       //Log.e(TAG, "Interface: " + intf.toString() + " name: " + intf.getName() + " display nane: " + intf.getDisplayName() );
	       for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	          InetAddress inetAddress = enumIpAddr.nextElement();
			   if (!inetAddress.isLoopbackAddress() && (!intf.getName().equals("wlan0")) && inetAddress instanceof Inet4Address) {
				   String ipaddress = inetAddress.getHostAddress().toString();
				   String subnet = getIPv4Subnet(inetAddress);
				   return new String[]{ ipaddress, subnet };
	          }
	       }
	    }
	  } catch (SocketException ex) {
	     Log.e(TAG, "Exception in Get IP Address: " + ex.toString());
	  }
	  return new String[]{ null, null };
	}

	public static String getIPv4Subnet(InetAddress inetAddress) {
		try {
			NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);
			List<InterfaceAddress> intAddrs =  ni.getInterfaceAddresses();
			for (InterfaceAddress ia : intAddrs) {
				if (!ia.getAddress().isLoopbackAddress() && ia.getAddress() instanceof Inet4Address) {
					return getIPv4SubnetFromNetPrefixLength(ia.getNetworkPrefixLength()).getHostAddress().toString();
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	public static InetAddress getIPv4SubnetFromNetPrefixLength(int netPrefixLength) {
		try {
			int shift = (1<<31);
			for (int i=netPrefixLength-1; i>0; i--) {
				shift = (shift >> 1);
			}
			String subnet = Integer.toString((shift >> 24) & 255) + "." + Integer.toString((shift >> 16) & 255) + "." + Integer.toString((shift >> 8) & 255) + "." + Integer.toString(shift & 255);
			return InetAddress.getByName(subnet);
		}
		catch(Exception e){
		}
		return null;
	}
}
