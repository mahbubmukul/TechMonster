package com.bitmakers.techmonster.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontAusomeTextView extends TextView {
	public static Typeface m_typeFace = null;

	public FontAusomeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		if (isInEditMode()) {
			return;
		}
		loadTypeFace(context);
	}

	public FontAusomeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}
		loadTypeFace(context);
	}

	public FontAusomeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode()) {
			return;
		}
		loadTypeFace(context);
	}

	private void loadTypeFace(Context context) {
		if (m_typeFace == null)
			m_typeFace = Typeface.createFromAsset(context.getAssets(),
					"fonts/fontawesome-webfont.ttf");
		this.setTypeface(m_typeFace);
	}
}
