package com.mycameratry02;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView textView01;
	private TextView textView02;
	private TextView textView03;
	private TextView textView04;
	
	private TextView resultText01;
	private TextView resultText02;
	private TextView resultText03;
	private TextView resultText04;
	private Button button,button01,button02,button03,button04;
	private static int dataPixel=0xFFFFFF;
	private static int dataPixelR=0xFFFFFF;
	private static int state;
	private static String name;
	private static boolean initial=true;
	public final int BLACKCLIBERMODE=0;
	public final int WHITECLIBERMODE=1;
	public final int SCANMODE=2;
	
	
	
	
	public static int upThread=0x000000;
	public static int downThread=0xFFFFFF;

	private final static int SCANNIN_GREQUEST_CODE = 1;//whole enable of scanning
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//open auto orientation view
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			Log.i("info","landscape"); // 横屏
		}else if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
			Log.i("info","portrait"); // 竖屏
		}
		//show camera available
		textView01=(TextView) findViewById(R.id.textView01);
		textView01.setText("Camera if available: "+checkCameraHardware(getApplication()));
		//show camera number
		textView02=(TextView) findViewById(R.id.textView02);
		textView02.setText("Camera Numbers: "+Camera.getNumberOfCameras());
		

		resultText01 = (TextView)findViewById(R.id.result01);
		resultText01.setBackgroundColor(0xFF000000+dataPixel);
		int r=(dataPixel&0xff0000)/0x10000;
		int g=(dataPixel&0xff00)/0x100;
		int b=dataPixel&0xff;
		if (r<0x40 && g<0x40 && b<0x40)
			resultText01.setTextColor(Color.WHITE);
		if (!(upThread==0x000000 && downThread==0xFFFFFF)){
			resultText03 = (TextView)findViewById(R.id.result03);
		
			resultText03.setBackgroundColor(0xFF000000+dataPixelR);
			r=(dataPixelR&0xff0000)/0x10000;
			g=(dataPixelR&0xff00)/0x100;
			b=dataPixelR&0xff;
			if (r<0x40 && g<0x40 && b<0x40)
				resultText03.setTextColor(Color.WHITE);
		}
		
		 //获得SharedPreferences实例  
        SharedPreferences sp = getSharedPreferences("temp_info", MODE_WORLD_READABLE);
		if(initial){
			initial=false;
			SharedPreferences.Editor editor = getSharedPreferences("temp_info", MODE_WORLD_WRITEABLE).edit();  
	        //将EditText中的文本内容添加到编辑器  
	        editor.putString("textView03String", "0x000000");
	        editor.putString("textView04String", "0xFFFFFF");
	        editor.putString("resultText01String", "256 8-bit data");
	        editor.putString("resultText02String", "Color");  
	        editor.putString("resultText03String", "");
	        editor.putString("resultText04String", "");  
	        
	        //提交编辑器内容  
	        editor.commit();
		}
		  
        //从SharedPreferences获得备忘录的内容 ，并在EditText中显示备忘录内容  

        textView03 = (TextView)findViewById(R.id.textView03);
		textView03.setText(sp.getString("textView03String", ""));

		textView04 = (TextView)findViewById(R.id.textView04);
		textView04.setText(sp.getString("textView04String", ""));

		resultText01 = (TextView)findViewById(R.id.result01);
		resultText01.setText(sp.getString("resultText01String", "Data"));
		
		resultText02 = (TextView)findViewById(R.id.result02);
		resultText02.setText(sp.getString("resultText02String", "Color"));
		
		resultText03 = (TextView)findViewById(R.id.result03);
		resultText03.setText(sp.getString("resultText03String", "DataR"));
		
		resultText04 = (TextView)findViewById(R.id.result04);
		resultText04.setText(sp.getString("resultText04String", "ColorR"));
		
		//reset thread-hold
		button03 = (Button) this.findViewById(R.id.button03);
		button03.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				upThread=0x000000;
				downThread=0xFFFFFF;
				dataPixelR=dataPixel;
				textView03.setText("0x000000");
				textView04.setText("0xFFFFFF");
				textView03.setTextColor(Color.BLACK);
				textView04.setTextColor(Color.BLACK);
				resultText03.setText("");
				resultText03.setBackgroundColor(Color.WHITE);
				resultText03.setTextColor(Color.BLACK);
				resultText04.setText("");
				
			}
		});
		

		button04 = (Button) this.findViewById(R.id.button04);
		button04.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
			    		Environment.DIRECTORY_PICTURES), "MyCameraApp");
				String fsource = mediaStorageDir.getPath() + File.separator + "temp.jpg";
				String ftarget = mediaStorageDir.getPath() + File.separator + name +".jpg";
				copyFile(fsource, ftarget);
				Log.i("info","save succeed");
			}
		});
		//ensure camera enable
		if (!checkCameraHardware(MainActivity.this))
		{//if no available camera, text color to red
			
			textView01.setTextColor(Color.RED);
			textView02.setTextColor(Color.RED);
		}
		//ensure upThread<downThread
		if (upThread>=downThread){
			textView03.setTextColor(Color.RED);
			textView04.setTextColor(Color.RED);
		}
		if (checkCameraHardware(MainActivity.this) && upThread<downThread){//if yes, able to access scanActivity

			button = (Button) this.findViewById(R.id.button);
			button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//new intent to access class ScanActivity
					state=SCANMODE;
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ScanActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					
					Bundle data = new Bundle();
					data.putInt("orientSub",MainActivity.this.getResources().getConfiguration().orientation);
					intent.putExtras(data);
					//process to the ScanActivity
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
					
				}
			});
			button01 = (Button) this.findViewById(R.id.button01);
			button01.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//new intent to access class ScanActivity
					state=BLACKCLIBERMODE;
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ScanActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					
					Bundle data = new Bundle();
					data.putInt("orientSub",MainActivity.this.getResources().getConfiguration().orientation);
					data.putInt("state",state);
					intent.putExtras(data);
					//process to the ScanActivity
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
					
				}
			});
			
			button02 = (Button) this.findViewById(R.id.button02);
			button02.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//new intent to access class ScanActivity
					state=WHITECLIBERMODE;
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ScanActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					
					Bundle data = new Bundle();
					data.putInt("orientSub",MainActivity.this.getResources().getConfiguration().orientation);
					data.putInt("state",state);
					intent.putExtras(data);
					//process to the ScanActivity
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
					
				}
			});
			
		}
		
		
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = getSharedPreferences("temp_info", MODE_WORLD_WRITEABLE).edit();  
        //将EditText中的文本内容添加到编辑器  
        editor.putString("textView03String", textView03.getText().toString());
        editor.putString("textView04String", textView04.getText().toString());
        editor.putString("resultText01String", resultText01.getText().toString());
        editor.putString("resultText02String", resultText02.getText().toString()); 
        editor.putString("resultText03String", resultText03.getText().toString());
        editor.putString("resultText04String", resultText04.getText().toString()); 
        //提交编辑器内容  
        editor.commit();
	}

	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode)
		{
		case RESULT_OK:
			Bundle bundle = data.getExtras();
			if (state==WHITECLIBERMODE){
				downThread=bundle.getInt("dataPixel");
				textView04 = (TextView)findViewById(R.id.textView04);
				textView04.setText("0x"+Integer.toHexString(downThread));
				if (upThread>=downThread){
					textView03.setTextColor(Color.RED);
					textView04.setTextColor(Color.RED);
				}
					
				
			}else if (state==BLACKCLIBERMODE){
				upThread=bundle.getInt("dataPixel");
				textView03 = (TextView)findViewById(R.id.textView03);
				textView03.setText("0x"+Integer.toHexString(upThread));
				if (upThread>=downThread){
					textView03.setTextColor(Color.RED);
					textView04.setTextColor(Color.RED);
				}
			}else{
				dataPixel = bundle.getInt("dataPixel");
				resultText01 = (TextView)findViewById(R.id.result01);
				resultText01.setText("scan_code: 0x"+Integer.toHexString(dataPixel));
				int r=(dataPixel&0xff0000)/0x10000;
				int g=(dataPixel&0xff00)/0x100;
				int b=dataPixel&0xff;
				resultText01.setBackgroundColor(0xFF000000+dataPixel);		
				if (r<0x40 && g<0x40 && b<0x40)
					resultText01.setTextColor(Color.WHITE);
				
				resultText02 = (TextView)findViewById(R.id.result02);
				name=ColorMap.decode(dataPixel);
				resultText02.setText("scan_color: "+name);
				resultText03 = (TextView)findViewById(R.id.result03);
				resultText04 = (TextView)findViewById(R.id.result04);
				
				if(downThread==0xFFFFFF && upThread==0x000000){
					dataPixelR=dataPixel;
					resultText03.setText("");
					resultText03.setBackgroundColor(Color.WHITE);
					resultText03.setTextColor(Color.BLACK);
					resultText04.setText("");	
				}else{
					dataPixelR=ColorMap.codeRescale(dataPixel,upThread,downThread);
					resultText03.setText("rescaled_code: 0x"+Integer.toHexString(dataPixelR));
					resultText03.setBackgroundColor(0xFF000000+dataPixelR);		
					r=(dataPixelR&0xff0000)/0x10000;
					g=(dataPixelR&0xff00)/0x100;
					b=dataPixelR&0xff;
					if (r<0x40 && g<0x40 && b<0x40)
						resultText03.setTextColor(Color.WHITE);
					resultText04.setText("rescaled_color: "+ColorMap.decode(dataPixelR));
				}
							
					
			}
			break;
		default:
			break;	
		}
	}
	
	
	
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	public void copyFile(String oldPath, String newPath) { 
		try { 
			int bytesum = 0; 
			int byteread = 0; 
			File oldfile = new File(oldPath); 
			if (oldfile.exists()) { //文件存在时 
				InputStream inStream = new FileInputStream(oldPath); //读入原文件 
				FileOutputStream fs = new FileOutputStream(newPath); 
				byte[] buffer = new byte[1444]; 
				while ( (byteread = inStream.read(buffer)) != -1) { 
					bytesum += byteread; //字节数 文件大小 
					System.out.println(bytesum); 
					fs.write(buffer, 0, byteread); 
				} 
				inStream.close(); 
			} 
		} 
		catch (Exception e) { 
			System.out.println("复制单个文件操作出错"); 
			e.printStackTrace(); 

		} 

	} 
	
	
	
	
	
}
