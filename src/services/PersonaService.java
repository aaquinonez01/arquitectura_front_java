package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import models.Persona;
import org.json.JSONArray;
import org.json.JSONObject;

public class PersonaService {
    private final String URL_API = "https://crudpersona.onrender.com/api/personas"; // Cambia esta URL por la de tu API.
    
    public ArrayList<Persona> GetPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        try {
            URL url = new URL(URL_API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                
                JSONArray jsonArray = new JSONArray(content.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Date fechaNac = dateFormat.parse(jsonObject.getString("fechaNac"));
                    Persona persona = new Persona(
                        jsonObject.getInt("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("cedula"),
                        fechaNac,
                        jsonObject.getInt("edad")
                    );
                    personas.add(persona);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al obtener a las personas, Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personas;
    }
    public boolean PostPersona(Persona persona) {
        try {
            URL url = new URL(URL_API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", persona.getId());
            jsonObject.put("nombre", persona.getNombre());
            jsonObject.put("cedula", persona.getCedula());
            jsonObject.put("fechaNac", new SimpleDateFormat("yyyy-MM-dd").format(persona.getFechaNac()));
            jsonObject.put("edad", persona.getEdad());
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
            
            int responseCode = conn.getResponseCode();
            JOptionPane.showMessageDialog(null, responseCode);
            if (responseCode == 200) {
                return true;
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                JOptionPane.showMessageDialog(null, "Error al crear la persona, Code: " + responseCode + ", Response: " + content.toString());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
  public Persona GetPersonaById(int id) {
        Persona persona = null;
        try {
            URL url = new URL(URL_API + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                
                JSONObject jsonObject = new JSONObject(content.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNac = dateFormat.parse(jsonObject.getString("fechaNac"));
                persona = new Persona(
                    jsonObject.getInt("id"),
                    jsonObject.getString("nombre"),
                    jsonObject.getString("cedula"),
                    fechaNac,
                    jsonObject.getInt("edad")
                );
            } else {
                JOptionPane.showMessageDialog(null, "Error al obtener la persona, Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persona;
    }
  public boolean UpdatePersona(int id, Persona persona) {
        try {
            URL url = new URL(URL_API + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", persona.getNombre());
            jsonObject.put("cedula", persona.getCedula());
            jsonObject.put("fechaNac", new SimpleDateFormat("yyyy-MM-dd").format(persona.getFechaNac()));
            jsonObject.put("edad", persona.getEdad());
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                JOptionPane.showMessageDialog(null, "Error al actualizar la persona, Code: " + responseCode + ", Response: " + content.toString());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
  public boolean DeletePersona(int id) {
        try {
            URL url = new URL(URL_API + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                JOptionPane.showMessageDialog(null, "Error al eliminar la persona, Code: " + responseCode + ", Response: " + content.toString());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
