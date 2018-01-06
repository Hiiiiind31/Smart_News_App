
package brand.smart_news_app.Articles.News.Articles_Model;

import java.util.List;

public class AllNewsModel {


    private Integer success;

    private List<Slider> slider = null;

    private List<TextArticle> textArticles = null;

    private List<ImageArticle> imageArticles = null;

    private List<VideoArticle> videoArticles = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<TextArticle> getTextArticles() {
        return textArticles;
    }

    public void setTextArticles(List<TextArticle> textArticles) {
        this.textArticles = textArticles;
    }

    public List<ImageArticle> getImageArticles() {
        return imageArticles;
    }

    public void setImageArticles(List<ImageArticle> imageArticles) {
        this.imageArticles = imageArticles;
    }

    public List<VideoArticle> getVideoArticles() {
        return videoArticles;
    }

    public void setVideoArticles(List<VideoArticle> videoArticles) {
        this.videoArticles = videoArticles;
    }

}
