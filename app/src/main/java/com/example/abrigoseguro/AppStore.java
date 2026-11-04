package com.example.abrigoseguro;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AtomicFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/** Local-only user state. No network calls or sensitive data in logs. */
public final class AppStore {
    private final SharedPreferences prefs;
    private final AtomicFile offline;
    public AppStore(Context context) {
        prefs = context.getSharedPreferences("abrigoseguro", Context.MODE_PRIVATE);
        offline = new AtomicFile(new File(context.getFilesDir(), "offline.json"));
    }
    public boolean flag(String key) { return prefs.getBoolean(key, false); }
    public void setFlag(String key, boolean value) { prefs.edit().putBoolean(key, value).apply(); }
    public String text(String key) { return prefs.getString(key, ""); }
    public long revision() { return 31L * prefs.getAll().hashCode() + offline.getBaseFile().lastModified(); }
    public void setText(String key, String value) { prefs.edit().putString(key, value).apply(); }
    public Set<String> favorites() { return new HashSet<>(prefs.getStringSet("favorites", new HashSet<>())); }
    public void toggleFavorite(String id) {
        Set<String> ids = favorites(); if (!ids.remove(id)) ids.add(id);
        prefs.edit().putStringSet("favorites", ids).apply();
    }
    public static final class Contact {
        public final String id, name, phone;
        Contact(String id, String name, String phone) { this.id = id; this.name = name; this.phone = phone; }
    }
    public List<Contact> contacts() {
        List<Contact> result = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(prefs.getString("contacts", "[]"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                result.add(new Contact(item.getString("id"), item.getString("name"), item.getString("phone")));
            }
        } catch (JSONException ignored) { /* Invalid local data is treated as empty. */ }
        return result;
    }
    public String saveContact(String id, String name, String rawPhone) {
        String phone = DemoData.normalizePhone(rawPhone);
        if (name == null || name.trim().isEmpty() || name.trim().length() > 60)
            throw new IllegalArgumentException("Informe um nome de até 60 caracteres.");
        if (phone.isEmpty()) throw new IllegalArgumentException("Informe um telefone com DDD (10 a 15 dígitos).");
        List<Contact> all = contacts();
        for (Contact c : all) if (!c.id.equals(id) && comparable(c.phone).equals(comparable(phone)))
            throw new IllegalArgumentException("Este telefone já está na sua rede.");
        String actualId = id == null ? UUID.randomUUID().toString() : id;
        all.removeIf(c -> c.id.equals(actualId));
        all.add(new Contact(actualId, name.trim(), phone)); writeContacts(all);
        return actualId;
    }
    private static String comparable(String phone) {
        String digits = phone.replace("+", "");
        return digits.startsWith("55") && (digits.length() == 12 || digits.length() == 13) ? digits.substring(2) : digits;
    }
    public void deleteContact(String id) {
        List<Contact> all = contacts(); all.removeIf(c -> c.id.equals(id)); writeContacts(all);
    }
    private void writeContacts(List<Contact> all) {
        JSONArray data = new JSONArray();
        try {
            for (Contact c : all) data.put(new JSONObject().put("id", c.id).put("name", c.name).put("phone", c.phone));
        } catch (JSONException e) { throw new IllegalStateException(e); }
        prefs.edit().putString("contacts", data.toString()).apply();
    }
    public void saveOffline() throws IOException {
        FileOutputStream out = null;
        try {
            JSONArray shelters = new JSONArray(), alerts = new JSONArray();
            for (DemoData.Shelter s : DemoData.shelters()) shelters.put(new JSONObject()
                .put("id", s.id).put("name", s.name).put("address", s.address).put("capacity", s.capacity)
                .put("occupied", s.occupied).put("accessible", s.accessible).put("pets", s.pets).put("x", s.x).put("y", s.y));
            for (DemoData.Alert a : DemoData.alerts()) alerts.put(new JSONObject()
                .put("id", a.id).put("level", a.level).put("title", a.title).put("body", a.body));
            JSONObject pack = new JSONObject().put("version", 1).put("savedAt", System.currentTimeMillis())
                .put("shelters", shelters).put("alerts", alerts);
            out = offline.startWrite(); out.write(pack.toString().getBytes(StandardCharsets.UTF_8)); offline.finishWrite(out);
        } catch (JSONException | IOException e) {
            if (out != null) offline.failWrite(out);
            throw new IOException("Não foi possível salvar o pacote.", e);
        }
    }
    private JSONObject readOffline() {
        try {
            JSONObject pack = new JSONObject(new String(offline.readFully(), StandardCharsets.UTF_8));
            if (pack.getInt("version") != 1 || pack.getLong("savedAt") <= 0) return null;
            parseShelters(pack); parseAlerts(pack); return pack;
        } catch (IOException | JSONException e) { return null; }
    }
    private List<DemoData.Shelter> parseShelters(JSONObject pack) throws JSONException {
        List<DemoData.Shelter> all = new ArrayList<>(); JSONArray list = pack.getJSONArray("shelters");
        if (list.length() == 0) throw new JSONException("Empty catalog");
        for (int i = 0; i < list.length(); i++) {
            JSONObject s = list.getJSONObject(i);
            all.add(new DemoData.Shelter(s.getString("id"), s.getString("name"), s.getString("address"),
                s.getInt("capacity"), s.getInt("occupied"), s.getBoolean("accessible"), s.getBoolean("pets"),
                (float)s.getDouble("x"), (float)s.getDouble("y")));
        }
        return all;
    }
    private List<DemoData.Alert> parseAlerts(JSONObject pack) throws JSONException {
        List<DemoData.Alert> all = new ArrayList<>(); JSONArray list = pack.getJSONArray("alerts");
        if (list.length() == 0) throw new JSONException("Empty alerts");
        for (int i = 0; i < list.length(); i++) {
            JSONObject a = list.getJSONObject(i);
            all.add(new DemoData.Alert(a.getString("id"), a.getString("level"), a.getString("title"), a.getString("body")));
        }
        return all;
    }
    public List<DemoData.Shelter> shelters() {
        JSONObject pack = readOffline();
        try { if (pack != null) return parseShelters(pack); } catch (JSONException ignored) { }
        return DemoData.shelters();
    }
    public List<DemoData.Alert> alerts() {
        JSONObject pack = readOffline();
        try { if (pack != null) return parseAlerts(pack); } catch (JSONException ignored) { }
        return DemoData.alerts();
    }
    public boolean hasOffline() { return readOffline() != null; }
    public long offlineSavedAt() { JSONObject pack = readOffline(); return pack == null ? 0 : pack.optLong("savedAt"); }
    public long offlineBytes() { return offline.getBaseFile().length(); }
    public void removeOffline() { offline.delete(); }
    public void clear() { prefs.edit().clear().apply(); removeOffline(); }
}
