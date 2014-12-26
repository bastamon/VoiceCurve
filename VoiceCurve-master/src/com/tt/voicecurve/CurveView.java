package com.tt.voicecurve;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <���ܼ���> </Br> <������ϸ����> </Br>
 * 
 * @author Kyson
 */
public class CurveView extends View {
    /**
     * ����
     */
    private Paint mPaint;
    /**
     * ���Ƶ����ߵ�
     */
    private LinkedList<Point> mPoints;
    private float startX = 0;
    private float startY = 0;

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initView();
    }

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView();
    }

    public CurveView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    /**
     * ��ʼ������������Ϊ�ӿ������ṩ������ʡȥ <���ܼ���>
     */
    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) 1.2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (!prepare()) {
            return;
        }
        canvas.drawLines(convertToPts(), mPaint);
    }

    /**
     * �������б�ת��Ϊ�������� <���ܼ���>
     * 
     * @return
     */
    private float[] convertToPts() {
        int size = (mPoints.size() - 1) * 4;
        float[] pts = new float[size];

        for (int i = 0; i < size; i++) {
            int pointIndex = (i + 2) / 4;
            // ����
            if (i % 2 == 1) {
                pts[i] = mPoints.get(pointIndex).y;
            } else {
                pts[i] = mPoints.get(pointIndex).x;
            }
        }
        return pts;
    }

    private boolean prepare() {
        if (mPoints == null || mPoints.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @return ���� mPoints
     */
    public List<Point> getPoints() {
        return mPoints;
    }

    /**
     * @param ��mPoints���и�ֵ
     */
    public void setPoints(LinkedList<Point> mPoints) {
        this.mPoints = mPoints;
        invalidate();
    }

    /**
     * ����һ���� <���ܼ���>
     * 
     * @param point
     */
    public void addPoint(Point point) {
        if (this.mPoints == null) {
            this.mPoints = new LinkedList<Point>();
        }
        this.mPoints.addLast(point);
        invalidate();
    }

    /**
     * ����һ���ɼ��ĵ㣨��������������Ļ���ұߣ� <���ܼ���>
     * 
     * @param point
     */
    public void addVisiablePoint(Point point) {
        addPoint(point);
        scrollToPoint(point);
    }

    /**
     * �����㵽��Ļ�ұ� <���ܼ���>
     * 
     * @param point
     */
    private void scrollToPoint(Point point) {
        scrollTo(point.x - getCurveViewWidth(), 0);
    }

    /**
     * ���� <���ܼ���>
     */
    public void clearScreen() {
        if (this.mPoints == null || this.mPoints.isEmpty()) {
            return;
        }
        this.mPoints.clear();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            startX = event.getX();
            startY = event.getY();
            return true;
        case MotionEvent.ACTION_MOVE:
            float newX = event.getX();
            float newY = event.getY();
            scrollBy((int) (startX - newX), (int) (startY - newY));
            startX = newX;
            startY = newY;
            break;
        case MotionEvent.ACTION_UP:
            int sx = getScrollX();
            int sy = getScrollY();
            // �ܳ���
            int mWidth = getTotalWidth();
            // ���ӳ���
            int vWidth = getCurveViewWidth();
            if (mWidth < vWidth) {
                scrollTo(mWidth - vWidth, 0);
            }
            // ������sx<0
            else if (sx < 0) {
                if (sy < -10 || sy > 10) {
                    scrollBy(-sx, -sy);
                } else {
                    scrollBy(-sx, 0);
                }
            }
            // Ȼ��sx�ұ߳���
            else if (sx > (mWidth - vWidth)) {
                if (sy < -10 || sy > 10) {
                    scrollBy(mWidth - sx - vWidth, -sy);
                } else {
                    scrollBy(mWidth - sx - vWidth, 0);
                }
            }
            // ����sx�ڷ�Χ��
            else {
                if (sy < -10 || sy > 10) {
                    scrollBy(0, -sy);
                }
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * ȡ���ܳ��� <���ܼ���>
     * 
     * @return
     */
    public int getTotalWidth() {
        if (this.mPoints == null || this.mPoints.isEmpty()) {
            return 0;
        }
        return this.mPoints.getLast().x;
    }

    private int getCurveViewHeight() {
        return this.getHeight();
    }

    private int getCurveViewWidth() {
        return this.getWidth();
    }
}
