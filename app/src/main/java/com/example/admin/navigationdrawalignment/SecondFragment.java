package com.example.admin.navigationdrawalignment;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Admin on 8/4/2017.
 */

public class SecondFragment extends Fragment {
    static MediaPlayer mp;
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);

        Button Play = (Button)myView.findViewById(R.id.button_Play);
        Button Pause = (Button)myView.findViewById(R.id.button_Pause);
        Button Stop = (Button)myView.findViewById(R.id.button_Stop);

        if(mp!=null && !mp.isPlaying()) {
            mp = MediaPlayer.create(getActivity(), R.raw.music1);
        }

        Play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.music1);
                    mp.start();
                }
                if(!mp.isPlaying()){
                    mp.start();
                }

            }
        });



        Pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mp != null)
                    if(mp.isPlaying())
                        mp.pause();            }
        });

        Stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    mp.stop();
                    mp.release();
                    mp = null;
                }
            }
        });

        return myView;


    }


}
