namespace java thrift.generated


typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Comic {
	1: i64 id;
	2: string name;
	3: string urlname;
	4: string author;
	5: string source;
	6: string status;
	7: i64 rate;
}




service TruyenFullService{
   Comic addComic(1:Comic comic);
   Comic getComic(1:i64 id);
}