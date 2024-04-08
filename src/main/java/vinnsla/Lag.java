package vinnsla;

/**
 * Lag klasi
 */
public class Lag {
    private String hljodskraNafn;
    private String lagNafn;
    private int lengd;
    private String myndNafn;

    /**
     * Lag smiður
     *
     * @param hljodskraNafn nafn mp3 skráar lagsins
     * @param lagNafn       Nafn lagsins sem sést á lista
     * @param lengd         lengd lags
     * @param myndNafn      mafn jpg skráar sem fylgir laginu
     */
    public Lag(String hljodskraNafn, String lagNafn, int lengd, String myndNafn) {
        this.hljodskraNafn = hljodskraNafn;
        this.lagNafn = lagNafn;
        this.lengd = lengd;
        this.myndNafn = myndNafn;
    }

    /**
     * @return nafni mp3 skrár lagsins
     */
    public String getHljodskraNafn() {
        return hljodskraNafn;
    }

    public String getLagNafn() {
        return lagNafn;
    }

    /**
     * @return lengd lagsins
     */
    public int getLengd() {
        return lengd;
    }

    /**
     * @return nafn lags toString svo það sjáist á lagalista
     */
    @Override
    public String toString() {
        return lagNafn;
    }

    /**
     * @return nafni jpg skráar sem fylgir með laginu
     */
    public String getMyndNafn() {
        return myndNafn;
    }

}
