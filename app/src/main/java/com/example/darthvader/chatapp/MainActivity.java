package com.example.darthvader.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Message;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RoomListener {

    String channelID="7YTqadEw81kSBkZC";
    String room="observable-room";
    EditText editText;
    Scaledrone scaledrone;
    private MemberAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.messageText);
        adapter=new MemberAdapter(this);

        listView=(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);
        MemberData data=new MemberData(getRandomName(),getRandomColor());

        scaledrone=new Scaledrone(channelID,data);

        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                Log.e("Open", "onOpen: " );
                scaledrone.subscribe(room,MainActivity.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                Log.e("OpenF", "onOpen: " +ex.toString());

                System.err.print(ex);


            }

            @Override
            public void onFailure(Exception ex) {
                Log.e("OpenFF", "onOpen: " +ex.toString());
                System.err.print(ex);


            }

            @Override
            public void onClosed(String reason) {
                System.err.print(reason);


            }
        });


    }

    @Override
    public void onOpen(Room room) {
        Log.e("Open", "onOpen: " );
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.print(ex);
    }

    @Override
    public void onMessage(Room room, Message message) {
        final ObjectMapper mapper=new ObjectMapper();

        try {
            final MemberData data=mapper.treeToValue(message.getMember().getClientData(),MemberData.class);
            boolean belongsToCurrentUser=message.getClientID().equals(scaledrone.getClientID());
            final com.example.darthvader.chatapp.Message message1=new com.example.darthvader.chatapp.Message(message.getData().asText(),data,belongsToCurrentUser);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run:");
                    adapter.add(message1);
                    listView.setSelection(listView.getCount()-1);
                }
            });
        } catch (JsonProcessingException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }


    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    public void sendMessage(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        String message=editText.getText().toString();
        if(message.length()>0){
            scaledrone.publish(room,message);
            editText.getText().clear();

        }

    }
}
