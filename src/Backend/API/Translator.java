package Backend.API;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

/** Translator using Google Translate. */
public class Translator {
  // "vi" for Vietnamese
  // "en" for English

  /**
   * Get meaning of a string. (Could be sentences, paragraphs, etc).
   *
   * @param langFrom language to translate, default "en" for English
   * @param langTo language to translate to, default "vi" for Vietnamese
   * @param text text to translate
   * @return
   * @throws IOException
   */
  public static String translate(String langFrom, String langTo, String text) throws IOException {
    String urlStr =
        "https://script.google.com/macros/s/AKfycbwFKMCY8l9lfefaOiskpuNnfyGMcLSrcsfG6fTsy4BopMOOORr30GbaesUhLODhCs_f2A/exec"
            + "?q="
            + URLEncoder.encode(text, StandardCharsets.UTF_8)
            + "&target="
            + langTo
            + "&source="
            + langFrom;
    URL url = new URL(urlStr);
    StringBuilder response = new StringBuilder();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestProperty("User-Agent", "Mozilla/20.0");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }

  /**
   * Text to speech function. Write to sound.mp3 file in the same directory. Overwrite if already
   * existed
   *
   * @param word could be a single word, sentence(s), paragraph(s)
   */
  public static void textToSpeech(String word) {
    try {
      word = java.net.URLEncoder.encode(word, StandardCharsets.UTF_8);
      URL url =
          new URL(
              "https://translate.google.com/translate_tts?ie=UTF-8&q="
                  + word
                  + ".&tl=en&total=1&idx=0&textlen=100&tk=350535.255567&client=webapp&prev=input");
      HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
      urlConnection.addRequestProperty("User-Agent", "Mozilla/20.0");
      InputStream audio = urlConnection.getInputStream();
      DataInputStream read = new DataInputStream(audio);
      OutputStream outputStream = new FileOutputStream(new File("sound.mp3"));
      byte[] buffer = new byte[1024];
      int len;
      while ((len = read.read(buffer)) > 0) {
        outputStream.write(buffer, 0, len);
      }
      outputStream.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /*
  public static void main(String[] args) {
    textToSpeech("hello world");
  }
   */

}
