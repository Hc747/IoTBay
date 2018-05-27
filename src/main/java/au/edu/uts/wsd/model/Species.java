package au.edu.uts.wsd.model;

public enum Species {
    CAT("http://i0.kym-cdn.com/photos/images/original/000/959/452/436.jpg"),
    DOG("https://www.kimballstock.com/images/dog-stock-photos.jpg"),
    BIRD("https://www.stockvault.net/data/2011/02/05/117437/thumb16.jpg")
    ;
    
    private final String imageURL;
    
    private Species(String imageURL) {
        this.imageURL = imageURL;
    }
    
    public String getImageURL() {
        return imageURL;
    }
    
}
