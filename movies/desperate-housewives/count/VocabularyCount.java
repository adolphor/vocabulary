
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VocabularyCount {

  File or;
  File[] files;
  List<String> pathName = new ArrayList<>();

  static Map<String, Integer> vocMap = new LinkedHashMap<>();
  static Integer totalCount = 0;

  public static void main(String[] args) {
    VocabularyCount vc = new VocabularyCount();

    vc.getAllFiles("/Users/adolphor/IdeaProjects/bob/vocabulary/movies/desperate-housewives");
    vc.countAllWords(vc);

    System.out.println("单词总量：" + totalCount);
    System.out.println("非重复量：" + vocMap.size());

    ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(vocMap.entrySet());
    Collections.sort(list, (o1, o2) -> o2.getValue() - o1.getValue());

    int i = 0;
    for (Map.Entry<String, Integer> entry : list) {
      System.out.println(++i + " -> " + entry.getKey() + " -> " + entry.getValue() + "  ");
    }

  }

  private void countAllWords(VocabularyCount vc) {
    for (String list : vc.pathName) {
      String s = vc.readToString(list);
      String[] words = s.split("[\\s+]|[:]|[…]");

      totalCount += words.length;

      for (String wd : words) {
        wd = wd.replaceAll("[?]|[!]|[.]|[,]|[;]|[\"]|[(]|[)]|[\\[]|[]]|[*]|[~]|[#]|[0-9]", "")
            .replaceAll("", "").toLowerCase();
        if (wd.endsWith("'s"))
          wd = wd.replaceAll("'s", "");
        if (vocMap.containsKey(wd)) {
          Integer integer = vocMap.get(wd);
          vocMap.put(wd, integer + 1);
        } else {
          vocMap.put(wd, 1);
        }
      }
    }
  }


  public void getAllFiles(String dir) {
    or = new File(dir);
    files = or.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          pathName.add(file.getAbsolutePath());
        } else if (file.isDirectory()) {
          getAllFiles(file.getAbsolutePath());
        }
      }
    }
  }

  public String readToString(String fileName) {
    String encoding = "UTF-8";
    File file = new File(fileName);
    Long filelength = file.length();
    byte[] filecontent = new byte[filelength.intValue()];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(filecontent);
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return new String(filecontent, encoding);
    } catch (UnsupportedEncodingException e) {
      System.err.println("The OS does not support " + encoding);
      e.printStackTrace();
      return null;
    }
  }


}
