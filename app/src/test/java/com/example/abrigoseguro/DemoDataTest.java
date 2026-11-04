package com.example.abrigoseguro;

import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.*;

public class DemoDataTest {
    @Test public void searchIgnoresAccentsAndCase() {
        assertEquals("central", DemoData.filter(DemoData.shelters(), " GINASIO ", false, false, false, false, Collections.emptySet()).get(0).id);
    }
    @Test public void combinedFiltersExcludeFullOrInaccessibleShelters() {
        for (DemoData.Shelter s : DemoData.filter(DemoData.shelters(), "", true, true, true, false, Collections.emptySet())) {
            assertTrue(s.accessible && s.pets && s.available() > 0);
        }
        assertEquals(1, DemoData.filter(DemoData.shelters(), "", true, true, true, false, Collections.emptySet()).size());
    }
    @Test public void favoritesAndEmptyResults() {
        assertTrue(DemoData.filter(DemoData.shelters(), "", false, false, false, true, Collections.emptySet()).isEmpty());
        assertEquals(1, DemoData.filter(DemoData.shelters(), "", false, false, false, true, Collections.singleton("norte")).size());
        assertTrue(DemoData.filter(DemoData.shelters(), "inexistente", false, false, false, false, Collections.emptySet()).isEmpty());
        assertNull(DemoData.findShelter(DemoData.shelters(), "invalid"));
    }
    @Test public void phonesAreNormalizedButInvalidInputIsRejected() {
        assertEquals("+5511999991234", DemoData.normalizePhone("+55 (11) 99999-1234"));
        assertEquals("11999991234", DemoData.normalizePhone("(11) 99999-1234"));
        assertEquals("", DemoData.normalizePhone("123"));
        assertEquals("", DemoData.normalizePhone("abc11999991234"));
        assertEquals("", DemoData.normalizePhone("11+999991234"));
    }
}
