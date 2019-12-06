package vn.fit.hcmus.truyenfull_restapi.selector;

public class TruyenFullPageSelector  implements PageSelector{
    private TruyenFullPageSelector(){};
    private static class SingletonHelper{
        private static final TruyenFullPageSelector INSTANCE = new TruyenFullPageSelector();
    }
    public static TruyenFullPageSelector getInstance(){
        return SingletonHelper.INSTANCE;
    }
    @Override
    public String getNextComicPageSelector() {
        return ".pagination li[class=active] + li > a";
    }

    @Override
    public String getCurrComicPageSelector() {
        return ".pagination li[class=active]";
    }
}
