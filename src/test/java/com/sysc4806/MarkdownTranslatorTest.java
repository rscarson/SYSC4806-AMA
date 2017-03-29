package com.sysc4806;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rscar on 3/29/2017.
 */
public class MarkdownTranslatorTest {
    @Test
    public void testStrikethrough() {
        Assert.assertEquals("<p><del>test</del></p>\n",
                MarkdownTranslator.translate("~~test~~"));
    }

    @Test
    public void testParagraphs() {
        Assert.assertEquals("<p>test</p>\n",
                MarkdownTranslator.translate("test"));

        Assert.assertEquals("<p>test</p>\n<p>test</p>\n",
                MarkdownTranslator.translate("test\n\ntest"));
    }

    @Test
    public void testLinks() {
        Assert.assertEquals("<p><a href=\"#\">test</a></p>\n",
                MarkdownTranslator.translate("[test](#)"));
    }
}
