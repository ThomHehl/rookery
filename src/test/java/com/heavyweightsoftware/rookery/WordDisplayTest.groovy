package com.heavyweightsoftware.rookery

import spock.lang.Specification

class WordDisplayTest extends Specification {

    private WordDisplay wordDisplay;

    def setup() {
        wordDisplay = new WordDisplay()

        wordDisplay.setText("For God so loved the world, that he gave his only begotten son, that whosoever believeth" +
                " in him should not perish, but have everlasting life.");
    }

    def "GetTextArray"() {
        given:
        String[] words;

        when: "Normal text"
        words = wordDisplay.getTextArray();

        then: "Should be correct"
        words.length == 25;
        words[0] == "For "
        words[1] == "God "
        words[2] == "so "
        words[3] == "loved "
        words[4] == "the "
        words[5] == "world, "
        words[15] == "believeth "
        words[16] == "in "
        words[24] == "life."
    }
}
