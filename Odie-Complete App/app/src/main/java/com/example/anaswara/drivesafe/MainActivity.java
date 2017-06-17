package com.example.anaswara.drivesafe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {

    LocationManager lm;
    TextView yourTextView;
    public int sp =0;
    AudioManager am;
    //Button maps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        yourTextView=(TextView)findViewById(R.id.message_status_text_view);
        //maps=(Button)findViewById(R.id.button2);

//        maps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
//                startActivity(i);
//            }
//        });


         am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(0);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.onLocationChanged(null);

        //addNotification();
        }

    @Override
    public void onLocationChanged(Location location) {


        if (location==null){
            // if you can't get speed because reasons :)
            yourTextView.setText("0 km/h");
        }
        else{
            //int speed=(int) ((location.getSpeed()) is the standard which returns meters per second. In this example i converted it to kilometers per hour

            sp=(int) ((location.getSpeed()*3600)/1000);

//            Intent i = new Intent(this, PhoneStateReceiver.class);
//            i.putExtra("SPEED", speed);

            yourTextView.setText("Speed:"+sp+" km/h");
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


//    public void addNotification() {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification");
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }




//    public void sendMySMS() {
//
//        String phone = "8606018392";
//        String message = "I m main driving";
//            Toast.makeText(getApplicationContext(), "In Function", Toast.LENGTH_SHORT).show();
//        //Check if the phoneNumber is empty
//        if (phone.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
//        } else {
//
//            SmsManager sms = SmsManager.getDefault();
//            // if message length is too long messages are divided
//
//
//
//                PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
//                PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
//                sms.sendTextMessage(phone, null, message, sentIntent, deliveredIntent);
//
//
//        }
//    }

   @Override
    public void onDestroy() {
       super.onDestroy();
       int pid=android.os.Process.myPid();
        am.setRingerMode(3);
       System.exit(0);

    }
}

