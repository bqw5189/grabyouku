import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baiqunwei on 15/5/13.
 */
public class Main {
    public static int TIME_OUT = 10000;


    private static String templateFileName = "/Volumes/MACINTOSH-WORK/work/code/utils/grabyouku/src/exportTemp.xlsx";

    private static String filterTags = "吃遍天下,膳道,美食,好吃,跟专家学做菜";


    public static void main(String[] args) throws Exception {
        //膳道
//        grap("膳道","UMzU1MjUzNzA0");

        //leelan
        grap("leelan","UNjI5NDMxNTY=");

//        grap("Tinrry下午茶","UOTU4NTExODA=");
//
//        grap("日日煮DayDayCook","UMTMyMTg4MzAwOA==");
//
//        grap("大厨制造","UMzc4OTE1NjQw");
//
//        grap("大吃货爱美食","UMTM2MjUzMzE5Ng==");



    }

    private static void grap(String author, String albumId) throws IOException, InvalidFormatException {
        List<Cooking> cookings = parseAlbum(author,albumId);
        writeToExcel(author + "-" + cookings.size() +  ".xlsx",cookings);
    }

    private static void writeToExcel(String destFileName, List<Cooking> cooks) throws IOException, InvalidFormatException {
        Map<String, List<Cooking>> beans = new HashMap<String, List<Cooking>>();

        Configuration config = new Configuration();
//        config.setUTF16( true );

        XLSTransformer transformer = new XLSTransformer(config);
//        transformer.groupCollection( "employee.name");
        beans.put("cook", cooks);

        transformer.transformXLS(templateFileName, beans, destFileName);
    }

    public static Document request(String url) throws IOException {
        return Jsoup.connect(url).timeout(TIME_OUT).get();
    }

    public static List<Cooking> parseAlbum(String author, String albumId) throws IOException {
        List<Cooking> result = new ArrayList<Cooking>();

        int i = 1;
        Document doc = request("http://i.youku.com/u/" + albumId + "/videos/fun_ajaxload/?__rt=1&__ro=&v_page=1&page_num=" + i++ + "&page_order=1");
        Elements vis = doc.select(".yk-col4");
        do{


        for (Element vi : vis) {
            Cooking cooking = new Cooking();

            Element img = vi.getElementsByTag("img").first();
            cooking.setTitle(img.attr("title"));
            cooking.setImg(img.attr("src"));

            Element vTime = vi.select(".v-time").first();
            cooking.setTimeLong(vTime.text());

            Element link = vi.getElementsByTag("a").first();
            cooking.setUrl(link.attr("href"));
            cooking.setVid(parseVID(cooking.getUrl()));
            Document detailDoc = request(cooking.getUrl());
            cooking.setDetail(parseDetail(detailDoc));
            cooking.setTags(parseTags(detailDoc));

            Element vNum = vi.select(".v-num").first();
            cooking.setHit(vNum.text());

            cooking.setUploadDate(vi.attr("c_time"));

            cooking.setAuthor(author);

            result.add(cooking);

            System.out.println(cooking);

        }

        doc = request("http://i.youku.com/u/" + albumId + "/videos/fun_ajaxload/?__rt=1&__ro=&v_page=1&page_num=" + i++ + "&page_order=1");
        vis = doc.select(".yk-col4");
        }while(vis.size() > 0);

        return result;
    }

    private static String parseDetail(Document doc) throws IOException {
        Element vis = doc.getElementById("text_short");

        if (null != vis) {
            return vis.text();
        } else {
            return "";
        }
    }

    private static String parseTags(Document doc) throws IOException {
        Elements tags = doc.select(".tag");

        String result = "";

        for (Element tag: tags){
            if (filterTags.indexOf(tag.text())>-1) continue;

            result += tag.text() + ",";
        }

        if (result.endsWith(",")){
            result = result.substring(0, result.length()-1);
        }


        return result;

    }



    private static String parseVID(String vUrl) {
        if (null == vUrl) {
            return "";
        }

        int startIndex = vUrl.indexOf("id_") + 3;
        int endIndex = vUrl.indexOf(".html");

        return vUrl.substring(startIndex, endIndex);
    }

}
