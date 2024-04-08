package vinnsla;

/**
 * Áskrifandi klasi
 */
public class Askrifandi {
    private String nafn;

    /**
     * Áskrifandi smiður
     *
     * @param nafn Nafn notanda
     */
    public Askrifandi(String nafn) {
        this.nafn = nafn;
    }

    /**
     * @return nafni notanda
     */
    public String getNafn() {
        return nafn;
    }

}
