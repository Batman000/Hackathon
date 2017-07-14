package justdoit.odie;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created by anaswara on 29/4/17.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    ITelephony telephonyService;
    MainActivity mActivity= new MainActivity();
    PendingIntent pi = null;



    public void onReceive(Context context, Intent intent) {


        pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        try {
            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
               // Toast.makeText(context,"Incoming Call State",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_SHORT).show();


            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                //Toast.makeText(context,"Call Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //Toast.makeText(context,"Call Idle State",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(tm);
            Bundle bundle = intent.getExtras();
            String phoneNumber = bundle.getString("incoming_number");
            Log.d("INCOMING", phoneNumber);
            int speed = mActivity.sp;

            if ((phoneNumber!=null) && speed > 10 ) {
                System.out.print("SPEED:"+ speed);
                telephonyService.endCall();
                Log.d("HANG UP", phoneNumber);
                sendMysms(phoneNumber,"I'm DRIVING");
                //mActivity.addNotification();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        }
    public void sendMysms(String phone,String message) {

        System.out.print("SEND MY SMS");
//        String phone = "8606018392";
//        String message = "I m khgytr driving";
       // Toast.makeText(mActivity.context, "In Function", Toast.LENGTH_SHORT).show();
        //Check if the phoneNumber is empty
        System.out.print("in funv");
        if (phone.isEmpty()) {
            //Toast.makeText(mActivity.context, "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
            System.out.print("Invalid");
        } else {
            System.out.print("SENT");
            SmsManager sms = SmsManager.getDefault();
            // if message length is too long messages are divided

            System.out.println(message);



                //PendingIntent sentIntent = PendingIntent.getBroadcast(mActivity.context, 0, new Intent("SMS_SENT"), 0);
                //PendingIntent deliveredIntent = PendingIntent.getBroadcast(mActivity.context, 0, new Intent("SMS_DELIVERED"), 0);
                sms.sendTextMessage(phone, null, message, pi, null);
                System.out.print("SENT");


        }
    }


//    public void onStop() {
//
//        mActivity.am.setRingerMode(3);
//        mActivity.onStop();
//        mActivity.finish();
//
//
//    }
}
