import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.helper.StringUtil;

/**
 * Created by baiqunwei on 15/5/13.
 */
public class Cooking {

    private String title;
    private String timeLong;
    private String detail;
    private String url;
    private String vid;
    private String uploadDate;
    private String img;
    private String hit;
    private String tags;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        if (this.title.endsWith("】") || this.title.endsWith("】")){
            return;
        }

        String parseTitle = this.title;

        if (this.title.lastIndexOf("】") > -1) {
            parseTitle = this.title.substring(this.title.lastIndexOf("】") + 1);
        }
        if (this.title.lastIndexOf("》") > -1) {
            parseTitle = this.title.substring(this.title.lastIndexOf("》") + 1);
        }
        if (this.title.lastIndexOf(" ") > -1) {
            parseTitle = this.title.substring(this.title.lastIndexOf(" ") + 1);
        }


        if (!StringUtil.isBlank(parseTitle)){
            this.title = parseTitle;
        }
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Cooking{" +
                "title='" + title + '\'' +
                ", timeLong='" + timeLong + '\'' +
                ", detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", vid='" + vid + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", img='" + img + '\'' +
                ", hit='" + hit + '\'' +
                ", tags='" + tags + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

