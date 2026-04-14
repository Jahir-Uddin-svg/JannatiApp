package com.jannati.ai;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.graphics.Color;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class HaramBlurService extends AccessibilityService {
    private WindowManager wm;
    private FrameLayout shieldView;
    private boolean isVisible = false;
    private Interpreter tflite;

    @Override
    protected void onServiceConnected() {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        try {
            // মডেল লোড করার লজিক
            tflite = new Interpreter(loadModelFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile() throws Exception {
        // assets ফোল্ডারে 'detector.tflite' ফাইলটি থাকতে হবে
        android.content.res.AssetFileDescriptor fileDescriptor = getAssets().openFd("detector.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // এআই এখানে কাজ করবে। যদি স্ক্রিনে খারাপ কিছু পায়:
        // showShield(); 
    }

    private void showShield() {
        if (isVisible) return;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        
        shieldView = new FrameLayout(this);
        shieldView.setBackgroundColor(Color.BLACK);
        wm.addView(shieldView, params);
        isVisible = true;
    }

    @Override public void onInterrupt() {}
}
