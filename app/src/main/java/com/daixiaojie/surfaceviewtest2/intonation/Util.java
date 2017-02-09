package com.daixiaojie.surfaceviewtest2.intonation;

import android.content.Context;
import android.util.TypedValue;

/**
 * 工具类
 * @author zhy
 *
 */
public class Util
{

	/**
	 * dp2px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp)
	{
		int px = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
						.getDisplayMetrics()));
		return px;
	}

	public static int getLineHeight(int areaHeight, int minCents, int maxCents) {
		return areaHeight / ((maxCents - minCents) / 100 + 1);
	}

	public static int getY(int cents, int areaHeight, int minCents, int maxCents) {
//		return ((maxCents - cents) / 100) * getLineHeight(areaHeight, minCents, maxCents);
		//简化
		return ((maxCents - cents) * areaHeight) / (maxCents - minCents + 100);
	}

}
