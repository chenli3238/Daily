package com.wqy.daily;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wqy.daily.interfaces.ImageLoader;
import com.wqy.daily.interfaces.ImageSpanTarget;
import com.wqy.daily.view.CreateMemoView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wqy on 17-2-22.
 */

public class StringUtils {

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

    public static List<Uri> extractImages(String text) {
        Pattern pattern = Pattern.compile("<img>([^<>]*)</img>");
        Matcher matcher = pattern.matcher(text);
        List<Uri> uris = new ArrayList<>();
        while (matcher.find()) {
            String s = matcher.group();
            uris.add(Uri.parse(s));
        }
        return uris;
    }

    public static String[] splitByImages(String text) {
        String[] texts = text.split("<img>([^<>]*)</img>");
        return texts;
    }

    public static String hideImages(String text) {
        return text.replaceAll("<img>([^<>]*)</img>", "[image]");
    }

    public static final int TITLE_MAX_LEN = 25;
    public static String parseTitle(String text) {
        int end = text.indexOf("\n");
        if (end < 0) {
            end = TITLE_MAX_LEN;
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

    public static String renderText(Context context, String text, List<Target> imageHolders, ImageLoader imageLoader) {
        if (TextUtils.isEmpty(text)) return "";
        List<Uri> images = StringUtils.extractImages(text);
        String[] texts = StringUtils.splitByImages(text);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int p = 0;
        if (!StringUtils.startWithImage(text)) {
            builder.append(texts[p++]);
        }
        for (int i = 0; i < images.size(); i++) {
            Uri uri = images.get(i);
            String spanText = StringUtils.encodeImageSpan(uri);
            ImageSpanTarget target = new ImageSpanTarget(spanText) {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ImageSpan newSpan = getSpan(new BitmapDrawable(context.getResources(), bitmap));
//                    int start = builder.getSpanStart(mImageSpan);
//                    int end = builder.getSpanEnd(mImageSpan);
                    mSpannableString.setSpan(newSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.d(TAG, "onPrepareLoad: ");
                    mImageSpan = getSpan(placeHolderDrawable);
                    mSpannableString.setSpan(mImageSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    builder.append(mSpannableString);
                }
            };
            imageHolders.add(target);
            imageLoader.load(uri, target);
            builder.append(texts[p++]);
        }
        while (texts[p] != null) {
            builder.append(texts[p++]);
        }
        return builder.toString();
    }
}
