package com.jannati.ai;

import android.graphics.*;
import android.media.Image;

public class JannatiDetector {
    // পিনপয়েন্ট একুরেসির জন্য থ্রেশহোল্ড কমিয়ে ০.২০ করা হলো
    private static final float CONFIDENCE_THRESHOLD = 0.20f;
    private static final float PADDING = 70.0f; // বডি পার্টস কভার করার জন্য এক্সট্রা প্যাডিং

    public Bitmap processFrame(Bitmap inputFrame, List<Recognition> results) {
        Bitmap output = inputFrame.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(output);
        Paint blurPaint = new Paint();
        
        // ব্লার ইফেক্টের বদলে সলিড কালার ব্যবহার করছি যাতে সিকিউরিটি ১০০% হয়
        blurPaint.setColor(Color.parseColor("#1A1A1A")); 
        blurPaint.setStyle(Paint.Style.FILL);

        for (Recognition res : results) {
            // শুধু 'person' বা 'human' ক্লাস ডিটেক্ট হলে কাজ করবে
            if (res.getTitle().equals("person") && res.getConfidence() > CONFIDENCE_THRESHOLD) {
                RectF location = res.getLocation();
                
                // পিনপয়েন্ট বক্স ক্যালকুলেশন উইথ প্যাডিং
                RectF blurredRect = new RectF(
                    location.left - PADDING,
                    location.top - PADDING,
                    location.right + PADDING,
                    location.bottom + PADDING
                );
                
                // স্ক্রিনে ড্র করা
                canvas.drawRect(blurredRect, blurPaint);
            }
        }
        return output;
    }
