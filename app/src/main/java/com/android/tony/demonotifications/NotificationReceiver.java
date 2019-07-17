package com.android.tony.demonotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.RemoteInput;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remote = RemoteInput.getResultsFromIntent(intent);
        if(remote!=null)
        {
            CharSequence charSequence = remote.getCharSequence("Remote");
            Toast.makeText(context,String.valueOf(charSequence),Toast.LENGTH_SHORT).show();
        }
    }


}
