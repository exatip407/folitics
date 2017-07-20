package com.ohmuk.folitics.component.newsfeed;

//import it.sauronsoftware.feed4j.bean.Feed;
//import it.sauronsoftware.feed4j.bean.FeedHeader;
//import it.sauronsoftware.feed4j.bean.FeedItem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;

public class RSSFeedUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RSSFeedUtil.class);

    public static final int MAX_SCRAP_ATTEMPTS = 5;
    public static final String CATEGORY_SEPARATOR = ",";
    public static final int MAX_LENGTH_DESCRIPTION = 1000;
    public static final int MAX_LENGTH_TITLE = 1000;
    public static final int MAX_LENGTH_LINK = 2000;
    public static final int MAX_LENGTH_COPYRIGHT = 1000;

    // public static final void printRSSFeed(Feed feed) {
    // printRSSFeedHeader(feed);
    // printRSSFeedItems(feed);
    // }
    //
    // public static final void printRSSFeedHeader(Feed feed) {
    // System.out.println("** HEADER **");
    // FeedHeader header = feed.getHeader();
    // System.out.println("AttributeCount: " + header.getAttributeCount());
    // System.out.println("Name: " + header.getName());
    // System.out.println("NamespaceURI: " + header.getNamespaceURI());
    // System.out.println("NodeCount: " + header.getNodeCount());
    // System.out.println("Title: " + header.getTitle());
    // System.out.println("Value: " + header.getValue());
    // System.out.println("Image: " + header.getImage());
    // System.out.println("Link: " + header.getLink());
    // System.out.println("URL: " + header.getURL());
    // System.out.println("Description: " + header.getDescription());
    // System.out.println("Language: " + header.getLanguage());
    // System.out.println("PubDate: " + header.getPubDate());
    // }
    //
    // public static final void printRSSFeedItems(Feed feed) {
    // System.out.println("** ITEMS **");
    // int items = feed.getItemCount();
    // for (int i = 0; i < items; i++) {
    // FeedItem item = feed.getItem(i);
    // System.out.println("AttributeCount: " + item.getAttributeCount());
    // System.out.println("EnclosureCount: " + item.getEnclosureCount());
    // System.out.println("GUID: " + item.getGUID());
    // System.out.println("Name: " + item.getName());
    // System.out.println("NamespaceURI: " + item.getNamespaceURI());
    // System.out.println("NodeCount: " + item.getNodeCount());
    // System.out.println("Value: " + item.getValue());
    // System.out.println("Title: " + item.getTitle());
    // System.out.println("Comments: " + item.getComments());
    // System.out.println("ModDate: " + item.getModDate());
    // System.out.println("Link: " + item.getLink());
    // System.out.println("Plain text description: " + item.getDescriptionAsText());
    // System.out.println("HTML description: " + item.getDescriptionAsHTML());
    // System.out.println("PubDate: " + item.getPubDate());
    // }
    // }

    public static final void printRSSFeedHeader(SyndFeed feed) {
        System.out.println("** SyndFeed **");
        System.out.println("Author: " + feed.getAuthor());
        System.out.println("Copyright: " + feed.getCopyright());
        System.out.println("Description: " + feed.getDescription());
        System.out.println("Docs: " + feed.getDocs());
        System.out.println("Title: " + feed.getTitle());
        System.out.println("Encoding: " + feed.getEncoding());
        System.out.println("Image: " + feed.getImage());
        System.out.println("Link: " + feed.getLink());
        System.out.println("FeedType: " + feed.getFeedType());
        System.out.println("Generator: " + feed.getGenerator());
        System.out.println("Language: " + feed.getLanguage());
        System.out.println("PublishedDate: " + feed.getPublishedDate());
        System.out.println("ManagingEditor: " + feed.getManagingEditor());
        System.out.println("StyleSheet: " + feed.getStyleSheet());
        System.out.println("Uri: " + feed.getUri());
        System.out.println("WebMaster: " + feed.getWebMaster());
        System.out.println("Authors: " + feed.getAuthors());
        System.out.println("Categories: " + feed.getCategories());
        List<SyndCategory> cats = feed.getCategories();
        RSSFeedUtil.printCategories(cats);
        System.out.println("Contributors: " + feed.getContributors());
        RSSFeedUtil.printPersons(feed.getContributors());
        System.out.println("DescriptionEx: " + feed.getDescriptionEx());
        System.out.println("ForeignMarkup: " + feed.getForeignMarkup());
        System.out.println("Image: " + feed.getImage());
        // System.out.println("Modules: " + feed.getModules());
        System.out.println("SupportedFeedTypes: " + feed.getSupportedFeedTypes());
        System.out.println("Entries: ");
        RSSFeedUtil.printEntries(feed.getEntries());

    }

    public static final void printCategory(SyndCategory cat) {
        System.out.println("*********Printing SyndCategory*****************");
        System.out.println("Name: " + cat.getName());
        System.out.println("TaxonomyUri: " + cat.getTaxonomyUri());
        System.out.println("Interface: " + cat.getInterface());
    }

    public static final void printEntry(SyndEntry entry) {
        System.out.println("*********Printing SyndEntry*****************");
        System.out.println("Author: " + entry.getAuthor());
        System.out.println("Comments: " + entry.getComments());
        System.out.println("Title: " + entry.getTitle());
        System.out.println("Link: " + entry.getLink());
        System.out.println("Link: ");
        RSSFeedUtil.printPersons(entry.getAuthors());
        System.out.println("Categories: ");
        RSSFeedUtil.printCategories(entry.getCategories());
        System.out.println("Interface: " + entry.getInterface());
        System.out.println("Contents: ");
        RSSFeedUtil.printContents(entry.getContents());
        System.out.println("Description: ");
        RSSFeedUtil.printContent(entry.getDescription());
        System.out.println("PublishedDate: " + entry.getPublishedDate());
        System.out.println("UpdatedDate: " + entry.getUpdatedDate());
        System.out.println("Links: " + entry.getLinks());
        System.out.println("Enclosures: " + entry.getEnclosures());
        System.out.println("ForeignMarkup: " + entry.getForeignMarkup());
        System.out.println("Source: " + entry.getSource());
        // System.out.println("Modules: " + entry.getModules());
        System.out.println("TitleEx: ");
        RSSFeedUtil.printContent(entry.getTitleEx());
        System.out.println("WireEntry: " + entry.getWireEntry());
    }

    public static final String getContent(SyndContent content) {
        if (content != null) {
            System.out.println("Mode: " + content.getMode());
            System.out.println("Type: " + content.getType());
            System.out.println("Value: " + content.getValue());
            return content.getValue();
        }
        return null;
    }

    public static final void printContent(SyndContent content) {
        System.out.println("*********Printing SyndContent*****************");

        System.out.println("Mode: " + content.getMode());
        System.out.println("Type: " + content.getType());
        System.out.println("Value: " + content.getValue());
    }

    public static final void printPerson(SyndPerson person) {
        System.out.println("*********Printing SyndPerson*****************");
        System.out.println("Name: " + person.getName());
        System.out.println("Email: " + person.getEmail());
        System.out.println("Uri: " + person.getUri());
        // System.out.println("Modules: " + person.getModules());
    }

    public static final void printCategories(List<SyndCategory> cats) {
        if (cats != null) {
            System.out.println("*********** # Categories ***** : " + cats.size());
            for (SyndCategory cat : cats) {
                printCategory(cat);
            }
        }
    }

    public static final String getCategories(List<SyndCategory> cats) {
        return getCategories(cats, CATEGORY_SEPARATOR);
    }

    public static final String getCategories(List<SyndCategory> cats, String separator) {
        StringBuilder sb = new StringBuilder();
        if (cats != null) {
            for (SyndCategory cat : cats) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(cat.getName());
            }
        }
        System.out.println("*********** # Categories  : " + sb.toString());

        return sb.toString();
    }

    public static final void printEntries(List<SyndEntry> entries) {
        if (entries != null) {
            System.out.println("*********** # Entries ***** : " + entries.size());
            for (SyndEntry entry : entries) {
                printEntry(entry);
            }
        }
    }

    public static final void printPersons(List<SyndPerson> persons) {
        if (persons != null) {
            System.out.println("*********** # Contributors ***** : " + persons.size());
            for (SyndPerson person : persons) {
                printPerson(person);
            }
        }
    }

    public static final void printContents(List<SyndContent> contents) {
        if (contents != null) {
            System.out.println("*********** # Contents ***** : " + contents.size());
            for (SyndContent content : contents) {
                printContent(content);
            }
        }
    }

    public static final void printRSSFeedItems(SyndFeed feed) {
        System.out.println("*********Printing SyndFeed Links *****************");
        List<SyndLink> links = feed.getLinks();
        int items = links.size();
        System.out.println("** Links ** : " + items);
        for (int i = 0; i < items; i++) {
            SyndLink item = links.get(i);
            System.out.println("Title: " + item.getTitle());
            System.out.println("Href: " + item.getHref());
            System.out.println("Length: " + item.getLength());
            System.out.println("Rel: " + item.getRel());
            System.out.println("Type: " + item.getType());
        }
    }

    public static String truncateLength(String str, int len) {
        if (str == null || str.isEmpty() || str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len - 1);
        }
    }

    public static String removeHTML(String html) {
        // detecting the file type
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

        try {
            // convert String into InputStream
            InputStream inputstream = new ByteArrayInputStream(html.getBytes());

            HtmlParser htmlparser = new HtmlParser();
            htmlparser.parse(inputstream, handler, metadata, pcontext);
            String plainText = handler.toString();
            System.out.println("Contents of the document: " + plainText);
            return plainText;
        } catch (Exception e) {
            LOGGER.error("Error while removing html from rich text", e);
        }
        return null;
    }

}
