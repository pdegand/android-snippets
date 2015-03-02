package fr.pdegand.android.snippets;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * With Snippets, you will never write boilerplate code anymore for trivial operations.
 * <p/>
 * Snippets handles for you version number checking to avoid using deprecated API.
 * This does NOT provides "compat" version for older platform. It only calls to deprecated functions
 * when running on older platform.
 */
@SuppressWarnings("deprecation")
public class Snippets {

    private Snippets() {
    }

    /**
     * Get the screen size in pixels.
     * <p/>
     * Useful if minSdk < 13
     *
     * @param context a non-null context
     * @return a Point with x as the width and y as the height
     */
    public static Point getScreenSize(@NonNull final Context context) {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(p);
        } else {
            p.set(display.getWidth(), display.getHeight());
        }
        return p;
    }

    /**
     * Get the width of the screen in pixels
     *
     * @param context a non-null context
     * @return the width of the screen in pixels
     */
    public static int getScreenWidth(@NonNull final Context context) {
        return getScreenSize(context).x;
    }

    /**
     * Get the height of the screen in pixels
     *
     * @param context a non-null context
     * @return the height of the screen in pixels
     */
    public static int getScreenHeight(@NonNull final Context context) {
        return getScreenSize(context).y;
    }

    /**
     * Remove a ViewTreeObserver.OnGlobalLayoutListener attached to the ViewTreeObserver of a View
     * <p/>
     * Useful if minSdk < 16
     *
     * @param view   a non-null view on wich the listener should be removed
     * @param victim the listener to remove
     */
    public static void removeOnGlobalLayoutListener(@NonNull final View view, final ViewTreeObserver.OnGlobalLayoutListener victim) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            viewTreeObserver.removeOnGlobalLayoutListener(victim);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(victim);
        }
    }

    /**
     * Set the background of a View
     * <p/>
     * Useful if minSdk < 16
     *
     * @param view     a non-null View on wich the background will be applied
     * @param drawable the Drawable to be set as background or null to remove background
     */
    public static void setBackgroundDrawable(@NonNull final View view, final Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in android.view.View.setId(int).
     * This value will not collide with ID values generated at build time by aapt for R.id.
     * <p/>
     * This is the code snippet directly from Android API v17.
     * <p/>
     * Useful if minSdk < 17
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
    }
}
