namespace java thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String



exception DataException {
    1: optional String message,
    2: optional String callStack,
    3: optional String data

}

service TruyenFullService{
      bool crawTruyen(1: required int num) throws (1: DataException dataException)
}