package ro.ubb.movie_catalog.core.domain.entities;

public class Pair<A, B> {
    public final A fst;

    public final B snd;

    public Pair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }
    public String toString() {
        return "Pair[" + fst + "," + snd + "]";
    }
}
