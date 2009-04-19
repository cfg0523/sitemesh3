package com.opensymphony.sitemesh3.content.tagrules.html;

import com.opensymphony.sitemesh3.tagprocessor.BasicRule;
import com.opensymphony.sitemesh3.tagprocessor.Tag;
import com.opensymphony.sitemesh3.tagprocessor.CustomTag;

import java.io.IOException;

/**
 * Very simple rule for replacing all occurences of one tag with another.
 *
 * <p>For example, to convert all &lt;b&gt; tags to &lt;strong&gt;:</p>
 * <p>new TagReplaceRule("b", "strong"));</p>
 *
 * @author Joe Walnes
 */
public class TagReplaceRule extends BasicRule {

    private final String newTagName;

    public TagReplaceRule(String newTagName) {
        this.newTagName = newTagName;
    }

    @Override
    public void process(Tag tag) throws IOException {
        CustomTag customTag = new CustomTag(tag);
        customTag.setName(newTagName);
        customTag.writeTo(tagProcessorContext.currentBuffer());
    }
}