package com.mycameratry02;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.google.zxing.Result;
import com.google.zxing.common.StringUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ScanActivity extends Activity implements Callback{
	private Camera myCamera;
	private Point screenResolution;
	private Point cameraResolution;
	
	
	private CameraPreview myView;
	private Button backButton,captureButton;
	private ToggleButton flashButton;
	private int size=30;//扫描矩形范围
	
	private static int orientMode;
	private PictureCallback myPicture = new PictureCallback() {
		@Override
	    public void onPictureTaken(byte[] data, Camera camera) {
			//将图片保存至相册
			File pictureFile = getOutputPicFile();
	        if (pictureFile == null){
	            Log.d("storage_profit", "Error creating media file, check storage permissions");
	            return;
	        }
	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d("storage_profit", "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d("storage_profit", "Error accessing file: " + e.getMessage());
	        }
	        
	        
	        camera.startPreview();
	    }
//				   //将图片保存至相册
//			       if (orientMode ==Configuration.ORIENTATION_PORTRAIT) {
//			    	   System.out.println("竖屏  竖屏  竖屏  竖屏  竖屏  竖屏  ");
//			    	   ContentResolver resolver = getContentResolver();
//			    	   Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//			    	   Matrix matrix=new Matrix();
//			    	   matrix.setRotate(90);
//			    	   Bitmap bitmap2=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//			           MediaStore.Images.Media.insertImage(resolver, bitmap2, "xxoo", "des");
//				}else {
//					System.out.println("横屏 横屏 横屏 横屏 横屏 横屏 ");
//					ContentResolver resolver = getContentResolver();
//					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//				    MediaStore.Images.Media.insertImage(resolver, bitmap, "xxoo", "des");
//				}
//			       //mSensorManager.unregisterListener(mAccSensorListener);
//				   //拍照后重新开始预览
//				   camera.startPreview();
//				}
	};
	private PictureCallback myPicture2 = new PictureCallback() {
	    
		@Override
	    public void onPictureTaken(byte[] data, Camera camera) {
	    //将图片扫描并回传数据
	    	
	    	Log.i("data", "scan_start");
	    	Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
	    	
	    	//计算平均值
	    	int average=averageBitmap(bitmap,bitmap.getWidth()/2-size,bitmap.getHeight()/2-size,size,size);     
	    	Log.i("data", "BitmapSize is " + bitmap.getHeight()+'*'+bitmap.getWidth());
	    	Log.i("data", "CenterPixel is " + Integer.toHexString(bitmap.getPixel(bitmap.getHeight()/2,bitmap.getWidth()/2)));
	    	
	    	
	    	//保存
	    	Bitmap bitmap2=Bitmap.createBitmap(bitmap,bitmap.getWidth()/2-size,bitmap.getHeight()/2-size,size,size);
	    	
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	    	bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos);    
	    	File pictureFile = getOutputPicFile();
	        if (pictureFile == null){
	            Log.d("storage_profit", "Error creating media file, check storage permissions");
	            return;
	        }
	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(baos.toByteArray());
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d("storage_profit", "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d("storage_profit", "Error accessing file: " + e.getMessage());
	        }
	    	
	    	//new intent to access class MainActivity
		
	    	Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("dataPixel", average);
			intent.putExtras(bundle);
			ScanActivity.this.setResult(RESULT_OK, intent);
			ScanActivity.this.finish();
	    }
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		//get orientation as MainActivity decided cameras
		Bundle data = getIntent().getExtras();
		orientMode = data.getInt("orientSub");
		
		//landscape open back_camera, portrait open facing_camera
		if (orientMode == Configuration.ORIENTATION_LANDSCAPE){
			myCamera=getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK); // 横屏背摄像头
		}
		else if(orientMode ==Configuration.ORIENTATION_PORTRAIT) {
			myCamera=getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT); // 竖屏前摄像头
		}

		myView = new CameraPreview(this, myCamera);
		
		backButton = (Button) this.findViewById(R.id.button_back);
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ScanActivity.this.finish();
				
			}
		});
		captureButton = (Button) findViewById(R.id.capture);
		captureButton.setOnClickListener(new OnClickListener(){
		        @Override
		    public void onClick(View v) {
		         // get an image from the camera
		        myCamera.takePicture(null, null, myPicture2);
		        
		    }
		    
		});
		
		flashButton=(ToggleButton)findViewById(R.id.flash_open);
		flashButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            	flashButton.setChecked(isChecked);
                if (isChecked){
                	Camera.Parameters parameters=myCamera.getParameters();
                	parameters.setFlashMode(Parameters.FLASH_MODE_TORCH); 
                	myCamera.setParameters(parameters);
                }else{
                	Camera.Parameters parameters=myCamera.getParameters();
                	parameters.setFlashMode(Parameters.FLASH_MODE_OFF); 
                	myCamera.setParameters(parameters);
                }
            }

        });
		 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initFromCameraParameters(myCamera);
		setDesiredCameraParameters(myCamera);

		myCamera.setFaceDetectionListener(new MyFaceDetectionListener());
	     // start face detection only *after* preview has started
        if (myCamera.getParameters().getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            myCamera.startFaceDetection();
        }
		myView = new CameraPreview(this, myCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.preview_view);
		preview.addView(myView);
		
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        myCamera.release();        // release the camera for other applications
        myCamera = null;
    }
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		if(myCamera != null) {  
            //if working, stop 
            myCamera.stopPreview();           
            myCamera.release();  
        }
	}


	
	
	//to get an instance of the Camera object
	public static Camera getCameraInstance(int type){
	    int cameraCount = 0;
	    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
	    cameraCount = Camera.getNumberOfCameras(); // get cameras number  
	            
	    for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
	        Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
	        if ( cameraInfo.facing ==type ) { // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
	            try {              
	                return Camera.open( camIdx );  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	        }
	    }
		return null;
	}

	void initFromCameraParameters(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
	    
		int previewFormat = parameters.getPreviewFormat();
	    String previewFormatString = parameters.get("preview-format");
	    Log.i("camera_profit", "Default preview format: " + previewFormat + '/' + previewFormatString);
	    
	    int pictureFormat = parameters.getPictureFormat();
	    String pictureFormatString = parameters.get("picture-format");
	    Log.i("camera_profit", "Default picture format: " + pictureFormat + '/' + pictureFormatString);
	    
	    List<Integer> supportedPicFormatList = parameters.getSupportedPictureFormats();  
	    String supportedPicFormatString="";
	    for(int num = 0; num < supportedPicFormatList.size(); num++)  
        {  
            Integer ISupPicFmt = supportedPicFormatList.get(num);
            supportedPicFormatString=supportedPicFormatString+ISupPicFmt.intValue()+'/';      
        }
	    Log.i("camera_profit", "Supported Picutre Format: "+ supportedPicFormatString); 
	    
	    WindowManager manager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
	    //set picture/screen size to default
	    Display display = manager.getDefaultDisplay();
	    screenResolution = new Point(display.getWidth(), display.getHeight());
//		//manual best picture size find 
//	    screenResolution = findBestPictureSize(parameters);
	    Log.i("camera_profit", "Screen resolution: " + screenResolution);

	    Point screenResolutionForCamera = new Point();   
	    if (screenResolution.x < screenResolution.y) {  
	         screenResolutionForCamera.x = screenResolution.y;  
	         screenResolutionForCamera.y = screenResolution.x;
	    }else{
		    screenResolutionForCamera.x = screenResolution.x;   
		    screenResolutionForCamera.y = screenResolution.y;
	    }
	    cameraResolution = getCameraResolution(parameters, screenResolutionForCamera);
	    Log.i("camera_profit", "Camera resolution: " + screenResolution);
	    
	  }
	
	void setDesiredCameraParameters(Camera camera) {
	    Camera.Parameters parameters = camera.getParameters();
	    Log.d("camera_profit", "Setting preview size: " + cameraResolution);
	    parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
	    //picture format set
	    parameters.setPictureFormat(PixelFormat.JPEG); 
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);
	  
	}
	
	private Point findBestPictureSize(Camera.Parameters parameters) {
        int  diff = Integer.MIN_VALUE;
        String pictureSizeValueString = parameters.get("picture-size-values");
             
         // saw this on Xperia
         if (pictureSizeValueString == null) {
             pictureSizeValueString = parameters.get("picture-size-value");
         }
             
         if(pictureSizeValueString == null) {
             return  new Point(screenResolution.x,screenResolution.y);
         }
             
         Log.d("picture_profit", "pictureSizeValueString : " + pictureSizeValueString);
         int bestX = 0;
         int bestY = 0;
            
            
         for(String pictureSizeString : Pattern.compile(",").split(pictureSizeValueString))
         {
             pictureSizeString = pictureSizeString.trim();
                 
             int dimPosition = pictureSizeString.indexOf('x');
             if(dimPosition == -1){
                 Log.e("picture_profit", "Bad pictureSizeString:"+pictureSizeString);
                 continue;
             }
                 
             int newX = 0;
             int newY = 0;
                 
             try{
                 newX = Integer.parseInt(pictureSizeString.substring(0, dimPosition));
                 newY = Integer.parseInt(pictureSizeString.substring(dimPosition+1));
             }catch(NumberFormatException e){
                 Log.e("picture_profit", "Bad pictureSizeString:"+pictureSizeString);
                 continue;
             }
                
             Point sResolution = new Point (screenResolution.x,screenResolution.y);
                 
             int newDiff = Math.abs(newX - sResolution.x)+Math.abs(newY- sResolution.y);
                 if(newDiff == diff)
                 {
                     bestX = newX;
                     bestY = newY;
                     break;
                 } else if(newDiff > diff){
                     if((3 * newX) == (4 * newY)) {
                         bestX = newX;
                         bestY = newY;
                         diff = newDiff;
                     }
                 }
             }
                 
         if (bestX > 0 && bestY > 0) {
            return new Point(bestX, bestY);
         }
        return null;
    }

	private static Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {

	    String previewSizeValueString = parameters.get("preview-size-values");
	    // saw this on Xperia
	    if (previewSizeValueString == null) {
	      previewSizeValueString = parameters.get("preview-size-value");
	    }

	    Point cameraResolution = null;

	    if (previewSizeValueString != null) {
	      Log.d("camera_profit", "preview-size-values parameter: " + previewSizeValueString);
	      cameraResolution = findBestPreviewSizeValue(previewSizeValueString, screenResolution);
	    }

	    if (cameraResolution == null) {
	      // Ensure that the camera resolution is a multiple of 8, as the screen may not be.
	      cameraResolution = new Point(
	          (screenResolution.x >> 3) << 3,
	          (screenResolution.y >> 3) << 3);
	    }

	    return cameraResolution;
	  }

	  private static Point findBestPreviewSizeValue(CharSequence previewSizeValueString, Point screenResolution) {
	    int bestX = 0;
	    int bestY = 0;
	    int diff = Integer.MAX_VALUE;
	    for (String previewSize : Pattern.compile(",").split(previewSizeValueString)) {

	      previewSize = previewSize.trim();
	      int dimPosition = previewSize.indexOf('x');
	      if (dimPosition < 0) {
	        Log.w("camera_profit", "Bad preview-size: " + previewSize);
	        continue;
	      }

	      int newX;
	      int newY;
	      try {
	        newX = Integer.parseInt(previewSize.substring(0, dimPosition));
	        newY = Integer.parseInt(previewSize.substring(dimPosition + 1));
	      } catch (NumberFormatException nfe) {
	        Log.w("camera_profit", "Bad preview-size: " + previewSize);
	        continue;
	      }

	      int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
	      if (newDiff == 0) {
	        bestX = newX;
	        bestY = newY;
	        break;
	      } else if (newDiff < diff) {
	        bestX = newX;
	        bestY = newY;
	        diff = newDiff;
	      }

	    }

	    if (bestX > 0 && bestY > 0) {
	      return new Point(bestX, bestY);
	    }
	    return null;
	  }

	  

	/** Create a File for saving an image */
	static File getOutputPicFile(){
		
		// Get store-path
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	    		Environment.DIRECTORY_PICTURES), "MyCameraApp");

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
//	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    Log.i("Storage Path", "at: "+mediaStorageDir.getPath());
	    
	    // Store picture to file
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "temp.jpg");
	    return mediaFile;
	}
	
	public void handleDecode(Result obj, Bitmap barcode) {
		String code = obj.getText();
		if(code == ""){
			Toast.makeText(ScanActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", code);
			intent.putExtra("bundle", bundle);
			this.setResult(RESULT_OK, intent);
		}
		ScanActivity.this.finish();
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(myCamera != null) {  
            //if working, stop 
            myCamera.stopPreview();           
            myCamera.release();  
        }
	}

	public int averageBitmap(Bitmap bitmat,int x,int y,int image_width,int image_height){
		int i,j;
		long rDataWhole=0;
		long gDataWhole=0;
		long bDataWhole=0;
		int n=image_width*image_height;
		Bitmap bitmaptemp = Bitmap.createBitmap(bitmat, x, y, image_width, image_height);
		for (i=0;i<image_width;i++)
			for (j=0;j<image_height;j++){
				int pixel=bitmaptemp.getPixel(i,j);
				int rData=(pixel&0xff0000)/0x10000;
				int gData=(pixel&0xff00)/0x100;
				int bData=pixel&0xff;
	          	rDataWhole+=rData;
	          	gDataWhole+=gData;
	          	bDataWhole+=bData;
			}
		rDataWhole=Math.round(rDataWhole*1.0/n);
		gDataWhole=Math.round(gDataWhole*1.0/n);
		bDataWhole=Math.round(bDataWhole*1.0/n);
		return (int) ((rDataWhole*0x10000)+(gDataWhole*0x100)+bDataWhole);
	}

	

}
