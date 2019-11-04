package com.need.unknown.component.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by anurr on 3/2/2018.
 */

public class FragmentPager extends ViewPager {
   boolean enabled;

   public FragmentPager(Context context) {
	  super(context);
   }

   public FragmentPager(Context context, AttributeSet attrs) {
	  super(context, attrs);
	  this.enabled = true;
   }

//   @Override
//   public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//	  int height = 0;
//	  for(int i = 0; i < getChildCount(); i++) {
//		 View child = getChildAt(i);
//		 child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//		 int h = child.getMeasuredHeight();
//		 if(h > height) height = h;
//	  }
//
//	  heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//
//	  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
       return this.enabled && super.onTouchEvent(event);

   }

   @Override
   public boolean onInterceptTouchEvent(MotionEvent event) {
       return this.enabled && super.onInterceptTouchEvent(event);

   }

   public void setPagingEnabled(boolean enabled) {
	  this.enabled = enabled;
   }
}
