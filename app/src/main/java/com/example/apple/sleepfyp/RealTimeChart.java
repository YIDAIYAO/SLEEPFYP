package com.example.apple.sleepfyp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.lang.Double;

//import com.jjoe64.graphview.GraphViewStyle;

public class RealTimeChart extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog pd;
    ConnectedThread connectedThread;
    public final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    protected final int SUCCESS_CONNECT = 0;
    protected final int FAILED_CONNECT = 1;
    protected static final int MESSAGE_READ = 2;
    protected final int AUTO = 3;
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private String fileName;




    @Override
    public void onBackPressed() {
        if (connectedThread != null) connectedThread.write("Q");
        super.onBackPressed();
    }
    //add handler


    //mHandler mHandler;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SUCCESS_CONNECT:

                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();

                    connectedThread = new ConnectedThread((BluetoothSocket) msg.obj);
                    connectedThread.start();

                    break;

                case FAILED_CONNECT:

                    pd.dismiss();
                    Toast.makeText(RealTimeChart.this, "Failed!", Toast.LENGTH_SHORT).show();

                    break;

                case MESSAGE_READ:


                    final byte[] readBuf = (byte[]) msg.obj;
                   // String strIncom = new String(readBuf, 0, msg.arg1);
                    Bundle data=msg.getData();
                    String strIncom=data.getString("BTdata");
                    strIncom = strIncom.replace("s", "");
                    //strIncom = strIncom.replace("ss", "");
                    strIncom = strIncom.replace(" ", "");
                    strIncom = strIncom.replace("\n", "");
                    //strIncom = strIncom.replace(",", "");
                    //strIncom = strIncom.replace("BPM: ", "");
                    if (!TextUtils.isEmpty(strIncom)&&TextUtils.isDigitsOnly(strIncom)) {

                        Series.appendData(new GraphViewData(graph2LastXValue, Double.parseDouble(strIncom)), AutoScrollX);
                        if (graph2LastXValue >= Xview && Lock == true) {
                            Series.resetData(new GraphViewData[]{});
                            graph2LastXValue = 0;
                        } else graph2LastXValue += 0.1;
                        if (Lock == true) graphView.setViewPort(0, Xview);
                        else graphView.setViewPort(graph2LastXValue, Xview);
                        //refresh
                        GraphView.removeView(graphView);
                        GraphView.addView(graphView);

                        final TextView TXV1 = findViewById(R.id.TXV1);
                        //Button VR = findViewById(R.id.VR);

                        final double[] AA = new double[500];

                       /* int i;
                        for (i = 0; i < 10; i++) {
                            final double a = Double.parseDouble(strIncom);
                            AA[i] = a;
                            //CharSequence cs1 = String.valueOf(i);
                            CharSequence cs2 = String.valueOf(AA[i]);
                            TXV1.setText(cs2);
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                    }*/

                        TXV1.setText(strIncom.toString());
                        //TXV1.setText(strIncom.toString());
                        Intent intent=getIntent();
                        String from=intent.getStringExtra("from");

                       /* VR.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int j;
                                for(j=0;j<500;j++) {
                                    //CharSequence cs = String.valueOf(HB(AA));
                                    //CharSequence cs4 = String.valueOf(AA);
                                   // TXV1.setText(cs4);
                                    //connectedThread.write("SPO2");
                                    //TXV1.setText(cs4);
                                    }
                            }
                        });*/
                        switch(from){
                            case "SPO2":
                               // TXV.setText("SPO2"+"\n");
                               // connectedThread.write("SPO2");
                                break;
                            case "Heart Rate":
                                //TXV.setText("Heart Rate"+"\n");
                                //connectedThread.write("HR");
                               // ReadHeartRateValue();
                               /* VR.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int j;
                                        for(j=0;j<500;j++) {
                                            CharSequence cs = String.valueOf(HB(AA));
                                            TXV1.setText(cs);
                                        }
                                    }
                                });*/
                                break;
                            case "Respiratory Rate":
                                //TXV.setText("Respiratory Rate"+"\n");
                               // connectedThread.write("RR");
                               // ReadRespRateValue();
                                break;
                        }
                        //intent.setClass(RealTimeChart.this,FFT.class);
                        //String text= strIncom.toString();
                        //intent.putExtra("RowData",text);
                        //startActivity(intent);
                       // int inncome = ConvertUtil.convertToInt("strIncom",AA);
                        //TXV1.setText(strIncom.toString());

                    }
                    saveDataToFile(strIncom, fileName);
                    break;

            }
        }

        //add is float method
        public boolean isFloatNumber(String num) {
            try {
                Double.parseDouble(num);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

    };

    // private static final
    Button bConnect, bDisconnect, bXplus, bXmius,Start,VR;
    ToggleButton tblock, tbScroll, tbStream;
    static boolean Lock, AutoScrollX, Stream;

    //graph init
    static LinearLayout GraphView;
    static GraphView graphView;
    static GraphViewSeries Series;
    private static double graph2LastXValue = 0;
    private static int Xview = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////landcape hid title and status bar
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_real_time_chart);
        ////////////change background color
        LinearLayout background = (LinearLayout) findViewById(R.id.bg);
        background.setBackgroundColor(Color.BLACK);
        init();
        Bottoninit();
        TextView TXV=findViewById(R.id.TXV);
        Intent intent=getIntent();
        String from=intent.getStringExtra("from");
        TXV.setText(from);
        final String QWE=from;
        VR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(QWE){
                    case "SPO2":
                        //TXV.setText("SPO2"+"\n");
                        // for(int i=0;i<10;i++){
                        if (connectedThread != null)connectedThread.write("4");
                          /* try {
                                Thread.sleep(40);
                           } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }*/
                        break;
                    case "Heart Rate":
                        // TXV.setText("Heart Rate"+"\n");
                        // for(int i=0;i<500;i++){
                        if (connectedThread != null)connectedThread.write("5");
                            /*try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }*/
                        // ReadHeartRateValue();

                        break;
                    case "Respiratory Rate":
                        //TXV.setText("Respiratory Rate"+"\n");
                        if (connectedThread != null)connectedThread.write("6");
                        // ReadRespRateValue();
                        break;

                }

            }
        });
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(QWE){
                    case "SPO2":
                        //TXV.setText("SPO2"+"\n");
                       // for(int i=0;i<10;i++){
                        if (connectedThread != null)connectedThread.write("1");
                          /* try {
                                Thread.sleep(40);
                           } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }*/
                        break;
                    case "Heart Rate":
                        // TXV.setText("Heart Rate"+"\n");
                       // for(int i=0;i<500;i++){
                            if (connectedThread != null)connectedThread.write("2");
                            /*try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }*/
                        // ReadHeartRateValue();

                        break;
                    case "Respiratory Rate":
                        //TXV.setText("Respiratory Rate"+"\n");
                        if (connectedThread != null)connectedThread.write("3");
                        // ReadRespRateValue();
                        break;

                }
                //if (connectedThread != null)connectedThread.write("1");
            }
        });




    }


    public void updateTitle()
    {

    }


    public void saveDataToFile(String strIncom, String fileName) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            /**
             * "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
             * 还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
             */
            fileOutputStream = openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(strIncom);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    void init() {
        fileName = System.currentTimeMillis() + ".txt";
        GraphView = (LinearLayout) findViewById(R.id.Graph);
        Series = new GraphViewSeries("Signal",
                new GraphViewStyle(Color.YELLOW, 2),
                new GraphViewData[]{new GraphViewData(0, 0)});
        graphView = new LineGraphView(this, "Graph");
        graphView.setViewPort(0, Xview);
        graphView.setScrollable(true);
        graphView.setScalable(true);
        graphView.setShowLegend(true);
        graphView.setLegendAlign(LegendAlign.BOTTOM);
        graphView.setManualYAxis(true);
        graphView.setManualYAxisBounds(550, -100);
        graphView.addSeries(Series);
        GraphView.addView(graphView);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);


    }

    void Bottoninit() {
        //initialize buttons
        bConnect = (Button) findViewById(R.id.bConnect);
        bConnect.setOnClickListener(this);
        bDisconnect = (Button) findViewById(R.id.bDisconnect);
        bDisconnect.setOnClickListener(this);
        bXplus = (Button) findViewById(R.id.bXplus);
        bXplus.setOnClickListener(this);
        bXmius = (Button) findViewById(R.id.bXmius);
        bXmius.setOnClickListener(this);

        tblock = (ToggleButton) findViewById(R.id.tblock);
        tblock.setOnClickListener(this);
        tbScroll = (ToggleButton) findViewById(R.id.tbScroll);
        tbScroll.setOnClickListener(this);
        tbStream = (ToggleButton) findViewById(R.id.tbStream);
        tbStream.setOnClickListener(this);
        Start=(Button)findViewById(R.id.Start);
        VR=(Button)findViewById(R.id.VR);
        //Start.setOnClickListener(this);
        TextView TXV=findViewById(R.id.TXV);
        Lock = true;
        AutoScrollX = true;
        Stream = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bConnect:
                startActivityForResult(new Intent(RealTimeChart.this, Bluetooth.class), 1024);
                break;
            case R.id.bDisconnect:
                disconnected();
                break;
            case R.id.bXplus:
                if (Xview < 60) Xview++;
                break;
            case R.id.bXmius:
                if (Xview > 1) Xview--;
                break;
            case R.id.tblock:
                if (tblock.isChecked()) {
                    Lock = true;
                } else {
                    Lock = false;
                }
                break;
            case R.id.tbScroll:
                if (tbScroll.isChecked()) {
                    AutoScrollX = true;
                } else {
                    AutoScrollX = false;
                }
                break;
            case R.id.tbStream:
                if (tbStream.isChecked()) {
                    if (connectedThread != null) connectedThread.write("E");
                } else {
                    if (connectedThread != null) connectedThread.write("Q");
                }
                break;
        }

    }

    public void disconnected() {
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;

        }
    }


    class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            if (btAdapter.isDiscovering())
                btAdapter.cancelDiscovery();
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                mmSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
        }

        public void run() {
            // Cancel discovery because it will slow down the connection

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                mHandler.obtainMessage(FAILED_CONNECT, mmSocket).sendToTarget();
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }

        }

        /**
         * Will cancel an in-progress connection, and close the socket
         */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }


    class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            //ChooseFunction();
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            String readMessage;


            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    int availableBytes = mmInStream.available();
                    // Read from the InputStream
                    if (availableBytes > 0) {
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                        Message msg=new Message();
                        Bundle data = new Bundle();
                        readMessage = new String(buffer,0,bytes);
                        data.putString("BTdata",readMessage);
                        msg.what = RealTimeChart.MESSAGE_READ;
                        msg.setData(data);
                        mHandler.sendMessage(msg);


                    //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                           // .sendToTarget();

                    }
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String income) {
            try {
                mmOutStream.write(income.getBytes());

            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1024 && data != null) {

            pd.show();
            BluetoothDevice selectedDevice = data.getParcelableExtra("device");
            ConnectThread connect = new ConnectThread(selectedDevice);
            connect.start();
        }
    }

    public int HB(double[] readBuf){
        int i=1;
        int j=0;
        double max;
        int Beat=28;
        int peak=0;

        for(i=0;i<499;i++){
            max=readBuf[i];
            if(readBuf[i+1]>max)
            {
                max=readBuf[i+1];
                if(readBuf[i+2]<max && readBuf[i+2]>400){
                    peak++;

                }
            }


            if(i%499==0){
                j++;
                Beat=peak*3;
                i=1;
                }
                if(j==1){break;}
        }
        return Beat;
    }



}
