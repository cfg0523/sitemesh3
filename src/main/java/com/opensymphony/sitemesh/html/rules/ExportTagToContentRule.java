package com.opensymphony.sitemesh.html.rules;

import com.opensymphony.sitemesh.tagprocessor.BasicBlockRule;
import com.opensymphony.sitemesh.tagprocessor.Tag;
import com.opensymphony.sitemesh.Content;

import java.io.IOException;

/**
 * Exports the contents of a match tag to property of the passed in {@link Content}.
 *
 * Additionally, if this tag has attributes, they will be written as child properties.
 *
 * <h3>Example</h3>
 *
 * <pre>
 * // Java
 * myState.addRule("foo", new ExportTagToContentRule(content, "bar");
 *
 * // Input
 * &lt;foo x=1 b=2&gt;hello&lt/foo&gt;
 *
 * // Exported properties of Content
 * bar=hello
 * bar.x=1
 * bar.b=2
 * </pre>
 *
 * @author Joe Walnes
 */
public class ExportTagToContentRule extends BasicBlockRule {

    private final Content content;
    private final String propertyName;

    public ExportTagToContentRule(Content content, String propertyName) {
        this.content = content;
        this.propertyName = propertyName;
    }

    @Override
    protected Object processStart(Tag tag) throws IOException {
        tag.writeTo(tagProcessorContext.currentBuffer());
        for (int i = 0; i < tag.getAttributeCount(); i++) {
            content.addProperty(propertyName + '.' + tag.getAttributeName(i), tag.getAttributeValue(i));
        }
        tagProcessorContext.pushBuffer();
        return null;
    }

    @Override
    protected void processEnd(Tag tag, Object data) throws IOException {
        CharSequence head = tagProcessorContext.currentBufferContents();
        content.addProperty(propertyName, head);
        tagProcessorContext.popBuffer();
        tagProcessorContext.currentBuffer().append(head);
        tag.writeTo(tagProcessorContext.currentBuffer());
    }
}