package com.android.internal.telephony;



/**
 * Created by anaswara on 29/4/17.
 */

public interface ITelephony  {
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();
}
