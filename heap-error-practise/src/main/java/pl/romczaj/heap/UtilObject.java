package pl.romczaj.heap;

class UtilObject {
    static {
        System.out.println("Creating UtilObject");
    }

    static void save() {
        System.out.println("UtilObject save");
    }

    static void load() {
        System.out.println("UtilObject load");
    }

    void get() {
        System.out.println("UtilObject get");
    }
}
