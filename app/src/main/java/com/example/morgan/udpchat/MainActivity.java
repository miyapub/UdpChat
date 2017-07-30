package com.example.morgan.udpchat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] ipArray=(new GetIP().getAllIp());//本机ip段的全部ip地址

        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(7777);
                    while (true) {
                        //byte data[] = new byte[1024];
                        byte data[] = new byte[32];
                        DatagramPacket packet = new DatagramPacket(data, data.length);
                        try {
                            socket.receive(packet);
                            final String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
                            //
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable(){
                                        @Override
                                        public void run() {
                                            //更新UI
                                            TextView msg=((TextView)findViewById(R.id.msg));
                                            String m=msg.getText().toString();
                                            msg.setText(result+"\n"+m);
                                        }
                                    });
                                }
                            }).start();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText edit=(EditText)findViewById(R.id.input);
                        String word=edit.getText().toString();

                        hide_soft_input(view);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                EditText edit=(EditText)findViewById(R.id.input);
                                edit.setText("");
                            }
                        });
                        for (int i = 0; i < ipArray.length; i++) {
                          (new UdpClient()).sendUdpMsg(ipArray[i],7777,word);
                        }
                    }
                }).start();
            }
        });
    }
    public void hide_soft_input(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        //imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);//强制显示键盘
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
