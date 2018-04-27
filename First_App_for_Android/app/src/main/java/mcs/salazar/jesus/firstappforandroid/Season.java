package mcs.salazar.jesus.firstappforandroid;

/**
 * Created by jesussalazar on 4/26/18.
 */

// Model
public class Season {

    private String SerieName;
    private int episodes;
    private int watchedEpisodes;
    private int numberOfSeason;

    public Season(String serieName, int episodes, int watchedEpisodes, int numberOfSeason) {
        SerieName = serieName;
        this.episodes = episodes;
        this.watchedEpisodes = watchedEpisodes;
        this.numberOfSeason = numberOfSeason;
    }

    public String getSerieName() {
        return SerieName;
    }

    public void setSerieName(String serieName) {
        SerieName = serieName;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getWatchedEpisodes() {
        return watchedEpisodes;
    }

    public void setWatchedEpisodes(int watchedEpisodes) {
        this.watchedEpisodes = watchedEpisodes;
    }

    public int getNumberOfSeason() {
        return numberOfSeason;
    }

    public void setNumberOfSeason(int numberOfSeason) {
        this.numberOfSeason = numberOfSeason;
    }

    @Override
    public String toString() {
        return "Season{" +
                "SerieName='" + SerieName + '\'' +
                ", episodes=" + episodes +
                ", watchedEpisodes=" + watchedEpisodes +
                ", numberOfSeason=" + numberOfSeason +
                '}';
    }
}
