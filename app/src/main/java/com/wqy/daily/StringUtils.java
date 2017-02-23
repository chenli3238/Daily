package com.wqy.daily;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wqy.daily.interfaces.ImageLoader;
import com.wqy.daily.interfaces.ImageSpanTarget;
import com.wqy.daily.model.SpanInfo;
import com.wqy.daily.view.CreateMemoView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wqy on 17-2-22.
 */

public class StringUtils {

    public static final String IMAGE_PATTERN = "<img>([^<>]*)</img>";

    private static String CANDICATES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String makeString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CANDICATES.charAt((int) (Math.random() * CANDICATES.length())));
        }
        return sb.toString();
    }

    public static String encodeImageSpan(Uri uri) {
        return String.format("<img>%s</img>", uri.toString());
    }

    public static Uri decodeImageSpan(String span) {
        String uri = span.replaceAll("<[^>]*>", "");
        return Uri.parse(uri);
    }

    public static List<SpanInfo> extractImages(String text) {
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(text);
        List<SpanInfo> infos = new ArrayList<>();
        while (matcher.find()) {
            String sUri = matcher.group(1);
            int start = matcher.start();
            int end = matcher.end();
            SpanInfo info  = new SpanInfo(start, end, Uri.parse(sUri));
            infos.add(info);
        }
        return infos;
    }

    public static String[] splitByImages(String text) {
        String[] texts = text.split(IMAGE_PATTERN);
        return texts;
    }

    public static String hideImages(String text) {
        return text.replaceAll(IMAGE_PATTERN, "[image]");
    }

    public static final int TITLE_MAX_LEN = 25;
    public static String parseTitle(String text) {
        int end = text.indexOf("\n");
        if (end < 0) {
            end = TITLE_MAX_LEN > text.length() ? text.length() : TITLE_MAX_LEN;
        }
        if (end >= TITLE_MAX_LEN) {
            return text.substring(0, TITLE_MAX_LEN);
        }
        return text.substring(0, end);
    }

    public static String parseBody(String titleOfText, String text) {
        String s = text.substring(titleOfText.length());
        if (s.startsWith("\n")) {
            return s.replaceFirst("\\n+", "");
        }
        return s;
    }

    public static boolean startWithImage(String text) {
        return text.startsWith("\\s*<img>");
    }

    public static ImageSpan getSpan(Drawable d) {
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        return span;
    }

    public static void renderText(Context context, String text, SpannableStringBuilder builder,
                                  List<Target> imageHolders, ImageLoader imageLoader,
                                  TextView editText) {
        List<SpanInfo> infos = StringUtils.extractImages(text);
        builder.append(text);

        for (int i = 0; i < infos.size(); i++) {
            SpanInfo info = infos.get(i);
            String spanText = StringUtils.encodeImageSpan(info.getUri());
            ImageSpanTarget target = new ImageSpanTarget(spanText) {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: ");
                    ImageSpan newSpan = getSpan(new BitmapDrawable(context.getResources(), bitmap));
//                    int start = builder.getSpanStart(mImageSpan);
//                    int end = builder.getSpanEnd(mImageSpan);
                    mSpannableString.setSpan(newSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    builder.replace(info.getStart(), info.getEnd(), mSpannableString);
                    editText.setText(builder);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.d(TAG, "onPrepareLoad: ");
                    mImageSpan = getSpan(placeHolderDrawable);
                    mSpannableString.setSpan(mImageSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    builder.replace(info.getStart(), info.getEnd(), mSpannableString);
                    editText.setText(builder);
                }
            };
            imageHolders.add(target);
            imageLoader.load(info.getUri(), target);
        }
    }
}
