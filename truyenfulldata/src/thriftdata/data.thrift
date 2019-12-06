namespace java thrift.generated


typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String






service TruyenFullData{
      String getAllComicsOfCategory(1: required String name)
      String getAllChapterOfAComic(1: required String name)
      String getAllCategoryInDatabase()
      String getAllComicOfDatabase()
      String getComicWithHighestRate()
      String getAllComicOfAuthor(1: required String name)
      String getAllComicByStatus(1: required String name)
      int  countNumberOfComic();
      String insertComic(String author, String name , String rate ,String source ,String status, String urlname  );
      String deleteComic(String name);
      String updateComic(String oldname, String newname);
}