package fitchat.com.fitchat;

import com.google.firebase.firestore.DocumentReference;

public class User {
    private String email;
    private String gender;
    private String name;
    private long age;
    private double weight;
    private double longitude;
    private double latitude;
    private String favoriteExercise;
    private boolean isPairing;

    private DocumentReference reference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String gender, long age,
                double weight, double longitude, double latitude, String favoriteExercise, boolean isPairing) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.longitude = longitude;
        this.latitude = latitude;
        this.favoriteExercise = favoriteExercise;
        this.isPairing = isPairing;
    }
    public User() {}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getFavoriteExercise() {
        return favoriteExercise;
    }

    public void setFavoriteExercise(String favoriteExercise) {
        this.favoriteExercise = favoriteExercise;
    }

    public boolean isPairing() {
        return isPairing;
    }

    public void setPairing(boolean pairing) {
        isPairing = pairing;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
    }

    public String toString() {
        return reference.getPath();
    }
}
