package com.my.zakupki;

import com.my.zakupki.Net.RegexRemoveRule;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dariya on 20.04.2017.
 */

public class RegexRemoveRuleTest {
    @Test
    public void removePinkBg() throws Exception {
        String regex = "<span class='pinkBg'>";
        String input = "\n" +
                "                                    Оказание <span class='pinkBg'>услуг</span> телефонной <span class='pinkBg'>связи</span> по цифровому потоку Е1\n" +
                "                                ";
        String output = "\n" +
                "                                    Оказание услуг</span> телефонной связи</span> по цифровому потоку Е1\n" +
                "                                ";
        RegexRemoveRule rule = new RegexRemoveRule(1,regex);
        List<String> resultList = rule.Apply(input);
        assertEquals(output,resultList.get(0));
    }
    @Test
    public void removePinkBgSpan() throws Exception {
        String regex = "(<span class='pinkBg'>)|(</span>)|(\\s{2,})";
        String input = "\n" +
                "                                    Оказание <span class='pinkBg'>услуг</span> телефонной <span class='pinkBg'>связи</span> по цифровому потоку Е1\n" +
                "                                ";
        String output = "Оказание услуг телефонной связи по цифровому потоку Е1";
        RegexRemoveRule rule = new RegexRemoveRule(1,regex);
        List<String> resultList = rule.Apply(input);
        assertEquals(output, resultList.get(0));
    }
}
