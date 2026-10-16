package com.droiddeck.launcher.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GithubMirrorTest {
    @Test fun githubUrlsGetDirectFirstThenMirrorPrefixes() {
        val url = "https://github.com/Droid-Deck/DroidDeck-Components/releases/download/r1/linuxfs.tar.zst"
        val candidates = GithubMirror.candidates(url)
        assertEquals(4, candidates.size)
        assertEquals(url, candidates[0])
        for (i in 1 until candidates.size) assertTrue(candidates[i].endsWith(url))
        assertTrue(candidates[1].startsWith("https://"))
    }

    @Test fun rawGithubUrlsAreCoveredToo() {
        val candidates = GithubMirror.candidates(
                "https://raw.githubusercontent.com/Droid-Deck/DroidDeck-Components/main/linuxfs.json")
        assertEquals(4, candidates.size)
        assertTrue(candidates[3].startsWith("https://ghproxy.net/https://raw.githubusercontent.com/"))
    }

    @Test fun nonGithubUrlsAreReturnedAlone() {
        assertEquals(1, GithubMirror.candidates("https://cdn.example.com/file.tar.zst").size)
    }

    @Test fun nullAndEmptyUrlsStayEmpty() {
        assertEquals(0, GithubMirror.candidates(null).size)
        assertEquals(0, GithubMirror.candidates("").size)
    }
}
