package com.sysc4806;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;

/**
 * Created by rscar on 3/29/2017.
 */
public class MarkdownTranslator {
    static final MutableDataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    TablesExtension.create(),
                    StrikethroughExtension.create(),
                    TaskListExtension.create(),
                    SuperscriptExtension.create()
            ));

    public static String translate(String input) {
        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        Node parsed = parser.parse(input);
        return renderer.render(parsed);
    }
}
