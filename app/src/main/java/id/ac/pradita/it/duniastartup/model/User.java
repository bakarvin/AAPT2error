package id.ac.pradita.it.duniastartup.model;
//INI
//BISA
//BUAT
//MANGGIL, BIKIN, DETAIL USER
//User
//KALO DI SQL INI TABLENYA, email,id,username,dll itu data nya
public class User {

    String email,id,imageUrl,username,facebook,twitter,telp,tglLahir,jenKel;


    public User(String email, String id, String imageUrl, String username, String facebook, String twitter, String telp, String tglLahir, String jenKel) {
        this.email = email;
        this.id = id;
        this.imageUrl = imageUrl;
        this.facebook = facebook;
        this.username = username;
        this.twitter = twitter;
        this.telp = telp;
        this.tglLahir = tglLahir;
        this.jenKel = jenKel;
    }

    public User()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }


    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }


    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getJenKel() {
        return jenKel;
    }

    public void setJenKel(String jenKel) {
        this.jenKel = jenKel;
    }
}

