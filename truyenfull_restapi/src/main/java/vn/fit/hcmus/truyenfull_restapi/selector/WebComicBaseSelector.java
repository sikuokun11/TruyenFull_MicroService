package vn.fit.hcmus.truyenfull_restapi.selector;

public interface WebComicBaseSelector<T extends ComicContentBaseSelector,U extends CategoryContentBaseSelector, Z extends PageSelector>  {
    String mainUrl();

    String getCategoryListSelector();

    String getComicListSelector();

    String getNextStoryPageSelector();

    String getCurrChapterOfComic();

    T getComicContentSelector();

    U getCategoryContentSelector();

    Z getPageSelector();

    String getUrlComic();
}
