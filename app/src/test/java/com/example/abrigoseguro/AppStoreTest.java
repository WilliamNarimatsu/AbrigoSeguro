package com.example.abrigoseguro;

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import java.io.File;
import java.io.FileOutputStream;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 35)
public class AppStoreTest {
    Context context; AppStore store;
    @Before public void setup() {
        context = RuntimeEnvironment.getApplication();
        store = new AppStore(context); store.clear();
    }
    @Test public void contactsPersistEditAndDelete() {
        String id = store.saveContact(null, "Ana", "(11) 99999-1234");
        assertEquals("Ana", new AppStore(context).contacts().get(0).name);
        store.saveContact(id, "Ana Silva", "+55 11 99999-1234");
        assertEquals(1, store.contacts().size());
        assertEquals("Ana Silva", store.contacts().get(0).name);
        store.deleteContact(id); assertTrue(store.contacts().isEmpty());
    }
    @Test public void duplicateAndInvalidContactsAreRejected() {
        store.saveContact(null, "Ana", "11999991234");
        assertThrows(IllegalArgumentException.class, () -> store.saveContact(null, "Outro", "+55 (11) 99999-1234"));
        assertThrows(IllegalArgumentException.class, () -> store.saveContact(null, "", "11988881234"));
        assertThrows(IllegalArgumentException.class, () -> store.saveContact(null, "Nome", "193"));
        assertEquals(1, store.contacts().size());
    }
    @Test public void favoritesAndReadStateSurviveNewStore() {
        store.toggleFavorite("norte"); store.setFlag("read_chuva", true);
        assertTrue(new AppStore(context).favorites().contains("norte"));
        assertTrue(new AppStore(context).flag("read_chuva"));
        store.toggleFavorite("norte"); assertTrue(store.favorites().isEmpty());
    }
    @Test public void offlinePackIsReadableRemovableAndCorruptionFallsBack() throws Exception {
        store.saveOffline(); assertTrue(store.hasOffline());
        assertTrue(store.offlineBytes() > 100);
        assertEquals(4, store.shelters().size()); assertEquals(3, store.alerts().size());
        try (FileOutputStream out = new FileOutputStream(new File(context.getFilesDir(), "offline.json"))) {
            out.write("invalid json".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        assertFalse(store.hasOffline()); assertEquals(4, store.shelters().size());
        store.removeOffline(); assertFalse(store.hasOffline());
    }
}
