package id.ac.pradita.it.duniastartup.model;


//TODO 3 Buat kelas model Mahasiswa

public class model {
    private String Uname_inv, nama_inv, tanggal_lahir;

    public model() {

    }

    public model(String nim, String nama, String alamat) {
        this.Uname_inv = nim;
        this.nama_inv = nama;
        this.tanggal_lahir = alamat;

    }

    public String getUname_inv() {
        return Uname_inv;
    }

    public void setUname_inv(String Uname_inv) {
        this.Uname_inv = Uname_inv;
    }

    public String getnama_inv() {
        return nama_inv;
    }

    public void setnama_inv(String nama_inv) {
        this.nama_inv = nama_inv;
    }

    public String gettanggal_lahir() {
        return tanggal_lahir;
    }

    public void settanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }
}

