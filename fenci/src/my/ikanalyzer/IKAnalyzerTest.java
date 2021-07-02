package my.ikanalyzer;

import java.io.*;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerTest implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException {
        IKAnalyzer analyzer = new IKAnalyzer(true);

        try {
            InputStream is = new FileInputStream("tmall_rank.txt");
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            FileWriter fileWriter = new FileWriter("tmall_rank_fenci.txt");

            line = reader.readLine();
            while (line != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(line));
                tokenStream.addAttribute(CharTermAttribute.class);
                while (tokenStream.incrementToken()) {
                    CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
                    // System.out.print(charTermAttribute.toString() + " ");
                    fileWriter.append(charTermAttribute.toString() + " ");
                }

                line = reader.readLine();
            }
            reader.close();
            is.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        analyzer.close();
	}

    public  String splitWords(String line){
    	String result = "";
    	IKAnalyzer analyzer = new IKAnalyzer(true);
    	try {
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(line));
			tokenStream.addAttribute(CharTermAttribute.class);
			StringBuilder sb = new StringBuilder();
			while (tokenStream.incrementToken()) {
				CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
				//System.out.print(charTermAttribute.toString() + "|");
				sb.append(charTermAttribute.toString());
				sb.append("\t");
			}
			result = sb.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    	analyzer.close();
    	return  result;
    }
}
