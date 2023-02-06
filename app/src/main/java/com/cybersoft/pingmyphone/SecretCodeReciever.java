package com.cybersoft.pingmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SecretCodeReciever extends BroadcastReceiver {
    public static final String Name = "_code_real_new";
    private String TAG = SecretCodeReciever.class.getSimpleName();
    Context context;
    //AudioManager ring  = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    @Override

    public void onReceive(Context context, Intent intent) {
        SaveCodeClass newcode = new SaveCodeClass();
        String current_code = newcode.getCode(Name,context);
        //Toast.makeText(context,current_code,Toast.LENGTH_LONG).show();
        MediaPlayer player = MediaPlayer.create(context, R.string.app_name);
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {
                // Convert Object array
                String format=intent.getStringExtra("format");
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                // Sender's phone number
                //     str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str = msgs[i].getMessageBody().toString();
                // Newline <img src="http://codetheory.in/wp-includes/images/smilies/simple-smile.png" alt=":-)" class="wp-smiley" style="height: 1em; max-height: 1em;">
                //     str += "\n";
            }
            if(!current_code.isEmpty()){
                if(new String(current_code.toLowerCase()).equals(str.toLowerCase())) {
                    // Display the entire SMS Message
         /*       player.start();
                Log.d(TAG, str);*/
                    Toast.makeText(context, str.toString(), Toast.LENGTH_SHORT).show();
                    AudioManager ring  = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    int streamMaxVolume = ring.getStreamMaxVolume(AudioManager.STREAM_RING);
                    ring.setStreamVolume(AudioManager.STREAM_RING,streamMaxVolume,AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
                    //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    //MediaPlayer mediaPlayer = MediaPlayer.create(context, notification);
                    //mediaPlayer.start();
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    Ringtone ringtone = RingtoneManager.getRingtone(context,uri);
                    ringtone.play();
                    //    ring.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 20, 0);
                    //ring.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
            else if(new String("pingme").equals(str.toLowerCase())) {
                // Display the entire SMS Message
         /*       player.start();
                Log.d(TAG, str);*/
                Toast.makeText(context, str.toString(), Toast.LENGTH_SHORT).show();
                AudioManager ring  = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int streamMaxVolume = ring.getStreamMaxVolume(AudioManager.STREAM_RING);
                ring.setStreamVolume(AudioManager.STREAM_RING,streamMaxVolume,AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
                //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                //MediaPlayer mediaPlayer = MediaPlayer.create(context, notification);
                //mediaPlayer.start();
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Ringtone ringtone = RingtoneManager.getRingtone(context,uri);
                ringtone.play();
                //    ring.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 20, 0);
                //ring.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                //Intent stopSoundIntent = new Intent(context,StopSoundActivity.class);
                //context.startActivity(stopSoundIntent);
                //intent.putExtra("MyMedPlay", (Parcelable) mediaPlayer);

                // to start stop screen activity intent

                /*   Intent launch_intent = new  Intent(context, MainActivity.class);
                launch_intent.setComponent(new ComponentName("com.cybersoft.malikahsan.findmymobile","com.cybersoft.malikahsan.findmymobile.StopSoundActivity"));
                launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP |intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                launch_intent.putExtra("some_data", "value");
                launch_intent.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(launch_intent);   */
            }
        }

    }
}