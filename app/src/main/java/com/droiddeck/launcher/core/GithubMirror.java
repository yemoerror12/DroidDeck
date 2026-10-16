package com.droiddeck.launcher.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Candidate URLs for GitHub content on networks that cannot reach GitHub directly
 * (common in mainland China): the direct URL first, then well-known mirror prefixes.
 * Mirrors only rewrite the host; the path, checksums and signatures stay intact, and
 * callers must keep verifying SHA-256 after download.
 */
public final class GithubMirror {
    private static final String[] PREFIXES = {
            "https://ghfast.top/",
            "https://gh-proxy.com/",
            "https://ghproxy.net/",
    };

    private GithubMirror() {}

    /** Direct URL first, then the same URL behind each mirror prefix. */
    public static List<String> candidates(String url) {
        List<String> list = new ArrayList<>();
        if (url == null || url.isEmpty()) return list;
        list.add(url);
        if (url.startsWith("https://github.com/")
                || url.startsWith("https://raw.githubusercontent.com/")
                || url.startsWith("https://objects.githubusercontent.com/")) {
            for (String prefix : PREFIXES) list.add(prefix + url);
        }
        return list;
    }
}
