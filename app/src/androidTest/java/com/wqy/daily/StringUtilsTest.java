package com.wqy.daily;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.wqy.daily.model.SpanInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by wqy on 17-2-22.
 */
@RunWith(AndroidJUnit4.class)
public class StringUtilsTest {

    @Test
    public void testEncodeImageSpan() {
        String s = "http://www.baidu.com";
        Uri uri = Uri.parse(s);
        assertNotNull(uri);
        String result = StringUtils.encodeImageSpan(uri);
        assertEquals("<img>" + s + "</img>", result);
    }

    @Test
    public void testDecodeImageSpan() {
        Uri uri = Uri.parse("http://www.baidu.com");
        assertNotNull(uri);
        Uri result = StringUtils.decodeImageSpan("<img>http://www.baidu.com</img>");
        assertEquals(uri.toString(), result.toString());
    }

    @Test
    public void testExtractImages() {
        String sUri = "http://www.baidu.com";
        String singleImg = StringUtils.encodeImageSpan(Uri.parse(sUri));
        String text = new StringBuilder()
                .append(singleImg)
                .append(singleImg)
                .append(singleImg)
                .append(singleImg)
                .append(singleImg)
                .append(singleImg)
                .toString();
        List<SpanInfo> infos = StringUtils.extractImages(text);
        assertNotNull(infos);
        assertEquals(6, infos.size());
        for (int i = 0; i < infos.size(); i++) {
            SpanInfo info = infos.get(i);
            assertEquals(sUri.length(), info.getUri().toString().length());
            assertEquals(i * singleImg.length(), info.getStart());
            assertEquals((i + 1) * singleImg.length(), info.getEnd());
        }
    }

    @Test
    public void testSplitByImages() {
        String text = "1<img>haha</img>1<img>haha</img>1<img>haha</img>1<img>haha</img>1<img>haha</img>";
        String[] result = StringUtils.splitByImages(text);
        assertEquals(5, result.length);
    }
}
