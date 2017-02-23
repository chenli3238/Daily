package com.wqy.daily;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

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
        String text = "<img>haha</img><img>haha</img><img>haha</img><img>haha</img><img>haha</img><img>haha</img>";
        List<Uri> uris = StringUtils.extractImages(text);
        assertNotNull(uris);
        assertEquals(6, uris.size());
    }

    @Test
    public void testSplitByImages() {
        String text = "1<img>haha</img>1<img>haha</img>1<img>haha</img>1<img>haha</img>1<img>haha</img>";
        String[] result = StringUtils.splitByImages(text);
        assertEquals(5, result.length);
    }
}
