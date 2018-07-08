package com.upc.tfg.WifiMapBuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceView;
import android.view.View;

public class MyView extends View
{
    Paint paint = null;
    Canvas canvas;
    int x,y;
    public MyView(Context context, int x, int y)
    {
        super(context);
        paint = new Paint();
        this.x = x;
        this.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.canvas = canvas;
        int gx = getWidth();
        int gy = getHeight();
        int radius;
        radius = 20;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);
        //Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#4CAF50"));
        //canvas.drawRect(x/2,y/2,x/2,y/2,paint);
        //canvas.drawCircle(x, y, radius, paint);
        drawTriangle(canvas,paint,x,y,50);
        //canvas.drawPoint(x / 2, y / 2, paint);
    }

    protected void drawDot(int x, int y){
        int gx = getWidth();
        int gy = getHeight();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawPoint(x*2,y*2,paint);

    }

    public void drawTriangle(Canvas canvas, Paint paint, int x, int y, int width) {
        int halfWidth = width / 2;

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }
}